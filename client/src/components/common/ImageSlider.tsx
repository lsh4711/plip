import { useEffect, useRef, useState } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

import Button from '../atom/Button';

import { ReactComponent as ArrowLeft } from '@/assets/icons/arrow-left.svg';
import { ReactComponent as ArrowRight } from '@/assets/icons/arrow-right.svg';
import { ReactComponent as EditIcon } from '@/assets/icons/edit.svg';

import useDebounce from '@/hooks/useDebounce';
import RecordImage from '../page-components/plan-detail/RecordImage';
import useModal from '@/hooks/useModal';
import WriteModal from './modals/WriteModal';

const sliceSpeed = 100;

const settings = {
  infinite: true,
  speed: sliceSpeed,
  slidesToShow: 1,
  slidesToScroll: 1,
  arrows: true,
};

type Props = {
  imgs: string[];
  defaultImage: string;
  recordId: number;
  content: string;
  recordRefetch: () => void;
  imageRefetch: () => void;
};

const ImageSlider = ({
  imgs,
  defaultImage,
  recordId,
  content,
  recordRefetch,
  imageRefetch,
}: Props) => {
  const sliderRef = useRef<Slider | null>(null);
  // const [uploadedImageTotals, setUploadedImageTotals] = useState(imgs.length);
  const [currentImageIndex, setCurrentImageIndex] = useState(imgs.length === 0 ? 0 : 1);
  const [openModal] = useModal();

  useEffect(() => {
    setCurrentImageIndex(imgs.length === 0 ? 0 : 1);
  }, [imgs]);

  const onEditModalHandler = () => {
    openModal(({ isOpen, close }) => (
      <WriteModal
        type={'edit'}
        id={recordId}
        isOpen={isOpen}
        onClose={close}
        content={content}
        imgs={imgs}
        recordRefetch={recordRefetch}
        imageRefetch={imageRefetch}
      />
    ));
  };

  const next = useDebounce(() => {
    sliderRef.current?.slickNext();
    setCurrentImageIndex((prev) => {
      const value = prev + 1;

      return imgs.length >= value ? value : 1;
    });
  }, sliceSpeed);

  const previous = useDebounce(() => {
    sliderRef.current?.slickPrev();
    setCurrentImageIndex((prev) => {
      const value = prev - 1;

      return value > 0 ? value : imgs.length;
    });
  }, sliceSpeed);
  return (
    <>
      <div className="relative">
        {imgs.length > 0 ? (
          <Slider ref={(slider) => (sliderRef.current = slider)} {...settings}>
            {imgs.map((img, index) => (
              <RecordImage key={index} imgSrc={img} />
            ))}
          </Slider>
        ) : (
          <RecordImage imgSrc={defaultImage} />
        )}
        <Button
          variant={'default'}
          hovercolor={'default'}
          className="absolute right-0 top-0"
          onClick={onEditModalHandler}
        >
          <EditIcon width={24} height={24} />
        </Button>
      </div>

      <div className="my-4 flex justify-center gap-4 text-center ">
        <Button variant={'primary'} onClick={previous} className=" h-[24px] w-[24px] p-0">
          <ArrowLeft />
        </Button>
        <span className="w-[50px]">{`${currentImageIndex} / ${imgs.length}`}</span>
        <Button variant={'primary'} onClick={next} className="p-0">
          <ArrowRight />
        </Button>
      </div>
    </>
  );
};

export default ImageSlider;
