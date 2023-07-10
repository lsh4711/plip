import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';

interface EmailValidationType {
  email: string;
  authcode: string;
}

const postEmailValidation = async ({ email, authcode }: EmailValidationType) => {
  const response = await fetch(`${BASE_URL}/api/mail/auth`, {
    method: 'POST',
    body: JSON.stringify({ email, authcode }),
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return response;
};

const useEmailValidationMutation = () => {
  const emailValidation = useMutation({
    mutationFn: (emailObj: EmailValidationType) => postEmailValidation(emailObj),
  });
  return emailValidation;
};

export default useEmailValidationMutation;
