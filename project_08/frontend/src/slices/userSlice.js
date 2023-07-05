import { createSlice } from '@reduxjs/toolkit';
import LordImage from "./img/LordOfTheHouse.jpg";
import PepegaImage from "./img/pepega.jpg";

const userSlice = createSlice({
  name: 'user',
  initialState: {
    users: [
      {
        id: 1,
      name: 'LordOfTheHouse',
      email: 'dun@ge.on',
      picture: LordImage,
      },
      {
        id: 2,
        name: 'Pepegas',
        email: 'pe@pe.pe',
        picture: PepegaImage,
      },
    ],
    currentUser: {
      id: 1,
      name: 'LordOfTheHouse',
      email: 'dun@ge.on',
      picture: LordImage,
    },
  },
  reducers: {
    set: (state, action) => {
      state.currentUser = action.payload;
    },
    switchUser: (state, action) => {
      const selectedUserName = action.payload;
      const selectedUser = state.users.find(user => user.name === selectedUserName);
      state.currentUser = selectedUser;
    },
    addUser: (state, action) => {
      const newUser = {
        id: state.users.length + 1,
        ...action.payload,
      };
      state.users.push(newUser);
    },
  },
});

export const { switchUser, addUser, set } = userSlice.actions;

export default userSlice.reducer;
