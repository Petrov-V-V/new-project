import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Row, Col, Input, Button, Layout, Card, AutoComplete, Modal, Select, message, Menu } from 'antd';
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


const { Header } = Layout;

const NavBar = () => {
  const [loggedIn, setLoggedIn] = useState(false);

  const [switchUserModalVisible, setSwitchUserModalVisible] = useState(false);
  const [logInEmail, setLogInEmail] = useState('');
  const [logInPassword, setLogInPassword] = useState('');  
  const [addUserModalVisible, setAddUserModalVisible] = useState(false);
  const [registerEmail, setRegisterEmail] = useState('');
  const [registerUsername, setRegisterUsername] = useState('');
  const [registerPassword, setRegisterPassword] = useState('');

  const theMostCurrentUser = useSelector((state) => state.auth.user);

  useEffect(() => {
    if(theMostCurrentUser !== null){
      setLoggedIn(true);
      cartService.getCart(dispatch, theMostCurrentUser.id);
    }
  }, []);

  const dispatch = useDispatch();
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
      cartService.getCart(dispatch);
    });
  };

  return (
    <Layout>
      <Header style={{ background: 'body-tertiary', paddingLeft: '40px', display: 'flex',
                alignItems: 'center',
                justifyContent: 'space-between',}}>
        <p className="logo" style={{ fontSize: '32px', color: '#fff' }}>
          Хрум-Хрум
        </p>
        <div style={{ textAlign: 'right' }}>         
          <Button
            type="primary"
            onClick={() => setSwitchUserModalVisible(true)}
            style={{ marginRight: '15px', display: loggedIn ? 'none' : 'inline-block' }}
          >
            Вход
          </Button>
          <Button
            type="primary"
            onClick={() => setAddUserModalVisible(true)}
            style={{ display: loggedIn ? 'none' : 'inline-block' }}
          >
            Регистрация
          </Button>
          <Button type="primary" onClick={handleLogout} style={{ display: loggedIn ? 'inline-block' : 'none' }}>
            Выход
          </Button>
                </div>
      </Header>
      <Modal
        title="Регистрация"
        visible={addUserModalVisible}
        onOk={handleAddUser}
        onCancel={() => setAddUserModalVisible(false)}
      >
        <Input style={{ marginTop: 10 }}
          placeholder="Email"
          value={registerEmail}
          onChange={(e) => setRegisterEmail(e.target.value)}
        />
        <Input style={{ marginTop: 10 }}
          placeholder="Логин" 
          value={registerUsername}
          onChange={(e) => setRegisterUsername(e.target.value)}
        />
        <Input.Password style={{ marginTop: 10 }}
          placeholder="Пароль"
          value={registerPassword}
          onChange={(e) => setRegisterPassword(e.target.value)}
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
        <Input.Password style={{ marginTop: 10 }}
          placeholder="Пароль"
          value={logInPassword}
          onChange={(e) => setLogInPassword(e.target.value)}
        />    
      </Modal>
    </Layout>
      
  );
}

export default NavBar;