import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Form, Input, Card, Row, Col, Button } from 'antd';
const { Meta } = Card;

const ProductList = ({ addToCart, deleteProduct, changePrice, changeName }) => {
  const products = useSelector((state) => state.product.filteredProducts);
  const dispatch = useDispatch();

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
        <Row gutter={[16, 16]}>
          {products.map((product) => (
            <Col key={product.id}>
              <Card style={{ width: 200 }} cover={
              <img
                alt={product.name}
                src={product.image}
              />}>
                < Meta
                title={product.name}
                description={<p className="price rub">{product.price} ₽</p>}
              />
                  <Button type="primary" onClick={() => addToCart(product.name, product.price)}>
                    В корзину
                  </Button>
                  <Button danger style={{ marginTop: '10px' }} onClick={() => deleteProduct(product.id)}>
                    Удалить продукт
                  </Button>
                  <Form.Item style={{ marginTop: '10px' }} name={`priceInput${product.id}`}>
                    <Input
                      type="number"
                      placeholder="Новая цена"
                      value={newPrice[product.id] || ''}
                      onChange={e => handlePriceChange(product.id, e.target.value)}
                    />
                    <Button style={{ marginTop: '2px' }} onClick={() => handlePriceSubmit(product.id, newPrice[product.id])}>
                      Изменить цену
                    </Button>
                  </Form.Item>
                  <Form.Item style={{ marginTop: '-15px', marginBottom: '-10px' }} controlId={`nameInput${product.id}`}>
                    <Input
                      type="text"
                      placeholder="Новое имя"
                      value={newName[product.id] || ''}
                      onChange={e => handleNameChange(product.id, e.target.value)}
                    />
                    <Button style={{ marginTop: '2px' }} onClick={() => handleNameSubmit(product.id, newName[product.id])}>
                      Изменить имя
                    </Button>
                  </Form.Item>
              </Card>
            </Col>
          ))}
        </Row>
      );
    };
  
  export default ProductList;