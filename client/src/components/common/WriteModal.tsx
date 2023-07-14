import { Button } from '@/components';
import { DialogButtonGroup, DialogContainer } from '@/components/common/Dialog';
import { ReactComponent as PlusCircleIcon } from '@/assets/icons/plus-circle.svg';
import { useRef, useState } from 'react';
import { ReactComponent as CloseIcon } from '@/assets/icons/close.svg';
import { maxImages, maxRecordCharacters } from '@/datas/constants';

import useModal from '@/hooks/useModal';
import axios from 'axios';

export type WriteModal = {
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

const WriteModal = ({ type, isOpen, onClose }: WriteModal) => {
  const [openModal] = useModal();

  const inputImageRef = useRef<HTMLInputElement>(null);

  const [uploadedImages, setUploadedImages] = useState([]);
  const [preViewImgSrcs, setPreViewImgSrcs] = useState([]);
  const [text, setText] = useState('');

  const onRemoveEntiresImages = () => {
    axios
      .delete('https://teamdev.shop:8000/api/records/1/imgs', {
        headers: {
          Authorization:
            'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImVtYWlsIjoiYWRtaW4iLCJtZW1iZXJJZCI6MSwic3ViIjoiYWRtaW4iLCJpYXQiOjE2ODkwMDQyOTEsImV4cCI6MTY5MTYzMjI4NH0.RU3k5t3V95_0xAvLSNTYqKmfIOM1y-jkqABRcGbNP5Iao92MR3ZnAjRHjlJ3dT-_j8shLbLxrPVNP08YaDr-pA',
        },
      })
      .then((res) => console.log(res));
  };

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
  };

  // let uploadedImages = undefined;

  const onUploadImageHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const formData = new FormData();

    const imgFiles = e.target.files;
    console.log(imgFiles);

    const currentUploadedImages = preViewImgSrcs.length + imgFiles?.length;

    if (currentUploadedImages > maxImages) {
      alert('사진은 최대 15장까지 추가할 수 있습니다.');
      return;
    }

    // 이미 파일이 추가되어 있을 경우 추가적으로 파일에 formData를 합쳐줘야
    if (uploadedImages) {
      const files = Array.from([...uploadedImages, ...imgFiles]);
      setUploadedImages(files);
    } else {
      setUploadedImages(imgFiles);
    }

    let relativeImageUrls = [...preViewImgSrcs]; // 상대 경로 이미지들을 저장

    for (let img of imgFiles) {
      formData.append('images', img);

      const currentImageUrl = URL.createObjectURL(img); // 상대 경로
      relativeImageUrls.push(currentImageUrl);
    }

    // for (let [key, value] of formData) {
    //   console.log(key);
    //   console.log(value);
    // }

    setPreViewImgSrcs(relativeImageUrls);
  };

  const onDeleteUploadedImage = (targetIndex: number) => {
    const filtered = preViewImgSrcs.filter((img, index) => index !== targetIndex);
    const filteredUploadedImages = uploadedImages.filter((img, index) => index !== targetIndex);

    setPreViewImgSrcs(filtered);
    setUploadedImages(filteredUploadedImages);
  };

  const onSubmitRecord = () => {
    console.log(uploadedImages);

    const formData = new FormData();

    for (let img of uploadedImages) {
      formData.append('images', img);
    }

    axios
      .post(
        'https://teamdev.shop:8000/api/records/3',
        { title: 'test', content: text },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization:
              'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImVtYWlsIjoiYWRtaW4iLCJtZW1iZXJJZCI6MSwic3ViIjoiYWRtaW4iLCJpYXQiOjE2ODkwMDQyOTEsImV4cCI6MTY5MTYzMjI4NH0.RU3k5t3V95_0xAvLSNTYqKmfIOM1y-jkqABRcGbNP5Iao92MR3ZnAjRHjlJ3dT-_j8shLbLxrPVNP08YaDr-pA',
          },
          withCredentials: true,
        }
      )
      .then((res) => res.headers.location)
      .then((param) => {
        console.log(param);

        return axios
          .post(`https://teamdev.shop:8000${param}/img`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
              Authorization:
                'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImVtYWlsIjoiYWRtaW4iLCJtZW1iZXJJZCI6MSwic3ViIjoiYWRtaW4iLCJpYXQiOjE2ODkwMDQyOTEsImV4cCI6MTY5MTYzMjI4NH0.RU3k5t3V95_0xAvLSNTYqKmfIOM1y-jkqABRcGbNP5Iao92MR3ZnAjRHjlJ3dT-_j8shLbLxrPVNP08YaDr-pA',
            },
            withCredentials: true,
          })
          .then((res) => console.log(res));
      });

    // axios
    //   .post('https://teamdev.shop:8000/api/records/1/img', formData, {
    //     headers: {
    //       'Content-Type': 'multipart/form-data',
    //       Authorization:
    //         'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImVtYWlsIjoiYWRtaW4iLCJtZW1iZXJJZCI6MSwic3ViIjoiYWRtaW4iLCJpYXQiOjE2ODkwMDQyOTEsImV4cCI6MTY5MTYzMjI4NH0.RU3k5t3V95_0xAvLSNTYqKmfIOM1y-jkqABRcGbNP5Iao92MR3ZnAjRHjlJ3dT-_j8shLbLxrPVNP08YaDr-pA',
    //     },
    //   })
    //   .then((res) => console.log(res));
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
            type={'button'}
            variant={'ring'}
            onClick={onRemoveEntiresImages}
            className="text-xs md:text-base"
            hovercolor={'default'}
          >
            테스트
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
          <Button
            type={'submit'}
            variant={'primary'}
            className="text-xs md:text-base"
            onClick={onSubmitRecord}
          >
            완료
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
    <DialogContainer
      isOpen={isOpen}
      onClose={onClose}
      className="z-50 flex w-2/3 flex-col rounded-lg bg-white p-6 md:w-[560px]"
    >
      정말로 일지 작성을 취소하시겠습니까?
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
        <Button
          type={'submit'}
          variant={'primary'}
          className="text-xs md:text-base"
          onClick={onComfirmClose}
        >
          완료
        </Button>
      </DialogButtonGroup>
    </DialogContainer>
  );
};

export default WriteModal;
