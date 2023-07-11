import { Button, HeadingParagraph, Paragraph } from '@/components';
import { DialogButtonGroup, DialogContainer } from '@/components/common/Dialog';
import { ReactComponent as PlusCircleIcon } from '@/assets/icons/plus-circle.svg';

export type WriteModal = {
  type: 'default' | 'edit';
  content?: string;
  isOpen: boolean;
  onClose: () => void;
};

const WriteModal = ({ type, isOpen, onClose }: WriteModal) => {
  return (
    <>
      <DialogContainer
        isOpen={isOpen}
        className="z-50 flex  h-[600px] w-2/3 flex-col rounded-lg bg-white p-6 md:w-[960px]"
      >
        <div className=" mb-8 flex w-full">
          <div className="flex h-[160px] w-[120px] cursor-pointer flex-col items-center justify-center gap-2 border border-dotted text-xs text-slate-400">
            <PlusCircleIcon width={16} height={16} />
            사진 추가
            <span>0 / 15</span>
          </div>
        </div>
        <div
          className="md: flex-1 text-xs outline-none md:text-base"
          contentEditable={true}
          placeholder="구체적인 계획 혹은 다녀온 장소에 대한 추억을 남겨보세요!"
        ></div>
        <DialogButtonGroup>
          <Button
            type={'button'}
            variant={'ring'}
            onClick={onClose}
            className="text-xs md:text-base"
            hovercolor={'default'}
          >
            취소
          </Button>
          <Button type={'submit'} variant={'primary'} className="text-xs md:text-base">
            완료
          </Button>
        </DialogButtonGroup>
      </DialogContainer>
    </>
  );
};

export default WriteModal;
