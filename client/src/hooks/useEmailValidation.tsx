import React from 'react';
import useDebounce from './useDebounce';
import { useEmailRequestMutation, useEmailValidationMutation, useSignupMutation } from '@/queries';

const useEmailValidation = () => {
  const [emailRequestState, setEmailRequestState] = React.useState({
    isSuccess: false,
    message: '',
  });
  const [authCodeState, setAuthCodeState] = React.useState({
    disabled: false,
    message: '',
  });

  
  return {
    emailRequestState,
    authCodeState,
    setEmailRequestState,
    setAuthCodeState,
  };
};

export default useEmailValidation;
