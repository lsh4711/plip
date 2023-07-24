import { MyTripTypes } from '@/types/mytrip/mytrip-types';
import { EditTitleProps } from './mytrip/useEditTripTitleMutation';
import instance from './axiosinstance';

export default class ScheduleAPI {
  constructor() {}

  #getSchedules = async () => {
    return instance.get<MyTripTypes[] | []>('/api/schedules').then((res) => res.data);
  };

  #deleteSchedule = async (id: number) => {
    return instance.delete(`/api/schedules/${id}`).then((res) => console.log(res));
  };

  #updateScheduleTitle = async (id: number, title: string) => {
    return instance.patch(`/api/schedules/${id}/edit`, { title }).then((res) => console.log(res));
  };

  onGetAllSchedules = () => {
    return this.#getSchedules();
  };

  onDeleteSchedule = async (id: number) => {
    return this.#deleteSchedule(id);
  };

  onUpdateScheduleTitle = async ({ id, title }: EditTitleProps) => {
    return this.#updateScheduleTitle(id, title);
  };
}
