import axios from "axios";
import { set, searchProducts } from '../slices/productSlice';
import authHeader from "./authHeader";


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
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        console.error(_content);
      }
    );
};

const updateProduct = (dispatch, product) => {
    return axios.put(API_URL, product,  {headers: authHeader()}).then(
        (response) => {
            getProducts(dispatch)
        },
        (error) => {
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
