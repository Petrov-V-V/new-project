import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.css';
import { Row, Col, Input, Button, Layout, Card, AutoComplete, Modal, Select, message } from 'antd';
import ProductList from './ProductList';
import Cart from './Cart';
import {
  searchProducts
} from '../slices/productSlice';
import productService from '../services/productService';
import cartService from '../services/cartService';
import paymentService from '../services/paymentService';
import authService from "../services/authService";
import { login, logout } from "../slices/authSlice";


const { Content } = Layout;
const { Meta } = Card;


export const App = () => {
  const [loggedIn, setLoggedIn] = useState(false);


  const [switchUserModalVisible, setSwitchUserModalVisible] = useState(false);
  const [logInEmail, setLogInEmail] = useState('');
  const [logInPassword, setLogInPassword] = useState('');  
  const [addProductModalVisible, setAddProductModalVisible] = useState(false);
  const [productName, setProductName] = useState('');
  const [productPrice, setProductPrice] = useState('');
  const [productQuantity, setProductQuantity] = useState('');
  const [addUserModalVisible, setAddUserModalVisible] = useState(false);
  const [registerEmail, setRegisterEmail] = useState('');
  const [registerUsername, setRegisterUsername] = useState('');
  const [registerPassword, setRegisterPassword] = useState('');

  const cartItems = useSelector((state) => state.product.cartItems);
  const products = useSelector((state) => state.product.products);
  const searchQuery = useSelector((state) => state.product.searchQuery);
  const dispatch = useDispatch();

  const theMostCurrentUser = useSelector((state) => state.auth.user);

  useEffect(() => {
    productService.getProducts(dispatch);
  }, []);

  const handleSearchQueryChange = (event) => {
    dispatch(searchProducts(event.target.value));
  };

  const handlePayment = () => {
    const newInfo = {
      cardNumber: "2135434165",
      userId: theMostCurrentUser.id
    }
    paymentService.pay(dispatch, newInfo, theMostCurrentUser.id);
  };

  const handleAddToCart = (productId) => {
    const currentUserId = theMostCurrentUser !== null ? theMostCurrentUser.id : 0;
    const newProductId = {
      id: productId
    };
    cartService.addProductToCart(dispatch, currentUserId, newProductId);
  };

  const handleClearCart = () => {
    for (const product of cartItems) {
      handleRemoveFromCart(product.id);
    }
  };

  const handleRemoveFromCart = (productId) => {
    cartService.deleteProductFromCart(dispatch, theMostCurrentUser.id, productId);
  };

  const handleChangeQuantity = (id, quantity) => {
    const newProductQuantity = {
      "count": quantity
    };
    cartService.updateProductInCart( dispatch, theMostCurrentUser.id, id, newProductQuantity );
  };

  const handleAddProduct = () => {
    const newProduct = {
        name: productName,
        price: productPrice,
        count: productQuantity,
    };
    productService.createProduct(dispatch, newProduct);
    setProductName('');
    setProductPrice('');
    setProductQuantity('');
  };

  const handleDeleteProduct = (productId) => {
    productService.deleteProduct(dispatch, productId);
  };

  const handleChangePrice = (productId, newPrice) => {
    const oldProduct = products.find(product => product.id === productId)
    const newProduct = {
        id: productId,
        name: oldProduct.name,
        price: newPrice,
        count: oldProduct.count,
    };
    const currentUserId = theMostCurrentUser !== null ? theMostCurrentUser.id : 0;
    productService.updateProduct(dispatch, newProduct, currentUserId);
  };

  const handleChangeName = (productId, newName) => {
    const oldProduct = products.find(product => product.id === productId)
    const newProduct = {
        id: productId,
        name: newName,
        price: oldProduct.price,
        count: oldProduct.count,
    };
    const currentUserId = theMostCurrentUser !== null ? theMostCurrentUser.id : 0;
    productService.updateProduct(dispatch, newProduct, currentUserId);
  };

  const handleChangeQuantityInDB = (productId, newQuantity) => {
    const oldProduct = products.find(product => product.id === productId)
    const newProduct = {
        id: productId,
        name: oldProduct.name,
        price: oldProduct.price,
        count: newQuantity,
    };
    const currentUserId = theMostCurrentUser !== null ? theMostCurrentUser.id : 0;
    productService.updateProduct(dispatch, newProduct, currentUserId);
  };

  const handleChangeProduct = (productId, newPrice, newName, newQuantity) => {
    const oldProduct = products.find(product => product.id === productId)
    if (newPrice === undefined){
      newPrice = oldProduct.price
    }
    if (newName === undefined){
      newName = oldProduct.name
    }
    if (newQuantity === undefined){
      newQuantity = oldProduct.count
    }
    const newProduct = {
        id: productId,
        name: newName,
        price: newPrice,
        count: newQuantity,
    };
    console.log(newProduct);
    const currentUserId = theMostCurrentUser !== null ? theMostCurrentUser.id : 0;
    productService.updateProduct(dispatch, newProduct, currentUserId);
  };

  const handleAddUser = () => {
    const newUser = {
      username: registerEmail,
      email: registerUsername,
      password: registerPassword,
    };
    authService.register(newUser);
    setAddUserModalVisible(false);
    setRegisterEmail('');
    setRegisterUsername('');
    setRegisterPassword('');
  };

  const handleLogInOperation = (logInEmail, logInPassword) => {
    const newLoginInfo = {
      "username": logInEmail,
      "password": logInPassword
    }
    authService.login(newLoginInfo).then((user) => {
      console.log(user)
      dispatch(login(user))
      cartService.getCart(dispatch, user.id);
      setLoggedIn(true);
    },
    (error) => {
      message.error("Данные введены неверно");
        const _content = (error.response && error.response.data) ||
            error.message ||
            error.toString();

        console.error(_content)
    });
    };
  
  const handleClickLogIn = () => {
    handleLogInOperation(logInEmail, logInPassword);
    setSwitchUserModalVisible(false);
    setLogInEmail('');
    setLogInPassword('');
  };

  const handleLogout = () => {
    authService.logout().then(() => {
      dispatch(logout());
      setLoggedIn(false);
    });
  };

  return (
    <div>
      <Layout>
      <Content style={{ paddingTop: '20px', paddingLeft: '20px' }}>
        <Row align="middle" gutter={[20, 0]} >
          <Col span={17}>
            <h2 className="text-start">Продукты</h2>
          </Col>
          <Col span={6}>
          <AutoComplete
          style={{ width: 290 }}
          >
            <Input.Search
            placeholder="Поиск продуктов"
            value={searchQuery}
            onSelect={handleSearchQueryChange}
            onChange={handleSearchQueryChange}
            style={{ width: 290 }}
          />
          </AutoComplete>
          </Col>
        </Row>
        <Row gutter={[20, 16]}>
          <Col className="text-center" span={17}>
            <ProductList
              addToCart={handleAddToCart}
              deleteProduct={handleDeleteProduct}
              changePrice={handleChangePrice}
              changeName={handleChangeName}
              changeQuantity={handleChangeQuantityInDB}
              changeProduct={handleChangeProduct}
            />
            {(theMostCurrentUser && theMostCurrentUser.roles[0] === "ROLE_ADMIN") && <Button type="primary" onClick={() => setAddProductModalVisible(true)} style={{ marginTop: '20px' }}>
              Добавить продукт
            </Button>}
          </Col>
          <Col>
            <Cart
              clearCart={handleClearCart}
              removeFromCart={handleRemoveFromCart}
              changeQuantity={handleChangeQuantity}
              doPayment={handlePayment}
            />
            {theMostCurrentUser && (
                <Card style={{ width: 290, marginTop: 20 }} >
                  <Meta
                    title={theMostCurrentUser.username}
                    description={theMostCurrentUser.email}
                    className="email"
                  />
                </Card>
              )}
          </Col>
        </Row>
      </Content>
    </Layout>
      <Modal
        title="Добавление продукта"
        visible={addProductModalVisible}
        onOk={handleAddProduct}
        onCancel={() => setAddProductModalVisible(false)}
      >
        <Input style={{ marginTop: 10 }}
          placeholder="Название"
          value={productName}
          onChange={(e) => setProductName(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Цена"
          value={productPrice}
          onChange={(e) => setProductPrice(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Количество"
          value={productQuantity}
          onChange={(e) => setProductQuantity(e.target.value)}
        />   
      </Modal>
    </div>
  );
};

export default App;
