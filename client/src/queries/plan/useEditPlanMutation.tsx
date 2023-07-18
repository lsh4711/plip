import { useMutation, useQueryClient } from '@tanstack/react-query';

import useToast from '@/hooks/useToast';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';
import instance from '../axiosinstance';

interface PatchPlan {
  id: string;
  places: ScheduledPlaceBase[][];
}

const patchPlan = ({ id, places }: PatchPlan) =>
  instance.patch(
    `api/schedules/${id}/edit`,
    { places },
    {
      withCredentials: true,
    }
  );

const useEditPlanMutation = (id: string) => {
  const toast = useToast();
  const queryClient = useQueryClient();

  return useMutation(patchPlan, {
    onSuccess: (data) => {
      queryClient.invalidateQueries(['/schedule', id]);
      toast({
        content: '자동 저장되었습니다.',
        type: 'success',
      });
    },
    onError: (e) => {
      toast({
        content: '일정 저장에 실패했습니다. 잠시 후에 다시 시도해 주세요.',
        type: 'warning',
      });
    },
  });
};

export default useEditPlanMutation;
