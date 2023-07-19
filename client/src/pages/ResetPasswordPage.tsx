import { HeadingParagraph } from '@/components';
import ResetPasswordForm from '@/components/forms/ResetPasswordForm';

interface ResetPasswordProps {}

const ResetPasswordPage = ({}: ResetPasswordProps) => {
  return (
    <main className=" mx-auto flex h-[80%] max-w-[1024px] flex-col  items-center justify-center">
      <HeadingParagraph variant={'darkgray'} size="lg" className=" mb-10 text-center">
        비밀번호를 잊어버리시다니
        <br />
        정신차리세요
      </HeadingParagraph>
      <ResetPasswordForm />
    </main>
  );
};

export default ResetPasswordPage;
