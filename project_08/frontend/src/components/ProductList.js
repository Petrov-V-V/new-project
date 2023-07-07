import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { Form, Input, Card, Row, Col, Button } from 'antd';

const { Meta } = Card;


const ProductList = ({ addToCart, deleteProduct, changePrice, changeName, changeQuantity, changeProduct }) => {
  const products = useSelector((state) => state.product.filteredProducts);

  const [editingProductId, setEditingProductId] = useState(null);
  const [newQuantity, setNewQuantity] = useState('');
  const [newPrice, setNewPrice] = useState('');
  const [newName, setNewName] = useState({});
  
  const theMostCurrentUser = useSelector((state) => state.auth.user);

  const handleEditSubmit = (productId, price, name, quantity) => {
    if (price) {
      price = parseInt(price, 10);
      setNewPrice(prevState => ({
        ...prevState,
        [productId]: ''
      }));
    } 
    if (name) {
      setNewName(prevState => ({
        ...prevState,
        [productId]: ''
      }));
    }
    if (quantity) {
      quantity = parseInt(quantity, 10);
      setNewQuantity(prevState => ({
        ...prevState,
        [productId]: ''
      }));
    }

    changeProduct(productId, price, name, quantity)
    setEditingProductId(null);
    setNewPrice((prevPrices) => ({ ...prevPrices, [productId]: undefined }));
    setNewName((prevNames) => ({ ...prevNames, [productId]: undefined }));
    setNewQuantity((prevQuantities) => ({ ...prevQuantities, [productId]: undefined }));
  };

    const handlePriceChange = (productId, price) => {
        setNewPrice(prevState => ({
          ...prevState,
          [productId]: price
        }));
      };

      const handleNameChange = (productId, name) => {
        setNewName(prevState => ({
          ...prevState,
          [productId]: name
        }));
      };

      const handleQuantityChange = (productId, quantity) => {
        setNewQuantity(prevState => ({
          ...prevState,
          [productId]: quantity
        }));
      };
  
      return (
        <Row gutter={[16, 16]}>
  {products.map((product) => (
    <Col key={product.id}>
      <Card style={{ width: 200 }} cover={<img alt={product.name} src={product.image} />}>
        <Meta
          title={product.name}
          description={
            <p>
              <p className="price rub">{product.price} ₽</p>
              <p>{product.count} шт.</p>
            </p>
          }
        />
        {editingProductId === product.id ? (
          <>
            <Form.Item style={{ marginTop: '10px' }} name={`priceInput${product.id}`}>
              <Input
                type="number"
                placeholder="Новая цена"
                value={newPrice[product.id] || ''}
                onChange={(e) => handlePriceChange(product.id, e.target.value)}
              />
            </Form.Item>
            <Form.Item style={{ marginTop: '-15px' }} controlId={`nameInput${product.id}`}>
              <Input
                type="text"
                placeholder="Новое имя"
                value={newName[product.id] || ''}
                onChange={(e) => handleNameChange(product.id, e.target.value)}
              />
            </Form.Item>
            <Form.Item style={{ marginTop: '-15px', marginBottom: '-10px' }} name={`quantityInput${product.id}`}>
              <Input
                type="number"
                placeholder="Новое количество"
                value={newQuantity[product.id] || ''}
                onChange={(e) => handleQuantityChange(product.id, e.target.value)}
              />
            </Form.Item>
            <Button
              style={{ marginTop: '15px', marginBottom: '-5px' }}
              onClick={() => handleEditSubmit(product.id, newPrice[product.id], newName[product.id], newQuantity[product.id])}
            >
              Завершить
            </Button>
          </>
        ) : (
          <>
            <Button type="primary" onClick={() => addToCart(product.id)}>
              В корзину
            </Button>
            {(theMostCurrentUser && theMostCurrentUser.roles[0] === "ROLE_ADMIN") && <Button danger style={{ marginTop: '10px' }} onClick={() => deleteProduct(product.id)}>
              Удалить продукт
            </Button>}
            {(theMostCurrentUser && theMostCurrentUser.roles[0] === "ROLE_ADMIN") && <Button style={{ marginTop: '10px' }} onClick={() => setEditingProductId(product.id)}>
              Редактировать
            </Button>}
          </>
        )}
      </Card>
    </Col>
  ))}
</Row>
      );
    };
  
  export default ProductList;