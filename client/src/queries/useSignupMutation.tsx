import { SingupType } from '@/pages/SignUpPage';
import BASE_URL from './BASE_URL';
import { useMutation } from '@tanstack/react-query';

export const postSignup = async (signupData: SingupType) => {
  const parsedSignupData = {
    email: signupData.email,
    password: signupData.password,
    nickname: signupData.nickname,
  };

  const response = await fetch(`${BASE_URL}/api/users/signup`, {
    method: 'POST',
    body: JSON.stringify(parsedSignupData),
    // credentials: 'include',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return response;
};

const useSignupMutation = () => {
  const signupMutation = useMutation({
    mutationFn: (signup: SingupType) => postSignup(signup),
  });
  return signupMutation;
};

export default useSignupMutation;
