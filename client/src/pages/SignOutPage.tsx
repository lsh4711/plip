import { Link } from 'react-router-dom';

import { Button, HeadingParagraph, Paragraph } from '@/components';
import SignoutForm from '@/components/forms/SignoutForm';
import { SIGNOUT_VALIDATION_STRING } from '@/datas/constants';

const SignOutPage = () => {
  return (
    <div className="flex h-auto flex-col pb-20">
      <HeadingParagraph variant={'darkgray'} size={'md'}>
        회원탈퇴
      </HeadingParagraph>
      <Paragraph className=" mb-3 mt-6">
        회원탈퇴를 원하시면 아래 문장을 똑같이 작성해주세요.
      </Paragraph>
      <div className="flex h-full w-full flex-col items-center pt-8">
        <Paragraph variant={'red'} weight={'bold'} className=" my-4">
          {SIGNOUT_VALIDATION_STRING}
        </Paragraph>

        <SignoutForm />

        <div className=" mt-6 flex w-[460px] flex-col">
          <Button
            className=" bg-zinc-400 text-white"
            hoveropacity={'active'}
            size={'lg'}
            hovercolor={'default'}
          >
            <Link to={'/'}>메인페이지로 돌아가기</Link>
          </Button>
        </div>
      </div>
    </div>
  );
};

export default SignOutPage;
