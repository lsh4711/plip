import dayjs, { Dayjs } from 'dayjs';

enum Days {
  '일',
  '월',
  '화',
  '수',
  '목',
  '금',
  '토',
}

const getDayOfWeek = (date: Date): string => {
  const dayNumber = dayjs(date).day();

  return Days[dayNumber];
};

export default getDayOfWeek;
