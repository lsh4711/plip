import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { SIGNOUT_VALIDATION_STRING } from '@/datas/constants';
import { Link } from 'react-router-dom';
import SignoutForm from '@/components/forms/SignoutForm';

const SignOutPage = () => {
  return (
    <main className=" mx-auto flex max-w-[1024px] flex-col">
      <div className=" mt-12 flex flex-col items-center justify-center">
        <HeadingParagraph variant={'darkgray'} size={'lg'}>
          회원탈퇴
        </HeadingParagraph>
        <Paragraph className=" mb-3 mt-6">
          회원탈퇴를 원하시면 아래 문장을 똑같이 작성해주세요
        </Paragraph>
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
    </main>
  );
};

export default SignOutPage;
