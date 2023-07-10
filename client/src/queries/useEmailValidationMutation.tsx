import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';

interface EmailValidationType {
  email: string;
  authcode: string;
}

const postEmailValidation = async ({ email, authcode }: EmailValidationType) => {
  try {
    const response = await instance.post('/api/mail/auth', {
      email,
      authCode: authcode,
    });
    return response;
  } catch (error) {
    if (error instanceof Error) {
      throw new Error('it something wrong', error);
    } else {
      throw new Error(String(error));
    }
  }
};

const useEmailValidationMutation = () => {
  const emailValidation = useMutation({
    mutationFn: (emailObj: EmailValidationType) => postEmailValidation(emailObj),
  });
  return emailValidation;
};

export default useEmailValidationMutation;

// 예전 fetch 코드
// const postEmailValidation = async ({ email, authcode }: EmailValidationType) => {
//   const response = await fetch(`${BASE_URL}/api/mail/auth`, {
//     method: 'POST',
//     body: JSON.stringify({ email, authCode: authcode }),
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   return response;
// };
