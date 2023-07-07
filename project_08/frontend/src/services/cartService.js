import axios from "axios";
import {setCart} from "../slices/productSlice";
import authHeader from "./authHeader";
import { message } from "antd";

const API_URL = "http://localhost:8080/shopping-carts";

export const getCart = (dispatch, id) => {
    return axios.get(`${API_URL}/${id}`).then(
        (response) => {
            dispatch(setCart(response.data));
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();
            console.error(_content)
            dispatch(setCart([]));
        });
};

export const addProductToCart = (dispatch, idUser, product) => {
    return axios.post(API_URL + `/${idUser}`, product,  {headers: authHeader()}).then(
        (response) => {
            getCart(dispatch, idUser);
        },
        (error) => {
            message.error("Невозможно добавить товар");
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateProductInCart = (dispatch, idUser, productId, product) => {
    return axios.put(API_URL + `/${idUser}/product/${productId}`, product,  {headers: authHeader()}).then(
        (response) => {
            getCart(dispatch, idUser);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProductFromCart = (dispatch, userId, productId) => {
    return axios.delete(API_URL + `/${userId}/products/${productId}`,  {headers: authHeader()}).then(
        (response) => {
            getCart(dispatch, userId);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const cartService = {
    getCart, addProductToCart, updateProductInCart, deleteProductFromCart
};



export default cartService;