import React from 'react';
import HeadingParagraph from '../atom/HeadingParagraph';

const Preparing = () => {
  return (
    <div className=" flex  w-full items-center justify-center">
      <div className="">
        <HeadingParagraph size={'lg'} variant={'blue'}>
          준비중인 페이지입니다.
        </HeadingParagraph>
      </div>
    </div>
  );
};

export default Preparing;
