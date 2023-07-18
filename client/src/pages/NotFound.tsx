import { useNavigate } from 'react-router';
import { Button, HeadingParagraph, Paragraph } from '@/components';

interface NotFoundProps {}

const NotFound = ({}: NotFoundProps) => {
  const navigate = useNavigate();

  return (
    <div className=" flex flex-col items-center justify-center px-3 pt-52">
      <HeadingParagraph variant={'darkgray'} size={'xl'} className="text-center">
        <strong className=" text-sky-400">이런</strong>
        <br />
        무언가 문제가 생겼어요
      </HeadingParagraph>
      <Paragraph className=" mt-4">
        <strong className=" text-sky-400">잠시</strong> 후 다시 시도해주세요
      </Paragraph>
      <Button onClick={() => navigate('/')} variant={'primary'}>
        메인 화면으로 돌아가기
      </Button>
    </div>
  );
};

export default NotFound;
