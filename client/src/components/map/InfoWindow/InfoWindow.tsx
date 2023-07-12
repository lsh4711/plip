import { FaStar } from '@react-icons/all-files/fa/FaStar';
import { MdClose } from '@react-icons/all-files/md/MdClose';

import { HeadingParagraph, Paragraph } from '@/components';
import { SelectDayButton } from '@/components/map/InfoWindow';
import { cn } from '@/utils';

type Props = {
  placeName: string;
  address: string;
  isBookmarked?: boolean;
  category?: string;
  phone?: string;
  className?: string;
};

const InfoWindow = ({ placeName, address, isBookmarked, category, phone, className }: Props) => {
  return (
    <div
      className={cn([
        'absolute z-50 flex min-w-[320px] flex-col gap-1 rounded-lg bg-white p-4 drop-shadow-lg',
        className,
      ])}
    >
      <div className="flex items-baseline justify-between gap-2">
        <div className="flex items-baseline gap-2">
          <HeadingParagraph variant={'blue'} size={'default'}>
            {placeName}
          </HeadingParagraph>
          <FaStar color={isBookmarked ? '#ffdd00' : '#eee'} size={16} />
        </div>
        {/* TODO Close Button Click Handler */}
        <MdClose color="#bbb" size={16} onClick={() => {}} />
      </div>

      <span className="text-xs text-[#bbb]">{category}</span>
      <div className="flex items-end justify-between">
        <div className="flex flex-col gap-1">
          <Paragraph variant={'darkgray'} className="text-xs">
            {address}
          </Paragraph>
          <Paragraph
            variant={'darkgray'}
            className={`text-xs ${phone ? 'text-[#343539]' : 'text-[#bbb]'}`}
          >
            {phone ? '064-123-3456' : '전화번호 미등록'}
          </Paragraph>
        </div>

        <SelectDayButton />
      </div>
    </div>
  );
};

export default InfoWindow;
