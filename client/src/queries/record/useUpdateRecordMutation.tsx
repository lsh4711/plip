import useToast from '@/hooks/useToast';
import RecordAPI, { RecordTypes } from '../RecordAPI';
import { useMutation } from '@tanstack/react-query';
import { AxiosError } from 'axios';

const useUpdateRecordMutation = () => {
  const toast = useToast();
  const recordAPI = new RecordAPI();

  const createMutation = useMutation({
    mutationFn: ({ param, content, formData }: RecordTypes) =>
      recordAPI.onPatchRecord({ param, content, formData }),
    onSuccess() {
      toast({ content: '일지가 성공적으로 수정되었습니다.', type: 'success' });
    },
    onError: (error: AxiosError) => {
      const message =
        typeof error.response?.data === 'string'
          ? error.response.data
          : '일지 수정 실패하였습니다.';
      toast({ content: message, type: 'warning' });
    },
  });

  return createMutation;
};

export default useUpdateRecordMutation;
