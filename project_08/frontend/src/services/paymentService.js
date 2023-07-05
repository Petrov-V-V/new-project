import axios from "axios";
import { getUser } from "./userService";
import productService from "./productService";

const API_URL = "http://localhost:8080/payment";

export const pay = (dispatch, payment, idUser) => {
    return axios.post(`${API_URL}`, payment).then(
      (response) => {
        getUser(dispatch, idUser);
        productService.getProducts(dispatch);
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