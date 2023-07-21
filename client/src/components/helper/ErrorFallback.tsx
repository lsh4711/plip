import { FallbackProps } from 'react-error-boundary';
import Button from '../atom/Button';
import Paragraph from '../atom/Paragraph';
import HeadingParagraph from '../atom/HeadingParagraph';

const ErrorFallback = ({ error, resetErrorBoundary }: FallbackProps) => {
  return (
    <div className=" flex flex-col items-center justify-center px-3 pt-52">
      <HeadingParagraph variant={'darkgray'} size={'xl'} className="text-center">
        {error.message}
      </HeadingParagraph>
      <Button onClick={() => resetErrorBoundary()}>재시도하기</Button>
      <Button onClick={() => location.reload()}>강력새로고침</Button>
      <Paragraph className=" mt-4">
        <strong className=" text-sky-400">잠시</strong> 기다려주세요
      </Paragraph>
    </div>
  );
};

export default ErrorFallback;
