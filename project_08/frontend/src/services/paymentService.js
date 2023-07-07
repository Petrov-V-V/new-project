import axios from "axios";
import {setCart} from "../slices/productSlice";
import productService from "./productService";
import cartService from '../services/cartService';
import { message } from "antd";
import authHeader from "./authHeader";

const API_URL = "http://localhost:8080/payment";

export const pay = (dispatch, payment, id) => {
    return axios.post(`${API_URL}`, payment,  {headers: authHeader()}).then(
      (response) => {
        productService.getProducts(dispatch);
        cartService.getCart(dispatch, id);
        message.success("Оплата успешно произведена!");
    },
    (error) => {
      message.error("Произошла ошибка при проведении оплаты");
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