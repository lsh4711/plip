import { SignupType } from '@/pages/SignUpPage';
import BASE_URL from './BASE_URL';
import { useMutation } from '@tanstack/react-query';
import instance from './axiosinstance';

const postSignup = async (signupData: SignupType) => {
  try {
    const response = await instance.post(
      '/api/users/signup',
      {
        email: signupData.email,
        password: signupData.password,
        nickname: signupData.nickname,
      },
      {
        withCredentials: true,
      }
    );
    return response;
  } catch (error) {
    if (error instanceof Error) {
      throw new Error('알 수 없는 에러입니다.', error);
    } else {
      throw new Error(String(error));
    }
  }
};

const useSignupMutation = () => {
  const signupMutation = useMutation({
    mutationFn: (signup: SignupType) => postSignup(signup),
  });
  return signupMutation;
};

export default useSignupMutation;

// 예전 fetch 코드
// export const postSignup = async (signupData: SignupType) => {
//   const parsedSignupData = {
//     email: signupData.email,
//     password: signupData.password,
//     nickname: signupData.nickname,
//   };

//   const response = await fetch(`${BASE_URL}/api/users/signup`, {
//     method: 'POST',
//     body: JSON.stringify(parsedSignupData),
//     // credentials: 'include',
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   return response;
// };
