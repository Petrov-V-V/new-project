import { configureStore } from '@reduxjs/toolkit';
import productReducer from './slices/productSlice';
import userReducer from './slices/userSlice';
import authReducer from './slices/authSlice';

export default configureStore({
  reducer: {
    product: productReducer,
    user: userReducer,
    auth: authReducer
  }
});
