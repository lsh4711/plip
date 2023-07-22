import React, { useRef, useState } from 'react';

type Props = {
  type: 'horizontal' | 'vertical';
  ref: HTMLDivElement;
};

const useScroll = ({ type = 'vertical', ref }: Props) => {
  const [isActive, setIsActive] = useState(false);

  //   const calcScorll = () => {
  //     if (type === 'vertical') {
  //       const offsetHeight = ref.current?.offsetHeight;
  //       const scrollHeight = ref.current?.scrollHeight;

  //       console.log(offsetHeight, scrollHeight);
  //     } else {
  //       // horizontal scroll
  //       const offsetWidth = ref.current?.offsetWidth;
  //       const scrollWidth = ref.current?.scrollWidth;

  //       console.log(offsetWidth, scrollWidth);
  //     }
  //   };

  return <div></div>;
};

export default useScroll;
