import { useMutation } from '@tanstack/react-query';
import BASE_URL from './BASE_URL';

interface EmailValidationType {
  email: string;
  authcode: string;
}

const postEmailValidation = async ({ email, authcode }: EmailValidationType) => {
  const response = await fetch(`${BASE_URL}/email/auth`, {
    method: 'POST',
    body: JSON.stringify({ email, authcode }),
  });
  const result = await response.json();
  const status = response.status.toString();
  let validationResult = false;
  if (status === '403') {
    validationResult = false;
  }
  if (status === '200') {
    validationResult = true;
  }

  return {
    result,
    status,
    validationResult,
  };
};

const useEmailValidationMutation = () => {
  const emailValidation = useMutation({
    mutationFn: (emailObj: EmailValidationType) => postEmailValidation(emailObj),
  });
  return emailValidation;
};

export default useEmailValidationMutation;
