import axios from "axios";
import {set} from "../slices/userSlice";
import {setCart} from "../slices/productSlice";

const API_URL = "http://localhost:8080/clients";

export const getUser = (dispatch, id) => {
    return axios.get(`${API_URL}/${id}`).then(
        (response) => {
            dispatch(set(response.data));
            dispatch(setCart(response.data.cart));
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();
            console.error(_content)
            dispatch(set([]));
        });
};

export const getUserByEmail = (dispatch, loginData) => {
  return axios.get(API_URL, { params: loginData }).then(
      (response) => {
          dispatch(set(response.data));
          dispatch(setCart(response.data.cart));
      },
      (error) => {
          const _content = (error.response && error.response.data) ||
              error.message ||
              error.toString();
          console.error(_content)
          dispatch(set([]));
      });
};

export const createUser = (dispatch, user) => {
    return axios.post(API_URL, user).then(
        (response) => {
            getUser(dispatch, response.data);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteUser = (dispatch, id) => {
  return axios
    .delete(API_URL + `/${id}`)
    .then(
      (response) => {
        getUser(dispatch);
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

const userService = {
    getUser, getUserByEmail, createUser, deleteUser
};

export default userService