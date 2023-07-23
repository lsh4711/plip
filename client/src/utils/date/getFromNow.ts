import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';

const getFromNow = (createdAt: Date) => {
  dayjs.extend(relativeTime);
  return dayjs(createdAt).fromNow();
};

export default getFromNow;
