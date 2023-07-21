import { useRef } from 'react';

function useFirstThrottle<T extends any[]>(callback: (...params: T) => void, time: number) {
  const timer = useRef<ReturnType<typeof setTimeout> | null>(null);
  const isFirst = useRef<boolean>(true);

  return (...params: T) => {
    if (isFirst.current) {
      callback(...params);
      isFirst.current = false;
      return;
    }

    if (!timer.current) {
      timer.current = setTimeout(() => {
        callback(...params);
        timer.current = null;
      }, time);
    }
  };
}

export default useFirstThrottle;
