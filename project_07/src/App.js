import React, { useEffect  } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.css';
import { Row, Col, Input, Button, Layout, Card, AutoComplete } from 'antd';
import NavBar from './components/NavBar';
import ProductList from './components/ProductList';
import Search from './components/Search';
import Cart from './components/Cart';
import {
  addToCart,
  deleteProduct,
  changePrice,
  changeName,
  clearCart,
  removeFromCart,
  changeQuantity,
  setSearchQuery,
  addProduct,
  searchProducts
} from './slices/productSlice';
import LordImage from "./slices/img/LordOfTheHouse.jpg";

const { Content } = Layout;
const { Meta } = Card;

export const App = () => {

  const totalPrice = useSelector((state) => state.product.totalPrice);
  const cartItems = useSelector((state) => state.product.cartItems);
  const products = useSelector((state) => state.product.products);
  const searchQuery = useSelector((state) => state.product.searchQuery);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(searchProducts(''));
  }, [dispatch]);

  const handleSearchQueryChange = (event) => {
    dispatch(searchProducts(event.target.value));
  };

  const handleSearch = (event) => {
    dispatch(setSearchQuery(event.target.value));
  };

  const handleAddToCart = (productName, productPrice) => {
    dispatch(addToCart({ name: productName, price: productPrice }));
  };

  const handleDeleteProduct = (productId) => {
    dispatch(deleteProduct(productId));
  };

  const handleChangePrice = (productId, newPrice) => {
    dispatch(changePrice({ id: productId, price: newPrice }));
  };

  const handleChangeName = (id, newName) => {
    dispatch(changeName({ id, name: newName }));
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
            <Card style={{ width: 290, marginTop: 20 }} cover={
              <img class="resize-image"
                alt="Lord of the House"
                src={LordImage}
              />}
              >
              < Meta
                title="LordOfTheHouse"
                description="dun@ge.on"
                className="email"
              />
            </Card>
          </Col>
        </Row>
      </Content>
    </Layout>
    </div>
  );
};

export default App;
