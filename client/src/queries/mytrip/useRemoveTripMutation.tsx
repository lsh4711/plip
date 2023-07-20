import { useMutation, useQueryClient } from '@tanstack/react-query';
import ScheduleAPI from '../ScheduleAPI';
import useToast from '@/hooks/useToast';

const useRemoveTripMutation = () => {
  const scheduleAPI = new ScheduleAPI();
  const queryClient = useQueryClient();
  const toast = useToast();
  const deleteMutation = useMutation({
    mutationFn: scheduleAPI.onDeleteSchedule,
    onSuccess: () => {
      toast({ content: '일정 삭제 완료!', type: 'success' });
      queryClient.invalidateQueries(['/schedules']);
    },
  });

  return deleteMutation;
};

export default useRemoveTripMutation;
