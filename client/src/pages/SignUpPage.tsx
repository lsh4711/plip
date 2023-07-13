import { HeadingParagraph } from '@/components';
import OauthUI from '@/components/helper/OauthUI';
import SignupForm from '@/components/forms/SignupForm';

const SignUpPage = () => {
  return (
    <main className="mx-auto flex max-w-[1024px] flex-col  ">
      <div className=" mt-12 flex flex-col items-center justify-center">
        <div className="mb-10">
          <HeadingParagraph variant={'darkgray'} size="lg" className=" text-center">
            PliP에 가입하여
            <br />
            나만의 멋진 여행 계획을 만들어 보세요!
          </HeadingParagraph>
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
