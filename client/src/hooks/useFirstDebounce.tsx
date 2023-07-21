import { useRef } from 'react';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
function useFirstDebounce<T extends any[]>(callback: (...params: T) => void, time: number) {
  const timer = useRef<ReturnType<typeof setTimeout> | null>(null);
  const isFirst = useRef<boolean>(true);
  return (...params: T) => {
    if (timer.current) clearTimeout(timer.current);

    if (isFirst.current) {
      isFirst.current = false;
      callback(...params);
      return;
    }

    timer.current = setTimeout(() => {
      callback(...params);
      timer.current = null;
    }, time);
  };
}

export default useFirstDebounce;
