import axios from "axios";
import { set, searchProducts } from '../slices/productSlice';
import cartService from '../services/cartService';
import authHeader from "./authHeader";
import { message } from "antd";


const API_URL = "http://localhost:8080/products";

const getProducts = (dispatch) => {
  return axios
    .get(API_URL)
    .then(
      (response) => {
        dispatch(set(response.data));
        dispatch(searchProducts(''));
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        console.error(_content);

        dispatch(set([]));
      }
    );
};

const createProduct = (dispatch, product) => {
  return axios
    .post(API_URL, product,  {headers: authHeader()})
    .then(
      (response) => {
        getProducts(dispatch);
      },
      (error) => {
        message.error("Недостаточно прав");
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        console.error(_content);
      }
    );
};

const deleteProduct = (dispatch, id) => {
  return axios
    .delete(API_URL + `/${id}`,  {headers: authHeader()})
    .then(
      (response) => {
        getProducts(dispatch);
      },
      (error) => {
        message.error("Недостаточно прав");
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        console.error(_content);
      }
    );
};

const updateProduct = (dispatch, product, userId) => {
    return axios.put(API_URL, product,  {headers: authHeader()}).then(
        (response) => {
            getProducts(dispatch);
            cartService.getCart(dispatch, userId);
        },
        (error) => {
          message.error("Недостаточно прав");
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const productService = {
  getProducts,
  createProduct,
  deleteProduct,
  updateProduct
};

export default productService;
