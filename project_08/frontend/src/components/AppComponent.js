import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.css';
import { Row, Col, Input, Button, Layout, Card, AutoComplete, Modal, Select } from 'antd';
import ProductList from './ProductList';
import Cart from './Cart';
import {
  searchProducts
} from '../slices/productSlice';
import productService from '../services/productService';
import userService from '../services/userService';
import cartService from '../services/cartService';
import paymentService from '../services/paymentService';


const { Content } = Layout;
const { Meta } = Card;


function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

export const App = () => {
  const [selectedUser, setSelectedUser] = useState('');
  const [userForDelete, setUserForDelete] = useState('');

  const [switchUserModalVisible, setSwitchUserModalVisible] = useState(false);
  const [logInEmail, setLogInEmail] = useState('');
  const [logInPassword, setLogInPassword] = useState('');  
  const [addProductModalVisible, setAddProductModalVisible] = useState(false);
  const [productName, setProductName] = useState('');
  const [productPrice, setProductPrice] = useState('');
  const [productQuantity, setProductQuantity] = useState('');
  const [addUserModalVisible, setAddUserModalVisible] = useState(false);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');

  const totalPrice = useSelector((state) => state.product.totalPrice);
  const cartItems = useSelector((state) => state.product.cartItems);
  const products = useSelector((state) => state.product.products);
  const searchQuery = useSelector((state) => state.product.searchQuery);
  const dispatch = useDispatch();

  const users = useSelector((state) => state.user.users);
  const currentUser = useSelector((state) => state.user.currentUser);

  useEffect(() => {
    productService.getProducts(dispatch);
  }, []);

  useEffect(() => {
    dispatch(searchProducts(''));
  }, [dispatch]);

  const handleSearchQueryChange = (event) => {
    dispatch(searchProducts(event.target.value));
  };

  const handlePayment = () => {
    const newInfo = {
      "cardNumber": "2135434165",
      "userId": currentUser.id
    }
    paymentService.pay(dispatch, newInfo, currentUser.id);
  };

  const handleAddToCart = (productId) => {
    const newProductId = {
      id: productId
    };
    cartService.addProductToCart(dispatch, currentUser.id, newProductId);
  };

  const handleClearCart = () => {
    for (const product of cartItems) {
      handleRemoveFromCart(product.id);
    }
  };

  const handleRemoveFromCart = (productId) => {
    cartService.deleteProductFromCart(dispatch, currentUser.id, productId);
  };

  const handleChangeQuantity = (id, quantity) => {
    const newProductQuantity = {
      "count": quantity
    };
    cartService.updateProductInCart( dispatch, currentUser.id, id, newProductQuantity );
  };

  const handleAddProduct = () => {
    const newProduct = {
        name: productName,
        price: productPrice,
        count: productQuantity,
    };
    productService.createProduct(dispatch, newProduct);
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
    productService.updateProduct(dispatch, newProduct);
  };

  const handleChangeName = (productId, newName) => {
    const oldProduct = products.find(product => product.id === productId)
    const newProduct = {
        id: productId,
        name: newName,
        price: oldProduct.price,
        count: oldProduct.count,
    };
    productService.updateProduct(dispatch, newProduct);
  };

  const handleChangeQuantityInDB = (productId, newQuantity) => {
    const oldProduct = products.find(product => product.id === productId)
    const newProduct = {
        id: productId,
        name: oldProduct.name,
        price: oldProduct.price,
        count: newQuantity,
    };
    console.log(newProduct);
    productService.updateProduct(dispatch, newProduct);
  };

  const handleAddUser = () => {
    const newUser = {
      name: name,
      email: email,
      username: login,
      password: password,
    };
    userService.createUser(dispatch, newUser);
    setAddUserModalVisible(false);
    setName('');
    setEmail('');
    setLogin('');
    setPassword('');
  };

  const handleSwitchUser = (userId) => {
    userService.getUser(dispatch, userId);
  };
  
  const handleClick = () => {
    handleSwitchUser(selectedUser);
  };

  const handleLogInOperation = (logInEmail, logInPassword) => {
    const newLoginInfo = {
      "email": logInEmail,
      "password": logInPassword
    }
    console.log(newLoginInfo);
    userService.getUserByEmail(dispatch, newLoginInfo);
  };
  
  const handleClickLogIn = () => {
    handleLogInOperation(logInEmail, logInPassword);
    setSwitchUserModalVisible(false);
    setLogInEmail('');
    setLogInPassword('');
  };

  const handleDeleteUser = (userId) => {
    userService.deleteUser(dispatch, userId);
  };
  
  const handleClickDelete = () => {
    handleDeleteUser(userForDelete);
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
            />
            <Button type="primary" onClick={() => setAddProductModalVisible(true)} style={{ marginTop: '20px' }}>
              Добавить продукт
            </Button>
          </Col>
          <Col>
            <Cart
              cartItems={cartItems} 
              clearCart={handleClearCart}
              removeFromCart={handleRemoveFromCart}
              changeQuantity={handleChangeQuantity}
              doPayment={handlePayment}
            />
            {currentUser && (
                <Card style={{ width: 290, marginTop: 20 }} cover={
                  <img
                    className="resize-image"
                    alt={currentUser.name}
                    src={currentUser.picture}
                  />}
                >
                  <Meta
                    title={currentUser.name}
                    description={currentUser.email}
                    className="email"
                  />
                </Card>
              )}
              <div style={{ marginTop: 20 }}>
                <h4>Действия с пользователем:</h4>
                {/*
                <Input
                  placeholder="Id"
                  style={{ width: 200 }}
                  value={selectedUser}
                  onChange={e => setSelectedUser(e.target.value)}
                />
                <Button type="primary" onClick={handleClick} style={{ marginTop: 10 }}>
                  Сменить
                </Button>
                <div style={{ marginTop: 20 }}>
                <Input
                  placeholder="Id"
                  style={{ width: 200 }}
                  value={userForDelete}
                  onChange={e => setUserForDelete(e.target.value)}
                />
                  <Button type="primary" onClick={handleClickDelete} style={{ marginTop: 10 }}>
                    Удалить
                  </Button>
                </div> */}
                <Row  gutter={[1,1]} justify="space-between" align="middle" style={{ marginTop: 20 }}>
                  <Col></Col>
                  <Button type="primary" onClick={() => setSwitchUserModalVisible(true)}>
                    Вход
                  </Button>
                  <Button type="primary" onClick={() => setAddUserModalVisible(true)}>
                    Регистрация
                  </Button>
                  <Col></Col>
                  </Row>
              </div>
          </Col>
        </Row>
      </Content>
    </Layout>
      <Modal
        title="Регистрация"
        visible={addUserModalVisible}
        onOk={handleAddUser}
        onCancel={() => setAddUserModalVisible(false)}
      >
        <Input style={{ marginTop: 10 }}
          placeholder="Имя"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Логин"
          value={login}
          onChange={(e) => setLogin(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Пароль"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />        
      </Modal>
      <Modal
        title="Вход"
        visible={switchUserModalVisible}
        onOk={handleClickLogIn}
        onCancel={() => setSwitchUserModalVisible(false)}
      >
        <Input style={{ marginTop: 10 }}
          placeholder="Email"
          value={logInEmail}
          onChange={(e) => setLogInEmail(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Пароль"
          value={logInPassword}
          onChange={(e) => setLogInPassword(e.target.value)}
        />    
      </Modal>
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
