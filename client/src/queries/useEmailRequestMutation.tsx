import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';

const postEmailRequest = async (email: string) => {
  console.log(JSON.stringify({ email: email }));
  const response = await fetch(`${BASE_URL}/api/mail/signup`, {
    method: 'POST',
    body: JSON.stringify({ email: email }),
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  const result = await response.json();
  const ok = response.ok;
  return {
    result,
    ok,
    response,
  };
};

const useEmailRequestMutation = () => {
  const emailRequest = useMutation({
    mutationFn: (email: string) => postEmailRequest(email),
  });

  return emailRequest;
};

export default useEmailRequestMutation;
