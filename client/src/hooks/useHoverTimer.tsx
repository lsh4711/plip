import { useRef } from 'react';

type Props = {
  openDelay?: number;
  closeDelay?: number;
};

const useHoverTimer = ({ openDelay = 500, closeDelay = 300 }: Props) => {
  const openTimerRef = useRef(0);
  const closeTimerRef = useRef(0);

  const onHandleOpen = (callback: (...params: any) => void) => {
    clearTimeout(openTimerRef.current);
    openTimerRef.current = window.setTimeout(() => {
      callback();
    }, openDelay);
  };

  const onHandleClose = (callback: () => void) => {
    clearTimeout(closeTimerRef.current);
    closeTimerRef.current = window.setTimeout(() => {
      callback();
    }, closeDelay);
  };

  return [onHandleOpen, onHandleClose];
};

export default useHoverTimer;
