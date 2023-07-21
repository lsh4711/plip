import { useRef } from 'react';

function isTimeDifference(date1: Date, date2: Date, time: number) {
  const timestamp1 = date1.getTime();
  const timestamp2 = date2.getTime();

  const timeDifference = Math.abs(timestamp1 - timestamp2);

  return timeDifference > time;
}

function useFirstThrottle<T extends any[]>(callback: (...params: T) => void, time: number) {
  const timer = useRef<ReturnType<typeof setTimeout> | null>(null);
  const isFirst = useRef<boolean>(true);
  const timeDifference = useRef<Date>(new Date());

  return (...params: T) => {
    if (isFirst.current) {
      callback(...params);
      isFirst.current = false;
      timeDifference.current = new Date();
    }

    if (!timer.current) {
      timer.current = setTimeout(() => {
        callback(...params);
        timer.current = null;
        const newTime = new Date();

        if (isTimeDifference(timeDifference.current!, newTime, 3000)) {
          timeDifference.current = newTime;
          isFirst.current = true;
        }
      }, time);
    }
  };
}

export default useFirstThrottle;
