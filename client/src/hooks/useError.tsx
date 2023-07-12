import React from 'react';
import useToast from './useToast';

const useError = () => {
  const toast = useToast();
  const ErrorToast = () => {
    toast({
      content: '잠시 후 다시 시도해주세요',
      type: 'warning',
    });
  };

  return ErrorToast;
};

export default useError;
