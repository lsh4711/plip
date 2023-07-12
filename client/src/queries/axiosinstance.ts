import axios from 'axios';
import BASE_URL from './BASE_URL';

const instance = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
  },
  // withCredentials: true,
});

// instance.interceptors.request.use((config) => {
//   const accesstoken = localStorage.getItem('accesstoken');
//   if (accesstoken) {
//     config.headers['Authorization'] = accesstoken;
//   }
//   console.log('인터셉팅하고있어염');
//   return config;
// });

// instance.interceptors.response.use((response) => {
//   console.log(response);
//   console.log('어엄응답도인터셉팅');
//   return response;
// });

export default instance;
