import { Button, HeadingParagraph } from '@/components';
import OauthUI from '@/components/helper/OauthUI';
import SignupForm from '@/components/forms/SignupForm';
import useToast from '@/hooks/useToast';

const SignUpPage = () => {
  const toast = useToast();

  return (
    <main className="mx-auto flex max-w-[1024px] flex-col  ">
      <div className=" mt-12 flex flex-col items-center justify-center">
        <div className="mb-10">
          <HeadingParagraph variant={'darkgray'} size="lg" className=" text-center">
            PliP에 가입하여
            <br />
            나만의 멋진 여행 계획을 만들어 보세요!
          </HeadingParagraph>
          <Button onClick={() => toast({ content: '닉네임이 중복되었습니다.', type: 'warning' })}>
            토스트 띄우기
          </Button>
        </div>
        <div className="">
          <SignupForm />
          <OauthUI />
        </div>
      </div>
    </main>
  );
};
export default SignUpPage;
