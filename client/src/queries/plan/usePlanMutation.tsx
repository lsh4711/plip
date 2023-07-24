import { PostScheduleRequest } from '@/types/api/schedules-types';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import instance from '../axiosinstance';
import useToast from '@/hooks/useToast';

const postPlan = (plan: PostScheduleRequest) =>
  instance.post('/api/schedules/write', plan, {
    withCredentials: true,
  });

const usePlanMutation = () => {
  const toast = useToast();
  const queryClient = useQueryClient();

  return useMutation(postPlan, {
    onSuccess: (data) => {
      queryClient.invalidateQueries(['plan']);
    },
    onError: () => {
      toast({
        content: '여행 계획 생성에 실패했습니다. 잠시 후에 다시 시도해 주세요.',
        type: 'warning',
      });
    },
  });
};

export default usePlanMutation;
