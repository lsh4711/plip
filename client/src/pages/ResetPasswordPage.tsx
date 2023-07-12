import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { useResetPasswordMutation } from '@/queries';
import { ResetPasswordType, resetPasswordSchema } from '@/schema/resetPasswordSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';

interface ResetPasswordProps {}

const ResetPasswordPage = ({}: ResetPasswordProps) => {
  const resetPasswordMutation = useResetPasswordMutation();
  const resetPasswordForm = useForm<ResetPasswordType>({
    mode: 'all',
    resolver: zodResolver(resetPasswordSchema),
  });

  const onSubmit: SubmitHandler<ResetPasswordType> = (data) => {
    resetPasswordMutation.mutate(data);
  };

  return (
    <main className=" mx-auto flex max-w-[1024px] flex-col">
      <div className=" mt-12 flex flex-col items-center justify-center">
        <div className=" mb-10 flex flex-col items-center justify-center gap-3">
          <HeadingParagraph variant={'darkgray'} size={'lg'}>
            비밀번호 재설정하기
          </HeadingParagraph>
          <Paragraph>새로운 비밀번호를 입력해주세요</Paragraph>
        </div>
        <div className="">
          <form
            className=" flex w-[460px] flex-col gap-y-6"
            onSubmit={resetPasswordForm.handleSubmit(onSubmit)}
          >
            <div className=" flex flex-col">
              <Input
                {...resetPasswordForm.register('username')}
                placeholder="비밀번호를 찾을 이메일을 입력하세요"
              />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {resetPasswordForm.formState.errors.username?.message}
              </Paragraph>
            </div>

            <div className=" flex flex-col">
              <Input
                {...resetPasswordForm.register('password')}
                type="password"
                placeholder="새로운 비밀번호를 설정하세요"
              />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {resetPasswordForm.formState.errors.password?.message}
              </Paragraph>
            </div>

            <div className=" flex flex-col">
              <Input
                {...resetPasswordForm.register('checkpassword')}
                type="password"
                placeholder="비밀번호 확인"
              />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {resetPasswordForm.formState.errors.checkpassword?.message}
              </Paragraph>
            </div>

            <Button variant={'primary'} size={'lg'} type="submit">
              비밀번호 변경하기
            </Button>
          </form>
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
    </main>
  );
};

export default ResetPasswordPage;
