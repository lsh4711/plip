import Header from '@/components/common/Header';
import { Button, Paragraph } from '@/components';
import { Link } from 'react-router-dom';
// 렌더 페이지
interface HomeProps {}

const Home = ({}: HomeProps) => {
  return (
    <>
      <div className="randing relative h-full w-full bg-[url('../assets/imgs/gyeonggi.jpg')] bg-cover bg-center bg-no-repeat">
        <Header isHome={true} />
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
          <Button variant={'primary'}>
            <Link to="/plan">여행 시작하기</Link>
          </Button>
        </div>
        <div className="fixed h-full w-full bg-black bg-opacity-50 "></div>
      </div>
    </>
  );
};

export default Home;
