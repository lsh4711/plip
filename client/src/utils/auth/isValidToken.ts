import { EMPTY_TOKEN } from '@/datas/constants';
import { AxiosHeaderValue } from 'axios';

const isValidToken = (token: AxiosHeaderValue) => {
  return token === EMPTY_TOKEN || token === undefined || token === null || !token;
};

export default isValidToken;
