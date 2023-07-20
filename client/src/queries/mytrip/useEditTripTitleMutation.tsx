import useToast from '@/hooks/useToast';
import ScheduleAPI from '../ScheduleAPI';
import { useMutation } from '@tanstack/react-query';

export type EditTitleProps = {
  id: number;
  title: string;
};

const useEditTripTitleMutation = () => {
  const scheduleAPI = new ScheduleAPI();
  const toast = useToast();
  const updateMutation = useMutation({
    mutationFn: scheduleAPI.onUpdateScheduleTitle,
    onSuccess: () => {
      toast({ content: '일정 이름 변경 완료!', type: 'success' });
    },
  });

  return updateMutation;
};

export default useEditTripTitleMutation;
