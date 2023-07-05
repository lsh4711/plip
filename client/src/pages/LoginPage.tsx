import { Button, HeadingParagraph, Input } from '@/components';
import OauthUI from '@/components/helper/OauthUI';
import { useLoginMutation } from '@/queries';
import { zodResolver } from '@hookform/resolvers/zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import { z } from 'zod';

interface LoginPageProps {}

let passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

const loginSchema = z.object({
  email: z
    .string()
    .min(1, { message: '이메일을 입력해주세요' })
    .email({ message: '유효하지 않은 이메일 양식입니다.' }),
  password: z.string().regex(passwordRegex),
});

export type LoginType = z.infer<typeof loginSchema>;

const LoginPage = ({}: LoginPageProps) => {
  const loginMutation = useLoginMutation();
  const loginForm = useForm<LoginType>({ resolver: zodResolver(loginSchema) });
  const onSubmit: SubmitHandler<LoginType> = (data) => {
    console.log(data);
  };
  return (
    <main className="mx-auto flex max-w-[1024px] flex-col items-center justify-center">
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
        <form
          className=" flex w-[460px] flex-col gap-y-7"
          onSubmit={loginForm.handleSubmit(onSubmit)}
        >
          <Input placeholder="이메일을 입력해 주세요." {...loginForm.register('email')} />
          <Input
            placeholder="비밀번호를 입력해 주세요."
            type={'password'}
            {...loginForm.register('password')}
          />
          <Button variant={'primary'} size="lg">
            Sign up
          </Button>
        </form>
        <OauthUI />
      </div>
    </main>
  );
};

export default LoginPage;
