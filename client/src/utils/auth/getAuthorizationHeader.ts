import instance from '@/queries/axiosinstance';

const getAuthorizationHeader = () => instance.defaults.headers['Authorization'];

export default getAuthorizationHeader;
