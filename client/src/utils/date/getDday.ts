import dayjs from 'dayjs';

const calcDday = (date: Date): number => {
  const now = dayjs();
  const expired = dayjs(date);
  const diff = now.diff(expired, 'day', true);

  // console.log(Math.floor(diff));

  return Math.floor(diff);
};

const getDday = (date: Date): string => {
  const dDay = calcDday(date);
  let str = dDay > 0 ? `D+${Math.abs(dDay)}` : `D-${Math.abs(dDay)}`;
  return str;
};

export default getDday;
