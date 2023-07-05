import axios from "axios";
import {getUser} from "./userService";

const API_URL = "http://localhost:8080/shopping-carts";


export const addProductToCart = (dispatch, idUser, product) => {
    return axios.post(API_URL + `/${idUser}`, product).then(
        (response) => {
            getUser(dispatch, idUser)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateProductInCart = (dispatch, idUser, productId, product) => {
    return axios.put(API_URL + `/${idUser}/product/${productId}`, product).then(
        (response) => {
            getUser(dispatch, idUser)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProductFromCart = (dispatch, userId, productId) => {
    return axios.delete(API_URL + `/${userId}/products/${productId}`).then(
        (response) => {
            getUser(dispatch, userId)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const cartService = {
    addProductToCart, updateProductInCart, deleteProductFromCart
};



export default cartService;