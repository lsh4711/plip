import Button from '../atom/Button';
import Paragraph from '../atom/Paragraph';
import { ReactComponent as PlusIcon } from '@/assets/icons/plus-circle.svg';
import { useRef } from 'react';
import { MdClose } from '@react-icons/all-files/md/MdClose';

const RecordOverray = ({ onClickClose }) => {
  const ref = useRef<HTMLDivElement>(null);
  return (
    <div
      ref={ref}
      className="absolute bottom-8 z-50 min-w-[320px] -translate-x-1/2 rounded-lg bg-white drop-shadow-lg"
    >
      <div className="relative flex flex-col items-center justify-center gap-1 p-8">
        <MdClose
          color="#bbb"
          size={16}
          onClick={onClickClose}
          className="absolute right-4 top-4 cursor-pointer"
        />
        <Paragraph>작성된 일지가 없어요😥</Paragraph>
        <Paragraph>일지를 작성하러 가시겠습니까?</Paragraph>
        <Button variant={'default'} hovercolor={'default'}>
          <PlusIcon />
        </Button>
      </div>
    </div>
  );
};

export default RecordOverray;
