import AppleImage from "./img/apple.png";
import OrangeImage from "./img/orange.png";
import BananaImage from "./img/banana.png";
import WatermelonImage from "./img/watermelon.png";
import PeachImage from "./img/peach.png";
import PearImage from "./img/pear.png";
import NothingImage from "./img/nothing.png";
import { createSlice } from '@reduxjs/toolkit';

export const productSlice = createSlice({
  name: 'product',
  initialState: {
    cartItems: [],
    totalPrice: 0,
    products: [
      // { id: 1, name: 'Яблоко', price: 30, image: AppleImage },
      // { id: 2, name: 'Апельсин', price: 80, image: OrangeImage },
      // { id: 3, name: 'Банан', price: 70, image: BananaImage },
      // { id: 4, name: 'Арбуз', price: 150, image: WatermelonImage },
      // { id: 5, name: 'Персик', price: 70, image: PeachImage },
      // { id: 6, name: 'Груша', price: 60, image: PearImage }
    ],
    searchQuery: '',
    filteredProducts: [],
  },
  reducers: {
    setCart: (state, action) => {
      state.cartItems = action.payload;
    },
    set: (state, action) => {
      state.products = action.payload;
    },
    searchProducts: (state, action) => {
      state.searchQuery = action.payload;
      state.filteredProducts = state.products.filter((product) =>
        product.name.toLowerCase().includes(state.searchQuery.toLowerCase())
      );
    },

    addToCart: (state, action) => {
      const { name, price } = action.payload;
      const existingItem = state.cartItems.find(
        item => item.name === name && item.price === price
      );

      if (existingItem) {
        state.cartItems = state.cartItems.map(item => {
          if (item.id === existingItem.id) {
            return { ...item, quantity: item.quantity + 1 };
          }
          return item;
        });

        state.totalPrice += price;
      } else {
        const cartItem = {
          id: Date.now(),
          name: name,
          price: price,
          quantity: 1
        };

        state.cartItems.push(cartItem);
        state.totalPrice += price;
      }
    },

    deleteProduct: (state, action) => {
      const productId = action.payload;
      state.products = state.products.filter(product => product.id !== productId);
    },

    changePrice: (state, action) => {
      const { id, price: newPrice } = action.payload;
      state.products = state.products.map(product =>
        product.id === id ? { ...product, price: newPrice } : product
      );
    },

    changeName: (state, action) => {
      const { id, name: newName } = action.payload;
      state.products = state.products.map(product =>
        product.id === id ? { ...product, name: newName } : product
      );
    },

    clearCart: (state) => {
      state.cartItems = [];
      state.totalPrice = 0;
    },

    removeFromCart: (state, action) => {
      const itemId = action.payload;
      const item = state.cartItems.find(cartItem => cartItem.id === itemId);
      if (item) {
        state.cartItems = state.cartItems.filter(cartItem => cartItem.id !== itemId);
        state.totalPrice -= item.price * item.quantity;
      }
    },

    changeQuantity: (state, action) => {
      const { item, quantity } = action.payload;
      state.cartItems = state.cartItems.map((cartItem) => {
        if (cartItem.id === item.id) {
          return { ...cartItem, quantity: parseInt(quantity) };
        }
        return cartItem;
      });

      state.totalPrice = state.cartItems.reduce(
        (total, cartItem) => total + cartItem.price * cartItem.quantity,
        0
      );
    },

    setSearchQuery: (state, action) => {
      state.searchQuery = action.payload;
    },

    addProduct: (state) => {
      const newProductId = state.products.length + 1;

      const newProduct = {
        id: newProductId,
        name: `Product ${newProductId}`,
        price: 0,
        image: NothingImage
      };

      state.products.push(newProduct);
    }
  }
});

export const {
  addToCart,
  deleteProduct,
  changePrice,
  changeName,
  clearCart,
  removeFromCart,
  changeQuantity,
  setSearchQuery,
  addProduct,
  searchProducts,
  set,
  setCart
} = productSlice.actions;

export default productSlice.reducer;
