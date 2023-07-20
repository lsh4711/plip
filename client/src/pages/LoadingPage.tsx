import { HeadingParagraph, Paragraph } from '@/components';
import LoadingSpinner from '@/components/atom/LoadingSpinner';

const LoadingPage = () => {
  return (
    <div className=" flex flex-col items-center justify-center px-3 pt-52">
      <HeadingParagraph variant={'darkgray'} size={'xl'} className="text-center">
        <strong className=" text-sky-400">지현</strong>
        님께 화면을 그려달라고
        <br />
        부탁하고 있어요!
      </HeadingParagraph>
      <Paragraph className=" mt-4">
        <strong className=" text-sky-400">잠시</strong> 기다려주세요
      </Paragraph>
      <LoadingSpinner className=" mt-10 h-12 w-12 fill-sky-400" />
    </div>
  );
};

export default LoadingPage;
