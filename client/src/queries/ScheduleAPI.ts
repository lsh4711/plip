import { MyTripTypes } from '@/types/mytrip/mytrip-types';
import instance from './axiosinstance';

export default class ScheduleAPI {
  constructor() {}

  #getSchedules = async () => {
    return instance.get<MyTripTypes[] | []>('/api/schedules').then((res) => res.data);
  };

  getAllMySchedules = () => {
    return this.#getSchedules();
  };
}
