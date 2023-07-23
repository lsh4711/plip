import dayjs from 'dayjs';

const getFromNow = (createdAt: Date) => {
  return dayjs(createdAt).fromNow();
};

export default getFromNow;
