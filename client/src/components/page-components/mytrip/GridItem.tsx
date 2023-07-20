import { ReactElement, useEffect, useMemo, useRef, useState } from 'react';
import { ReactComponent as EditIcon } from '@/assets/icons/edit.svg';
import Button from '@/components/atom/Button';
import useModal from '@/hooks/useModal';
import Confirm from '@/components/common/Confirm';
import useEditTripTitleMutation from '@/queries/mytrip/useEditTripTitleMutation';

type Props = {
  id?: number;
  title: string | ReactElement;
  content: string | Date | number;
  editable?: boolean;
};

const maxTitleLength = 30;

const GridItem = ({ id, title, content, editable }: Props) => {
  const [isEdit, setIsEdit] = useState(!editable);
  const [text, setText] = useState(
    editable && (content as String).length === 0
      ? '여행의 이름을 지어주세요~!'
      : (content as string)
  );

  const editRef = useRef(document.createElement('div'));
  const [openModal] = useModal();
  const editTitleMutation = useEditTripTitleMutation();

  useEffect(() => {
    if (editable && text.length === 0) {
      onToggleEditHandler();
    } else if (editable && text.length > 0) {
      editRef.current.innerHTML = text;
    }
  }, []);

  const onInputText = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputText = e.target.innerHTML;

    if (inputText.length > maxTitleLength) {
      alert(`일정의 제목은 ${maxTitleLength}자를 초과할 수 없습니다.`);
      e.target.innerHTML = text;
      return;
    }
  };

  const onEditTripName = useMemo(() => onInputText, [text]);

  const onToggleEditHandler = () => {
    const currentTitle = editRef.current.textContent;

    const onConfirm = (close: () => void) => {
      setText(currentTitle!);
      editTitleMutation.mutateAsync({ id: id!, title: currentTitle! }).then(() => close());
    };

    const onCancel = (close: () => void) => {
      editRef.current.textContent = text;
      close();
    };

    setIsEdit(!isEdit);
    setTimeout(() => {
      onEditFocus();
    }, 0);

    if (text !== currentTitle) {
      openModal(({ isOpen, close }) => (
        <Confirm
          type={'default'}
          title={'알림'}
          content={'일정 이름을 변경하시겠습니까?'}
          isOpen={isOpen}
          onClose={close}
          primaryLabel={'변경'}
          secondaryLabel={'취소'}
          onClickSecondaryButton={() => onCancel(close)}
          onClickPrimaryButton={() => onConfirm(close)}
        />
      ));
    }
  };

  const onEditFocus = () => {
    editRef.current.focus();

    const range = document.createRange();
    const sel = window.getSelection();

    if (
      typeof editRef.current.lastChild === 'object' &&
      editRef.current.lastChild !== null &&
      editRef.current.lastChild.nodeType === Node.TEXT_NODE
    ) {
      // @ts-ignore
      range.setStart(editRef.current.lastChild, editRef.current.lastChild.length);
      sel?.removeAllRanges();
      sel?.addRange(range);
    }
  };

  return (
    <div className="flex items-center gap-2 text-sm">
      <div className="font-bold text-[#4568DC]">{title}</div>
      <div
        className="outline-none"
        ref={editRef}
        contentEditable={isEdit}
        suppressContentEditableWarning={true}
        onInput={onEditTripName}
        onBlur={onToggleEditHandler}
        placeholder="여행의 이름을 정해주세요!"
        role="input"
      >
        {!editable && text}
      </div>
      {editable && (
        <Button onClick={onToggleEditHandler} className="p-0" hovercolor={'default'}>
          <EditIcon width={16} height={16} />
        </Button>
      )}
    </div>
  );
};

export default GridItem;
