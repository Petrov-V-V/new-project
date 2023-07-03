import React from 'react';
import { Card, Button, Form } from 'react-bootstrap';

const Cart = ({ cartItems, totalPrice, clearCart, removeFromCart, changeQuantity }) => {
  const handleRemoveFromCart = (item) => {
    removeFromCart(item);
  };

  const handleChangeQuantity = (item, quantity) => {
    changeQuantity(item, quantity);
  };

  const handlePayment = () => {
    window.alert('Оплата произведена!');
  };

  return (
    <Card style={{ width: '18rem' }}>
      <Card.Body> 
        <Card.Title className="name text-center">Корзина</Card.Title>
        <div className="list">
          {cartItems.length === 0 ? (
            <p className="empty card-text text-center">Пусто</p>
          ) : (
            cartItems.map((item) => (
              <div className="card item" key={item.id}>
                <div className="card-body">
                  <Card.Title className="name">{item.name}</Card.Title>
                  <Card.Text className="price rub">
                    {item.price} за {item.quantity} шт.
                  </Card.Text>
                  <div className="d-flex justify-content-between align-items-center">
                    <div>
                      <Button variant="danger" onClick={() => handleRemoveFromCart(item)}>Удалить</Button>
                    </div>
                    <div>
                      <Form.Control
                        type="number"
                        min="1"
                        value={item.quantity}
                        onChange={(e) => handleChangeQuantity(item, e.target.value)}
                      />
                    </div>
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
        <p className="card-text text-start">
          <b>Всего: </b>
          <span className="total-price rub">{totalPrice}</span>
        </p>
        <Button variant="primary card-link" onClick={clearCart}>
          Очистить
        </Button>
        <Button variant="primary card-link" onClick={handlePayment}>Оплатить</Button>
      </Card.Body>
    </Card>
  );
}

export default Cart;
