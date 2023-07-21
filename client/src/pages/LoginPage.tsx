import { HeadingParagraph, OauthUI } from '@/components';
import LoginForm from '@/components/forms/LoginForm';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import { Navigate } from 'react-router';

const LoginPage = () => {
  const auth = useAuthRedirect();
  if (!auth.isRedirect) return <Navigate to={'/'} replace />;

  return (
    <main className="mx-auto mt-40 flex max-w-[1024px] flex-col items-center justify-center">
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
