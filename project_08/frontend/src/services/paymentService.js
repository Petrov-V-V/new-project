import axios from "axios";
import {setCart} from "../slices/productSlice";
import productService from "./productService";
import cartService from '../services/cartService';

const API_URL = "http://localhost:8080/payment";

export const pay = (dispatch, payment, id) => {
    return axios.post(`${API_URL}`, payment).then(
      (response) => {
        productService.getProducts(dispatch);
        cartService.getCart(dispatch, id);
    },
    (error) => {
        const _content = (error.response && error.response.data) ||
            error.message ||
            error.toString();

        console.error(_content)
    });
};

const paymentService = {
  pay
};

export default paymentService