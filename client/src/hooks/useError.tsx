import useToast from './useToast';
import { AxiosError } from 'axios';

const useError = () => {
  const toast = useToast();
  const ErrorToast = () => {
    toast({
      content: '네트워크 요청에 실패했습니다.',
      type: 'warning',
    });
  };

  return ErrorToast;
};

export default useError;
