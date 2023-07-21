import { useRef, useState } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

import Button from '../atom/Button';

import { ReactComponent as ArrowLeft } from '@/assets/icons/arrow-left.svg';
import { ReactComponent as ArrowRight } from '@/assets/icons/arrow-right.svg';
import useDebounce from '@/hooks/useDebounce';

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
};

const ImageSlider = ({ imgs }: Props) => {
  const sliderRef = useRef<Slider | null>(null);
  const [currentImageIndex, setCurrentImageIndex] = useState(1);

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
      <Slider ref={(slider) => (sliderRef.current = slider)} {...settings}>
        {imgs.map((img, index) => (
          <div className="h-[300px]" key={index}>
            <img src={img} alt="" className="h-full  w-full rounded-t-lg object-cover" />
          </div>
        ))}
      </Slider>
      <div className="mt-4 flex justify-center gap-4">
        <Button variant={'primary'} onClick={previous} className="h-[24px] w-[24px] p-0">
          <ArrowLeft />
        </Button>
        <span>{`${currentImageIndex} / ${imgs.length}`}</span>
        <Button variant={'primary'} onClick={next} className="p-0">
          <ArrowRight />
        </Button>
      </div>
    </>
  );
};

export default ImageSlider;
