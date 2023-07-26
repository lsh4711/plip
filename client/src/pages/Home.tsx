import { useSelector } from 'react-redux';

import { Button, Paragraph } from '@/components';
import { RootState } from '@/redux/store';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const isLogin = useSelector((state: RootState) => state.auth.isLogin);
  const navigate = useNavigate();

  return (
    <>
      <div className="landing h-full w-full bg-[url('../assets/imgs/gyeonggi.webp')] bg-cover bg-center bg-no-repeat">
        {/* 임시 기프티콘 이벤트 버튼 (TODO 추후 삭제) */}
        <button className="flying absolute h-32 w-32" onClick={() => navigate('/event')}>
          <img src="/present.webp" draggable={false} />
        </button>
        <div className="z-50 flex h-full w-full flex-col items-center justify-end bg-black/50 px-10 pb-6 pt-[76px] sm:items-start sm:pb-10">
          <Paragraph
            size={'md'}
            variant={'white'}
            weight={'extrabold'}
            className="text-center sm:text-justify md:text-[44px] xl:text-[52px]"
          >
            이번 국내 여행,
          </Paragraph>
          <Paragraph
            size={'md'}
            variant={'white'}
            weight={'extrabold'}
            className="text-center sm:text-justify md:text-[44px] xl:text-[52px]"
          >
            보다 알찬 여행으로 만들고 싶다면?
          </Paragraph>
          <Paragraph
            size={'md'}
            variant={'white'}
            weight={'extrabold'}
            className="mb-4 text-center sm:text-justify md:text-[44px] xl:text-[52px]"
          >
            <span className="gradient-text">PliP</span>으로 여행 계획을 디자인해보세요!
          </Paragraph>
          <Button
            variant={'primary'}
            onClick={() => (isLogin ? navigate('/plan') : navigate('/login'))}
            className="w-full sm:w-fit"
          >
            여행 시작하기
          </Button>
        </div>
      </div>
    </>
  );
};

export default Home;
