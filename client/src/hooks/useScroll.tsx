import React, { RefObject, useState } from 'react';

type Props = {
  type: 'horizontal' | 'vertical';
  ref: RefObject<HTMLDivElement>;
};

const useScroll = ({ type = 'vertical', ref }: Props) => {
  const [isActive, setIsActive] = useState(false);

  const calcScroll = () => {
    if (type === 'vertical') {
      const offsetHeight = ref.current?.offsetHeight;
      const scrollHeight = ref.current?.scrollHeight;

      if (scrollHeight && offsetHeight && scrollHeight > offsetHeight) {
        setIsActive(true);
      } else {
        setIsActive(false);
      }
    } else {
      // horizontal scroll
      const offsetWidth = ref.current?.offsetWidth;
      const scrollWidth = ref.current?.scrollWidth;

      if (scrollWidth && offsetWidth && scrollWidth > offsetWidth) {
        setIsActive(true);
      } else {
        setIsActive(false);
      }
    }
  };

  return { isActive, calcScroll };
};

export default useScroll;
