import useToast from '@/hooks/useToast';
import RecordAPI, { PostRecordTypes } from '@/queries/RecordAPI';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';

const useCreateRecordMutation = () => {
  const toast = useToast();
  const recordAPI = new RecordAPI();
  const createMutation = useMutation({
    mutationFn: ({ param, content, formData }: PostRecordTypes) =>
      recordAPI.onPostRecord({ param, content, formData }),
    onSuccess() {
      toast({ content: '일지가 정상적으로 작성 완료되었습니다.', type: 'success' });
    },
    onError: (error: AxiosError) => {
      const message =
        typeof error.response?.data === 'string'
          ? error.response.data
          : '일지 작성 실패하였습니다.';
      toast({ content: message, type: 'warning' });
    },
  });

  return createMutation;
};

export default useCreateRecordMutation;
