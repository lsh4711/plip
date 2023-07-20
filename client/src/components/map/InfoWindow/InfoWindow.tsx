import { FaStar } from '@react-icons/all-files/fa/FaStar';
import { MdClose } from '@react-icons/all-files/md/MdClose';
import { useDispatch } from 'react-redux';

import { HeadingParagraph, Paragraph } from '@/components';
import { SelectDayButton } from '@/components/map/InfoWindow';
import { allCategories } from '@/datas/categories';
import useToast from '@/hooks/useToast';
import { addSchedule, setIsStale } from '@/redux/slices/scheduleSlice';
import { CategoryGroupCode } from '@/types/mapApi/place-types';
import { cn } from '@/utils';

type Props = {
  id: number;
  type: 'scheduling' | 'recording';
  placeName: string;
  address: string;
  latitude: string;
  longitude: string;
  isBookmarked: boolean;
  category: CategoryGroupCode;
  phone: string;
  className?: string;
  onClickClose: () => void;
};

const InfoWindow = ({
  id,
  type,
  placeName,
  address,
  latitude,
  longitude,
  isBookmarked,
  category,
  phone,
  className,
  onClickClose,
}: Props) => {
  const dispatch = useDispatch();
  const toast = useToast();

  const add = (day: number) => {
    dispatch(
      addSchedule({
        day,
        schedule: {
          apiId: id,
          name: placeName,
          address,
          latitude,
          longitude,
          category: category!,
          bookmark: isBookmarked!,
          phone: phone!,
        },
      })
    );
    dispatch(setIsStale(true));
    toast({
      type: 'success',
      content: `Day ${day}에 추가되었습니다.`,
    });
    onClickClose();
  };

  return (
    <div
      className={cn([
        'z-50 flex min-w-[320px] flex-col gap-1 rounded-lg bg-white p-4 drop-shadow-lg',
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
        <MdClose color="#bbb" size={16} onClick={onClickClose} className="cursor-pointer" />
      </div>

      <span className="text-xs text-[#bbb]">{allCategories[category!]}</span>
      <div className="flex items-end justify-between">
        <div className="flex flex-col gap-1">
          <Paragraph variant={'darkgray'} className="text-xs">
            {address}
          </Paragraph>
          <Paragraph
            variant={'darkgray'}
            className={`text-xs ${phone ? 'text-[#343539]' : 'text-[#bbb]'}`}
          >
            {phone ? phone : '전화번호 미등록'}
          </Paragraph>
        </div>

        {type === 'scheduling' && <SelectDayButton addSchedule={add} />}
      </div>
    </div>
  );
};

export default InfoWindow;
