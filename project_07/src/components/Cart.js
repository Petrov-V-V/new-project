import React from 'react';
import { Card, Button, InputNumber, List, Row, Col } from 'antd';

const Cart = ({ cartItems, totalPrice, clearCart, removeFromCart, changeQuantity }) => {
  const handleRemoveFromCart = (item) => {
    removeFromCart(item);
  };

  const handleChangeQuantity = (item, quantity) => {
    changeQuantity(item, quantity);
  };

  const handlePayment = () => {
    if (cartItems.length === 0) {
      window.alert('Корзина пуста!');
    } else {
      window.alert('Оплата произведена!');
      clearCart();
    }
  };
  
  return (
    <Card style={{ width: '18rem' }}>
      <Card.Meta
        title={<div style={{ textAlign: 'center' }}>Корзина</div>}
        description={
          <List>
            {cartItems.length === 0 ? (
              <p style={{ textAlign: 'center' }}>Пусто</p>
            ) : (
              cartItems.map((item) => (
                <Card key={item.id}>
                    <div className="name">{item.name}</div>
                    <div className="price rub">
                      {item.price} за {item.quantity} шт.
                    </div>
                    <Row justify="space-between" align="middle">
                    <Col>
                        <Button danger onClick={() => handleRemoveFromCart(item)}>
                          Удалить
                        </Button>
                        </Col>
                        <Col>
                        <InputNumber
                          min={1}
                          value={item.quantity}
                          onChange={(value) => handleChangeQuantity(item, value)}
                        />
                      </Col>
                    </Row>
                </Card>
              ))
            )}
          </List>
        }
      />
      <b>Всего: </b>
      <span className="total-price rub">{totalPrice}</span>
      <Row justify="space-between" align="middle">
        <Button onClick={clearCart}>Очистить</Button>
        <Button onClick={handlePayment}>Оплатить</Button>
      </Row>
    </Card>
    );
}

export default Cart;