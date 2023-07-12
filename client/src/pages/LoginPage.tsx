import { HeadingParagraph } from '@/components';
import { OauthUI } from '@/components';
import LoginForm from '@/components/forms/LoginForm';

const LoginPage = () => {
  return (
    <main className="mx-auto mt-24 flex max-w-[1024px] flex-col items-center justify-center">
      <div className=" mb-10">
        <HeadingParagraph
          variant={'darkgray'}
          size="lg"
          className=" flex items-center justify-center text-center"
        >
          로그인
        </HeadingParagraph>
      </div>

      <div className="">
        <LoginForm />
        <OauthUI />
      </div>
    </main>
  );
};

export default LoginPage;
