import axios from 'axios';
import BASE_URL from './BASE_URL';

const instance = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json;charset=UTF-8',
    Authorization: 'empty',
    // 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImVtYWlsIjoiYWRtaW4iLCJtZW1iZXJJZCI6MSwic3ViIjoiYWRtaW4iLCJpYXQiOjE2ODkwMDQyOTEsImV4cCI6MTY5MTYzMjI4NH0.RU3k5t3V95_0xAvLSNTYqKmfIOM1y-jkqABRcGbNP5Iao92MR3ZnAjRHjlJ3dT-_j8shLbLxrPVNP08YaDr-pA',
  },
  withCredentials: true,
});

export default instance;
