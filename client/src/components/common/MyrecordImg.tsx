import Image from '../atom/Image';
import React from 'react';

interface MyrecordImgProps {}

const MyrecordImg = ({}: MyrecordImgProps) => {
  return (
    <div className=" relative">
      <Image size={'lg'} rounded="active" hoveropacity={'active'} />
      <div className=" absolute bottom-0 left-0 mb-6 ml-6">
        <p className=" text-lg font-semibold text-white">협재해수욕장</p>
        <p className=" text-xs font-thin text-white">제주도</p>
      </div>
    </div>
  );
};

export default MyrecordImg;
