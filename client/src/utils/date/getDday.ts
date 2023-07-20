import dayjs from 'dayjs';

const getDday = (date: Date): number => {
  const now = dayjs();
  const expired = dayjs(date);
  const diff = now.diff(expired, 'day', false);

  return diff;
};

export default getDday;
