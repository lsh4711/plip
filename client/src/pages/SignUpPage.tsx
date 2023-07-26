import { HeadingParagraph } from '@/components';
import SignupForm from '@/components/forms/SignupForm';
import OauthUI from '@/components/helper/OauthUI';

const SignUpPage = () => {
  return (
    <main className="mx-auto mt-40 flex max-w-[1024px] flex-col pb-20  ">
      <div className="flex flex-col items-center justify-center">
        <div className="mb-10">
          <HeadingParagraph variant={'darkgray'} size="lg" className=" text-center">
            <span className="gradient-text">PliP</span>에 가입하여
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
