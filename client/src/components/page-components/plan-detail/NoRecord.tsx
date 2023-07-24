import useModal from '@/hooks/useModal';
import Button from '../../atom/Button';
import Paragraph from '../../atom/Paragraph';
import { ReactComponent as PlusIcon } from '@/assets/icons/plus-circle.svg';
import WriteModal from '../../common/modals/WriteModal';
import { Schedule } from '@/types/api/schedules-types';
import { getPlaceNameScheduleInfo } from '@/utils';

type Props = {
  id: number;
  scheduleInfo: Schedule;
  placeName?: string;
};

const NoRecord = ({ id, scheduleInfo }: Props) => {
  const [openModal] = useModal();

  const onClickHandler = () => {
    openModal(({ isOpen, close }) => (
      <WriteModal id={id} type={'default'} isOpen={isOpen} onClose={close} />
    ));
  };

  return (
    <div className="flex h-full w-full flex-col items-center justify-center p-4">
      <Paragraph className="lg:text-md text-center text-sm xl:text-lg">
        현재{' '}
        <span className=" font-semibold text-[#3458DC]">
          {scheduleInfo.places ? getPlaceNameScheduleInfo(id, scheduleInfo) : ''}
        </span>
        에 작성된 일지가 없어요😥
      </Paragraph>
      <Button variant={'default'} hovercolor={'default'} onClick={onClickHandler}>
        <PlusIcon />
      </Button>
    </div>
  );
};

export default NoRecord;
