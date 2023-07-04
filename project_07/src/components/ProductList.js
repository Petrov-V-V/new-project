import React, { useState } from 'react';
import { Row, Col, Card, Button, Form } from 'react-bootstrap';

const ProductList = ({ products, addToCart, deleteProduct, changePrice, changeName }) => {
    const [newPrice, setNewPrice] = useState('');
    const [newName, setNewName] = useState({});
  
    const handlePriceChange = (productId, price) => {
        setNewPrice(prevState => ({
          ...prevState,
          [productId]: price
        }));
      };
    
      const handlePriceSubmit = (productId, price) => {
        if (price) {
          changePrice(productId, parseInt(price, 10));
          setNewPrice(prevState => ({
            ...prevState,
            [productId]: ''
          }));
        }
      };

      const handleNameChange = (productId, name) => {
        setNewName(prevState => ({
          ...prevState,
          [productId]: name
        }));
      };
    
      const handleNameSubmit = (productId, name) => {
        if (name) {
          changeName(productId, name);
          setNewName(prevState => ({
            ...prevState,
            [productId]: ''
          }));
        }
      };
  
      return (
        <div className="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4">
          {products.map((product) => (
            <div key={product.id} className="col">
              <Card>
                <Card.Img variant="top" src={product.image} />
                <Card.Body>
                  <Card.Title className="name">{product.name}</Card.Title>
                  <p className="price rub">{product.price}</p>
                  <Button variant="primary" onClick={() => addToCart(product.name, product.price)}>
                    В корзину
                  </Button>
                  <Button variant="danger" onClick={() => deleteProduct(product.id)}>
                    Удалить продукт
                  </Button>
                  <Form.Group controlId={`priceInput${product.id}`}>
                    <Form.Control
                      type="number"
                      placeholder="Новая цена"
                      value={newPrice[product.id] || ''}
                      onChange={e => handlePriceChange(product.id, e.target.value)}
                    />
                    <Button variant="secondary" onClick={() => handlePriceSubmit(product.id, newPrice[product.id])}>
                      Изменить цену
                    </Button>
                  </Form.Group>
                  <Form.Group controlId={`nameInput${product.id}`}>
                    <Form.Control
                      type="text"
                      placeholder="Новое имя"
                      value={newName[product.id] || ''}
                      onChange={e => handleNameChange(product.id, e.target.value)}
                    />
                    <Button variant="secondary" onClick={() => handleNameSubmit(product.id, newName[product.id])}>
                      Изменить имя
                    </Button>
                  </Form.Group>
                </Card.Body>
              </Card>
            </div>
          ))}
        </div>
      );
    };
  
  export default ProductList;