import React from 'react';
import NavBar from './components/NavBar';
import ProductList from './components/ProductList';
import Cart from './components/Cart';
import 'bootstrap/dist/css/bootstrap.css';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';
import AppleImage from "./img/apple.png";
import OrangeImage from "./img/orange.png";
import BananaImage from "./img/banana.png";
import WatermelonImage from "./img/watermelon.png";
import PeachImage from "./img/peach.png";
import PearImage from "./img/pear.png";
import LordImage from "./img/LordOfTheHouse.jpg";
import NothingImage from "./img/nothing.png";

class App extends React.Component {
  state = {
    cartItems: [],
    totalPrice: 0,
    products: [
      { id: 1, name: 'Яблоко', price: 30, image: AppleImage },
      { id: 2, name: 'Апельсин', price: 80, image: OrangeImage },
      { id: 3, name: 'Банан', price: 70, image: BananaImage },
      { id: 4, name: 'Арбуз', price: 150, image: WatermelonImage },
      { id: 5, name: 'Персик', price: 70, image: PeachImage },
      { id: 6, name: 'Груша', price: 60, image: PearImage }
    ],
    searchQuery: '' 
  };

  handleSearch = (event) => {
    this.setState({ searchQuery: event.target.value });
  };

  addToCart = (productName, productPrice) => {
    const { cartItems } = this.state;
  
    const existingItem = cartItems.find(
      item => item.name === productName && item.price === productPrice
    );
  
    if (existingItem) {
      const updatedCartItems = cartItems.map(item => {
        if (item.id === existingItem.id) {
          return { ...item, quantity: item.quantity + 1 };
        }
        return item;
      });
  
      this.setState(prevState => ({
        cartItems: updatedCartItems,
        totalPrice: prevState.totalPrice + productPrice
      }));
    } else {
      const cartItem = {
        id: Date.now(),
        name: productName,
        price: productPrice,
        quantity: 1
      };
  
      this.setState(prevState => ({
        cartItems: [...prevState.cartItems, cartItem],
        totalPrice: prevState.totalPrice + productPrice
      }));
    }
  };
  

  clearCart = () => {
    this.setState({
      cartItems: [],
      totalPrice: 0
    });
  };

  addProduct = () => {
    const { products } = this.state;
    const newProductId = products.length + 1;

    const newProduct = {
      id: newProductId,
      name: `Product ${newProductId}`,
      price: 0,
      image: NothingImage
    };

    this.setState(prevState => ({
      products: [...prevState.products, newProduct]
    }));
  };

  deleteProduct = productId => {
    const { products } = this.state;

    this.setState(prevState => ({
      products: prevState.products.filter(product => product.id !== productId)
    }));
  };

  changePrice = (productId, newPrice) => {
    const { products } = this.state;

    this.setState(prevState => ({
      products: prevState.products.map(product =>
        product.id === productId ? { ...product, price: newPrice } : product
      )
    }));
  };

  clearCart = () => {
    this.setState({
      cartItems: [],
      totalPrice: 0
    });
  };

  removeFromCart = (item) => {
    const { cartItems, totalPrice } = this.state;
    const updatedCartItems = cartItems.filter(cartItem => cartItem.id !== item.id);
    const updatedTotalPrice = totalPrice - (item.price * item.quantity);

    this.setState({
      cartItems: updatedCartItems,
      totalPrice: updatedTotalPrice
    });
  };

  changeQuantity = (item, quantity) => {
    const { cartItems } = this.state;
  
    const updatedCartItems = cartItems.map((cartItem) => {
      if (cartItem.id === item.id) {
        return { ...cartItem, quantity: parseInt(quantity) };
      }
      return cartItem;
    });
  
    const updatedTotalPrice = updatedCartItems.reduce(
      (total, cartItem) => total + cartItem.price * cartItem.quantity,
      0
    );
  
    this.setState({
      cartItems: updatedCartItems,
      totalPrice: updatedTotalPrice,
    });
  };

  changeName = (id, newName) => {
    const { products } = this.state;
    const updatedProducts = products.map(product => {
      if (product.id === id) {
        return { ...product, name: newName };
      }
      return product;
    });
    this.setState({ products: updatedProducts });
  };

  clearCart = () => {
    this.setState({ cartItems: [], totalPrice: 0 });
  };

  render() {
    const { cartItems, totalPrice, products, searchQuery } = this.state;

    const filteredProducts = products.filter((product) =>
      product.name.toLowerCase().includes(searchQuery.toLowerCase())
    );

    <div class="input-group">
    <div class="form-outline">
      <input type="search" id="form1" class="form-control"
          placeholder="Поиск продуктов"
          value={searchQuery}
          onChange={this.handleSearch} />
    </div>
  </div>
    return (
      <div>
        <NavBar />
        <Container style={{ paddingTop: '20px' }}>
          <Row className="align-items-center">
            <Col>
              <h2 className="text-start">Продукты</h2>
            </Col>
            <Col lg={3} className="text-end">
            <div className="input-group">
              <div className="form-outline search-bar">
                <input
                  type="search"
                  id="form1"
                  className="form-control"
                  placeholder="Поиск продуктов"
                  value={searchQuery}
                  onChange={this.handleSearch}
                />
              </div>
            </div>
            </Col>
          </Row>
          <Row >
            <Col className="text-center">
              <ProductList
                products={filteredProducts}
                addToCart={this.addToCart}
                deleteProduct={this.deleteProduct}
                changePrice={this.changePrice}
                changeName={this.changeName}
              />
              <Button variant="primary" onClick={this.addProduct}>
                Добавить продукт
              </Button>
            </Col>
            <Col lg={3}>
              <Cart cartItems={cartItems} totalPrice={totalPrice} clearCart={this.clearCart} removeFromCart={this.removeFromCart} changeQuantity={this.changeQuantity} />
              <Card style={{ width: '290px', marginTop: '20px' }} >
                <Card.Img variant="top" src={LordImage}/>
                <Card.Body>
                  <Card.Title className="LordOfTheHouse">LordOfTheHouse</Card.Title>
                  <Card.Title className="email">dun@ge.on</Card.Title>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default App;