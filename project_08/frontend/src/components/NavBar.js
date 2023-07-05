import React from 'react';
import { Layout, Menu } from 'antd';

const { Header } = Layout;

const NavBar = () => {
  return (
    <Layout>
      <Header style={{ background: 'body-tertiary', paddingLeft: '40px' }}>
        <div className="logo" style={{ fontSize: '32px', color: '#fff' }}>
          Хрум-Хрум
        </div>
      </Header>
    </Layout>
  );
}

export default NavBar;