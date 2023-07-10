import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';

const postEmailRequest = async (email: string) => {
  try {
    const response = await instance.post('/api/mail/signup', {
      email,
    });
    return response;
  } catch (error) {
    if (error instanceof Error) {
      throw new Error('알 수 없는 에러입니다.', error);
    } else {
      throw new Error(String(error));
    }
  }
};

const useEmailRequestMutation = () => {
  const emailRequest = useMutation({
    mutationFn: (email: string) => postEmailRequest(email),
  });

  return emailRequest;
};

export default useEmailRequestMutation;

// 예전 fetch 코드
// const postEmailRequest = async (email: string) => {
//   console.log(JSON.stringify({ email: email }));
//   const response = await fetch(`${BASE_URL}/api/mail/signup`, {
//     method: 'POST',
//     body: JSON.stringify({ email: email }),
//     headers: {
//       'Content-Type': 'application/json;charset=utf-8',
//     },
//   });
//   return response;
// };
