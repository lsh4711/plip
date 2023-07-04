import { LoginType } from '@/pages/LoginPage';
import { isSuccessStatus } from '@/utils';
import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';

const postLogin = async (loginData: LoginType) => {
  const response = await fetch(`${BASE_URL}/users/login`, {
    method: 'POST',
    body: JSON.stringify(loginData),
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  const ACCESS_TOKEN = response.headers.get('Authorization');
  const REFRESH_TOKEN = response.headers.get('Refresh');
  const status = isSuccessStatus(response);
  const result = await response.json();
  return {
    accesstoken: ACCESS_TOKEN,
    refreshtoken: REFRESH_TOKEN,
    memberId: result.memberId,
    body: result,
    status: status,
  };
};

const useLoginMutation = () => {
  const loginMutation = useMutation({
    mutationFn: (loginData: LoginType) => postLogin(loginData),
    onSuccess(data, variables, context) {
      if (!data.status) return;
      const access = data.accesstoken ? data.accesstoken : '토큰이 없네요';
      const refresh = data.refreshtoken ? data.refreshtoken : '토큰이 없네요';

      window.localStorage.setItem('accesstoken', access);
      window.localStorage.setItem('refreshtoken', refresh);
    },
  });
  return loginMutation;
};

export default useLoginMutation;
