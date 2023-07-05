import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.css';
import { Row, Col, Input, Button, Layout, Card, AutoComplete, Modal, Select } from 'antd';
import NavBar from './components/NavBar';
import ProductList from './components/ProductList';
import Cart from './components/Cart';
import {
  addToCart,
  deleteProduct,
  changePrice,
  changeName,
  clearCart,
  removeFromCart,
  changeQuantity,
  addProduct,
  searchProducts
} from './slices/productSlice';
import {
  switchUser, addUser
} from './slices/userSlice';

const { Content } = Layout;
const { Meta } = Card;
const { Option } = Select;

export const App = () => {
  const [addUserModalVisible, setAddUserModalVisible] = useState(false);
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [picture, setPicture] = useState('');

  const totalPrice = useSelector((state) => state.product.totalPrice);
  const cartItems = useSelector((state) => state.product.cartItems);
  const products = useSelector((state) => state.product.products);
  const searchQuery = useSelector((state) => state.product.searchQuery);
  const dispatch = useDispatch();

  const users = useSelector((state) => state.user.users);
  const currentUser = useSelector((state) => state.user.currentUser);

  useEffect(() => {
    dispatch(searchProducts(''));
  }, [dispatch]);

  const handleSearchQueryChange = (event) => {
    dispatch(searchProducts(event.target.value));
  };

  const handleAddToCart = (productName, productPrice) => {
    dispatch(addToCart({ name: productName, price: productPrice }));
  };

  const handleDeleteProduct = (productId) => {
    dispatch(deleteProduct(productId));
    dispatch(searchProducts(''));
  };

  const handleChangePrice = (productId, newPrice) => {
    dispatch(changePrice({ id: productId, price: newPrice }));
    dispatch(searchProducts(''));
  };

  const handleChangeName = (id, newName) => {
    dispatch(changeName({ id, name: newName }));
    dispatch(searchProducts(''));
  };

  const handleClearCart = () => {
    dispatch(clearCart());
  };

  const handleRemoveFromCart = (item) => {
    dispatch(removeFromCart(item.id));
  };

  const handleChangeQuantity = (item, quantity) => {
    dispatch(changeQuantity({ item, quantity }));
  };

  const handleAddProduct = () => {
    dispatch(addProduct());
    dispatch(searchProducts(''));
  };

  const handleSwitchUser = (userId) => {
    dispatch(switchUser(userId));
  };

  const handleAddUser = () => {
    const newUser = {
      name,
      email,
      picture
    };
    dispatch(addUser(newUser));
    setAddUserModalVisible(false);
    setName('');
    setEmail('');
    setPicture('');
  };

  return (
    <div>
      <NavBar />
      <Layout>
      <Content style={{ paddingTop: '20px', paddingLeft: '20px' }}>
        <Row align="middle" gutter={[20, 0]} >
          <Col span={17}>
            <h2 className="text-start">Продукты</h2>
          </Col>
          <Col span={6}>
          <AutoComplete
          options={products.filter(product => product.name.toLowerCase().includes(searchQuery.toLowerCase())).map((product) => ({ value: product.name }))}
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
            />
            <Button type="primary" onClick={handleAddProduct} style={{ marginTop: '20px' }}>
              Добавить продукт
            </Button>
          </Col>
          <Col>
            <Cart
              cartItems={cartItems} 
              totalPrice={totalPrice}
              clearCart={handleClearCart}
              removeFromCart={handleRemoveFromCart}
              changeQuantity={handleChangeQuantity}
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
                <h4>Сменить пользователя:</h4>
                <Select style={{ width: 200 }} onChange={handleSwitchUser} defaultValue={currentUser?.name}>
                  {users.map(user => (
                    <Option key={user.id} value={user.name}>{user.name}</Option>
                  ))}
                </Select>
              </div>
              <div style={{ marginTop: 20 }}>
                <Button type="primary" onClick={() => setAddUserModalVisible(true)}>
                  Добавить пользователя
                </Button>
              </div>
          </Col>
        </Row>
      </Content>
    </Layout>
      <Modal
        title="Add User"
        visible={addUserModalVisible}
        onOk={handleAddUser}
        onCancel={() => setAddUserModalVisible(false)}
      >
        <Input
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <Input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <Input
          placeholder="Picture URL/Path"
          value={picture}
          onChange={(e) => setPicture(e.target.value)}
        />
      </Modal>
    </div>
  );
};

export default App;
