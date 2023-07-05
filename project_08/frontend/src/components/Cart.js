import React from 'react';
import { Card, Button, InputNumber, List, Row, Col } from 'antd';

const Cart = ({ cartItems, clearCart, removeFromCart, changeQuantity, doPayment }) => {
  const handleRemoveFromCart = (item) => {
    removeFromCart(item);
  };

  const handleChangeQuantity = (id, quantity) => {
    changeQuantity(id, quantity);
  };

  const handlePayment = () => {
    if (cartItems.length === 0) {
      window.alert('Корзина пуста!');
    } else {
      window.alert('Оплата произведена!');
      doPayment();
    }
  };

  let totalPrice = 0;

for (const product of cartItems) {
  totalPrice += product.price * product.count;
}
  
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
                      {item.price} за {item.count} шт.
                    </div>
                    <Row justify="space-between" align="middle">
                    <Col>
                        <Button danger onClick={() => handleRemoveFromCart(item.id)}>
                          Удалить
                        </Button>
                        </Col>
                        <Col>
                        <InputNumber
                          min={1}
                          value={item.count}
                          onChange={(value) => handleChangeQuantity(item.id, value)}
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