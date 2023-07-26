import { useMutation } from '@tanstack/react-query';
import instance from '../axiosinstance';
import useSuccessFailToast from '@/hooks/useSuccessFailToast';

const useEmailRequestMutation = (type: 'pw' | 'signup') => {
  const mutateHandler = useSuccessFailToast();
  const postEmailRequest = async (email: string) => {
    const response = await instance.post(`/api/mail?type=${type}`, {
      email,
    });
    return response;
  };

  const emailRequest = useMutation({
    mutationFn: (email: string) => postEmailRequest(email),
    onSuccess: mutateHandler.onSuccess('이메일 요청 전송에 성공했습니다.'),
    onError: mutateHandler.onError('이메일 요청 전송에 실패했습니다.'),
    
  });

  return emailRequest;
};

export default useEmailRequestMutation;
