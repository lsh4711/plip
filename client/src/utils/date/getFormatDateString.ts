import { getDayOfWeek } from '@/utils/date';
import dayjs from 'dayjs';

const getFormatDateString = (
  date: Date,
  includeDate: boolean,
  format: 'korean' | 'dot' | 'dash'
): string => {
  let dateString;

  switch (format) {
    case 'korean':
      dateString = dayjs(date).format('YYYY년 M월 D일 ');
      break;
    case 'dot':
      dateString = dayjs(date).format('YYYY.MM.DD.');
      break;
    case 'dash':
      dateString = dayjs(date).format('YYYY-MM-DD');
      break;
  }

  const dayString = getDayOfWeek(date);

  return includeDate ? `${dateString}${dayString}` : dateString;
};

export default getFormatDateString;
