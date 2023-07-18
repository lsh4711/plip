import instance from '@/queries/axiosinstance';

const setAccessTokenToHeader = (token: string) => {
  instance.defaults.headers['Authorization'] = token;
};

export default setAccessTokenToHeader;
