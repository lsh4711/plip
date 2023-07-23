import useModal from '@/hooks/useModal';
import Button from '../../atom/Button';
import Paragraph from '../../atom/Paragraph';
import { ReactComponent as PlusIcon } from '@/assets/icons/plus-circle.svg';
import WriteModal from '../../common/modals/WriteModal';

const NoRecord = ({ id }: { id: number }) => {
  const [openModal] = useModal();

  const onClickHandler = () => {
    openModal(({ isOpen, close }) => (
      <WriteModal id={id} type={'default'} isOpen={isOpen} onClose={close} />
    ));
  };

  return (
    <div className="flex h-full w-full flex-col items-center justify-center p-4">
      <Paragraph className="lg:text-md text-sm xl:text-lg">
        현재 {id}에 작성된 일지가 없어요😥
      </Paragraph>
      <Paragraph className="lg:text-md text-sm xl:text-lg">일지를 작성하러 가시겠습니까?</Paragraph>
      <Button variant={'default'} hovercolor={'default'} onClick={onClickHandler}>
        <PlusIcon />
      </Button>
    </div>
  );
};

export default NoRecord;
