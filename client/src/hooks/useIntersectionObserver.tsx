import { useEffect } from 'react';
import { useInView } from 'react-intersection-observer';

const useIntersectionObserver = (callback: () => void) => {
  const { ref, inView } = useInView({ threshold: 0.5 });

  useEffect(() => {
    if (inView) {
      callback();
    }
  }, [inView]);

  return { ref };
};

export default useIntersectionObserver;
