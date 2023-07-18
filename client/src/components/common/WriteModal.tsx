import { Button } from '@/components';
import { DialogButtonGroup, DialogContainer } from '@/components/common/Dialog';
import { ReactComponent as PlusCircleIcon } from '@/assets/icons/plus-circle.svg';
import { useRef, useState } from 'react';
import { ReactComponent as CloseIcon } from '@/assets/icons/close.svg';
import { maxImages, maxRecordCharacters } from '@/datas/constants';
import { resizeFile } from '@/utils/file/resizeFile';

import useModal from '@/hooks/useModal';
import Confirm from './Confirm';
import useCreateRecordMutation from '@/queries/record/useCreateRecordMutation';
import LoadingSpinner from '../atom/LoadingSpinner';

export type WriteModal = {
  id: number;
  type: 'default' | 'edit';
  content?: string;
  isOpen: boolean;
  onClose: () => void;
};

type CancelAlertProps = {
  isOpen: boolean;
  onClose: () => void;
  onCloseParent: () => void;
};

const WriteModal = ({ type, id, isOpen, onClose }: WriteModal) => {
  const [openModal] = useModal();
  const createRecordMutation = useCreateRecordMutation();
  const SCHEDULE_PLACE_ID = id; // 테스트를 위한 임시 변수입니다. 요청 주소의 param으로 사용됩니다.

  const inputImageRef = useRef<HTMLInputElement>(document.createElement('input'));

  const [uploadedImages, setUploadedImages] = useState<File[]>([]);
  const [test, setTest] = useState<File[]>([]); // 원본 이미지를 서버에 전송하기 위한 임시변수 추후 삭제 예정
  const [preViewImgSrcs, setPreViewImgSrcs] = useState<string[]>([]);
  const [text, setText] = useState('');

  const onInputText = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputText = e.target.innerHTML;

    if (inputText.length > 300) {
      alert(`일지의 내용은 ${maxRecordCharacters}자를 초과할 수 없습니다.`);
      e.target.innerHTML = text;
      return;
    }
    setText(inputText);
  };

  const openCancelAlert = () => {
    openModal(({ isOpen, close }) => (
      <CancelAlert isOpen={isOpen} onClose={close} onCloseParent={onClose} />
    ));
  };

  const onClickImgInputHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    e.preventDefault();
    inputImageRef.current?.click();
    inputImageRef.current.value = '';
  };

  const onUploadImageHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const imgFiles = e.target.files!;

    const currentUploadedImages = preViewImgSrcs.length + imgFiles?.length!;

    if (currentUploadedImages > maxImages) {
      alert('사진은 최대 15장까지 추가할 수 있습니다.');
      return;
    }

    let files: File[] = [];
    const resizedImages: File[] = [];

    // 이미 파일이 추가되어 있을 경우 추가적으로 파일에 formData를 병합
    if (uploadedImages.length > 0) {
      files = Array.from([...uploadedImages, ...imgFiles]);
    } else {
      files = Array.from([...imgFiles]);
    }

    // 업로드 이미지 미리보기
    let relativeImageUrls: string[] = []; // 상대 경로 이미지들을 저장

    for (let img of files) {
      const resizedImage: File = (await resizeFile(img)) as File;
      resizedImages.push(resizedImage);
      const currentImageUrl = URL.createObjectURL(resizedImage); // 상대 경로
      relativeImageUrls.push(currentImageUrl);
    }

    setTest(files);
    setUploadedImages(resizedImages);
    setPreViewImgSrcs(relativeImageUrls);
  };

  const onDeleteUploadedImage = (targetIndex: number) => {
    const filtered = preViewImgSrcs.filter((img, index) => index !== targetIndex);
    const filteredUploadedImages = uploadedImages.filter((img, index) => index !== targetIndex);

    setPreViewImgSrcs(filtered);
    setUploadedImages(filteredUploadedImages);
  };

  const onSubmitRecord = async () => {
    const formData = new FormData();

    if (uploadedImages.length === 0 && text.length === 0) {
      alert('이미지 업로드 혹은 글을 작성해주세요.');
      return;
    }

    for (let img of uploadedImages) {
      try {
        formData.append('images', img);
      } catch (e) {
        console.error(e);
      }
    }

    createRecordMutation
      .mutateAsync({ param: SCHEDULE_PLACE_ID, content: text, formData: formData })
      .then((res) => onClose());
  };

  return (
    <>
      <DialogContainer
        isOpen={isOpen}
        onClose={openCancelAlert}
        className="z-50 flex  h-[600px] w-2/3 flex-col rounded-lg bg-white p-6 md:w-[960px]"
      >
        <input
          ref={inputImageRef}
          type="file"
          accept="image/*"
          multiple
          className="hidden"
          onChange={onUploadImageHandler}
        />
        <div className="plip-scrollbar mb-8 flex h-[200px] w-full flex-nowrap gap-4 overflow-x-hidden px-4 pt-4 hover:overflow-x-scroll ">
          {preViewImgSrcs.map((image, id) => (
            <div
              key={`${image}-${id}`}
              className="relative h-[160px] w-[120px] shrink-0 grow-0 basis-auto"
            >
              <img src={image} alt="upload" className=" h-full w-full object-cover" />
              <Button
                variant={'primary'}
                hovercolor={'active'}
                className="absolute right-[-10px] top-[-10px] z-50 cursor-pointer p-1 text-white"
                onClick={() => onDeleteUploadedImage(id)}
              >
                <CloseIcon width={16} height={16} fill="white" />
              </Button>
            </div>
          ))}
          <div
            className=" flex h-[160px] w-[120px] shrink-0 grow-0 basis-auto cursor-pointer flex-col items-center justify-center gap-2 border border-dotted text-xs text-slate-400"
            onClick={onClickImgInputHandler}
          >
            <PlusCircleIcon width={16} height={16} />
            사진 추가
            <span>{`${preViewImgSrcs.length} / ${maxImages}`}</span>
          </div>
        </div>
        <div
          className="md: flex-1 text-xs outline-none md:text-base"
          contentEditable={true}
          placeholder="구체적인 계획 혹은 다녀온 장소에 대한 추억을 남겨보세요!"
          onInput={onInputText}
        ></div>
        <span className="self-end p-4 text-sm text-slate-400">{`( ${text.length} / ${maxRecordCharacters} )`}</span>
        <DialogButtonGroup>
          <Button
            type={'submit'}
            variant={'primary'}
            className="text-xs md:text-base"
            onClick={onSubmitRecord}
          >
            {createRecordMutation.status === 'loading' ? <LoadingSpinner /> : null}
            <span>완료</span>
          </Button>
          <Button
            type={'button'}
            variant={'ring'}
            onClick={openCancelAlert}
            className="text-xs md:text-base"
            hovercolor={'default'}
          >
            취소
          </Button>
        </DialogButtonGroup>
      </DialogContainer>
    </>
  );
};

const CancelAlert = ({ isOpen, onClose, onCloseParent }: CancelAlertProps) => {
  const onComfirmClose = () => {
    onClose();
    onCloseParent();
  };

  return (
    <>
      <Confirm
        type={'default'}
        isOpen={isOpen}
        onClose={onComfirmClose}
        onClickSecondaryButton={onClose}
        title={'알림'}
        content={'정말로 일지 작성을 취소하시겠습니까?'}
        primaryLabel={'확인'}
        secondaryLabel={'취소'}
      />
    </>
  );
};

export default WriteModal;
