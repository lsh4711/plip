import { useSelector } from 'react-redux';

import { Button, Paragraph } from '@/components';
import { RootState } from '@/redux/store';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const isLogin = useSelector((state: RootState) => state.auth.isLogin);
  const navigate = useNavigate();

  return (
    <>
      <div className="randing relative h-full w-full bg-[url('../assets/imgs/gyeonggi.webp')] bg-cover bg-center bg-no-repeat">
        <div className="absolute bottom-[80px] left-[80px] z-50">
          <Paragraph size={'xl'} variant={'white'} weight={'extrabold'}>
            이번 국내 여행,
          </Paragraph>
          <Paragraph size={'xl'} variant={'white'} weight={'extrabold'}>
            보다 알찬 여행으로 만들고 싶다면?
          </Paragraph>
          <Paragraph size={'xl'} variant={'white'} weight={'extrabold'} className="mb-2">
            <span className="gradient-text">PliP</span>으로 여행 계획을 디자인해보세요!
          </Paragraph>
          <Button
            variant={'primary'}
            onClick={() => (isLogin ? navigate('/plan') : navigate('/login'))}
          >
            여행 시작하기
          </Button>
        </div>
        <div className="fixed h-full w-full bg-black bg-opacity-50 "></div>
      </div>
    </>
  );
};

export default Home;
