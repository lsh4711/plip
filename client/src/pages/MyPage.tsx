import { Link } from 'react-router-dom';

import { HeadingParagraph, Paragraph } from '@/components';
import Avatar from '@/components/common/Avatar';
import ChangeNicknameForm from '@/components/forms/ChangeNicknameForm';
import ChangePasswordForm from '@/components/forms/ChangePasswordForm';
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

      <HeadingParagraph variant={'darkgray'} size={'sm'}>
        프로필
      </HeadingParagraph>
      <div className="flex flex-col items-center justify-center">
        <Avatar size={84} imgSrc="" />
        <Paragraph variant={'black'} className="mt-2">
          {data?.nickname}
        </Paragraph>
        <ChangeNicknameForm />
      </div>

      {data?.role === 'USER' && (
        <>
          <HeadingParagraph variant={'darkgray'} size={'sm'} className="mt-12">
            비밀번호 변경
          </HeadingParagraph>
          <div className="flex flex-col items-center justify-center">
            <ChangePasswordForm />
          </div>
        </>
      )}

      <div className="flex justify-end">
        <Link to="/mypage/signout" className="mt-12 text-sm text-[#bbb] hover:text-red-400">
          회원 탈퇴하기
        </Link>
      </div>
    </div>
  );
};

export default MyPage;
