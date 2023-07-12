import React from 'react';
import useToast from './useToast';

const useError = () => {
  const toast = useToast();
  const ErroToast = () => {
    toast({
        content:'알수없는에러입니다.'
    })
  }
};

export default useError;
