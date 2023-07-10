import dayjs from 'dayjs';

const getTripPeriod = (start: Date, end: Date) => {
  const night = dayjs(end).diff(start, 'day');
  const day = night + 1;

  return night === 0 ? '당일치기' : `${night}박 ${day}일`;
};

export default getTripPeriod;
