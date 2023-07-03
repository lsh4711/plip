import { Button, HeadingParagraph, Input } from '@/components';
import { zodResolver } from '@hookform/resolvers/zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import { z } from 'zod';

interface SignUpPageProps {}

let passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

const signupSchema = z
  .object({
    email: z
      .string()
      .min(1, { message: '이메일을 입력해주세요' })
      .email({ message: '유효하지 않은 이메일 양식입니다.' }),
    nickname: z.string().min(2).max(10),
    password: z.string().regex(passwordRegex),
    checkPassword: z.string().regex(passwordRegex),
  })
  .superRefine(({ checkPassword, password }, ctx) => {
    if (checkPassword !== password) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: '비밀번호가 서로 일치하는지 확인 해주세요',
        path: ['checkPassword'],
      });
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: '비밀번호가 서로 일치하는지 확인 해주세요',
        path: ['password'],
      });
    }
  });

export type SingupType = z.infer<typeof signupSchema>;

const SignUpPage = ({}: SignUpPageProps) => {
  const signupForm = useForm<SingupType>({
    resolver: zodResolver(signupSchema),
  });
  const onSubmit: SubmitHandler<SingupType> = (data) => {
    console.log(data);
  };

  return (
    <main className="flex h-screen max-w-[1024px] flex-col items-center justify-center">
      <div className=" mb-10">
        <HeadingParagraph
          variant={'darkgray'}
          size="lg"
          className=" flex items-center justify-center text-center"
        >
          PliP에 가입하여 <br />
          나만의 멋진 여행 계획을 만들어 보세요!
        </HeadingParagraph>
      </div>
      <div className="">
        <form action="" className=" flex w-[460px] flex-col gap-y-7">
          <div className="flex justify-between gap-6">
            <Input placeholder="이메일을 입력해 주세요." className=" flex-grow" />
            <Button variant={'primary'} type="button">
              인증 요청
            </Button>
          </div>
          <div className="flex justify-between gap-6">
            <Input placeholder="인증번호를 입력해 주세요." className=" flex-grow" />
            <Button variant={'primary'} type="button" className="">
              인증 하기
            </Button>
          </div>
          <Input placeholder="사용하실 닉네임을 입력해 주세요." />
          <Input
            type={'password'}
            placeholder="비밀번호를 입력해 주세요. (영문, 숫자, 특수문자 포함 8자 이상)."
          />
          <Input type={'password'} placeholder="다시 한번 비밀번호를 입력해 주세요" />
          <Button variant={'primary'} size="lg" type="submit">
            Sign up
          </Button>
        </form>
      </div>
    </main>
  );
};

export default SignUpPage;
