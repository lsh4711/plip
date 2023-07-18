import useToast from './useToast';
import { AxiosError, AxiosResponse } from 'axios';

const useSuccessFailToast = () => {
  const toast = useToast();

  const onSuccess = (toastText: string, callback?: () => void) => {
    return (data: AxiosResponse<any, any>, variables:string, context:unknown) => {
      if (typeof callback === 'function') {
        callback();
      }
      toast({ content: toastText, type: 'success' });
    };
  };

  const onError = (toastText: string) => {
    return (error: AxiosError) => {
      const message = typeof error.response?.data === 'string' ? error.response.data : toastText;
      toast({
        content: message,
        type: 'warning',
      });
    };
  };

  return { onSuccess, onError };
};

export default useSuccessFailToast;
