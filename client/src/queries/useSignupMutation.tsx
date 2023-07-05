import { SingupType } from '@/pages/SignUpPage';
import BASE_URL from './BASE_URL';

const postSignup = async (signupData: SingupType) => {
  const response = await fetch(`${BASE_URL}/users/signup`, {
    method: 'POST',
    body: JSON.stringify(signupData),
    // credentials: 'include',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  const result = await response.json();
  return result;
};

const useSignupMutation = () => {
  return <div>useSignupMutation</div>;
};

export default useSignupMutation;
