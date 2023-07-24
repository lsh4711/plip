import { Link } from 'react-router-dom';

import { HeadingParagraph, Paragraph } from '@/components';
import Avatar from '@/components/common/Avatar';
import MypageForm from '@/components/forms/MypageForm';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';

interface MyPageProps {}

const MyPage = ({}: MyPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const { data } = useInquireUsersQuery();

  return (
    <div className=" flex h-auto flex-col pb-20">
      <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-6">
        회원 정보
      </HeadingParagraph>
      <div className="flex flex-col items-center justify-center">
        <Avatar size={84} imgSrc="" />
        <Paragraph variant={'black'} className="mt-2">
          {data?.data.data.nickname}
        </Paragraph>
        <MypageForm />
      </div>
      <div className="flex justify-end">
        <Link to="/mypage/signout" className="text-sm text-[#bbb] hover:text-red-400">
          회원 탈퇴하기
        </Link>
      </div>
    </div>
  );
};

export default MyPage;
