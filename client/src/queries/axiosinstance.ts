import axios from 'axios';
import BASE_URL from './BASE_URL';

const instance = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Authorization: 'empty',
  },
  withCredentials: true,
});

export default instance;
