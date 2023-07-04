import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { z } from 'zod';

interface SignUpPageProps {}

let passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

const signupSchema = z
  .object({
    email: z.string().nonempty().email({ message: '유효하지 않은 이메일 양식입니다.' }),
    authnumber: z.string().optional(),
    nickname: z.string().nonempty().min(2).max(10),
    password: z.string().nonempty().regex(passwordRegex),
    checkpassword: z.string().nonempty().regex(passwordRegex),
  })
  .superRefine(({ checkpassword, password }, ctx) => {
    if (checkpassword !== password) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: '비밀번호가 서로 일치하는지 확인 해주세요',
        path: ['checkpassword'],
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
    mode: 'all',
    resolver: zodResolver(signupSchema),
  });
  const onSubmit: SubmitHandler<SingupType> = (data) => {
    console.log(data);
  };

  const emailCredentailsRequest = () => {};

  return (
    <main className="mx-auto flex h-screen max-w-[1024px] flex-col items-center justify-center ">
      <div className=" mb-10">
        <HeadingParagraph variant={'darkgray'} size="lg" className=" text-center">
          PliP에 가입하여
          <br />
          나만의 멋진 여행 계획을 만들어 보세요!
        </HeadingParagraph>
      </div>
      <div className="">
        <form action="" className=" flex w-[460px] flex-col gap-y-6">
          <div className="">
            <div className="flex justify-between gap-6">
              <Input
                placeholder="이메일을 입력해 주세요."
                className=" flex-grow"
                {...signupForm.register('email', {
                  onChange: (event: React.ChangeEvent<HTMLInputElement>) => {
                    const nowdata = signupForm.getValues('email');
                    console.log(nowdata);
                  },
                })}
              />
              <Button variant={'primary'} type="button" onClick={() => emailCredentailsRequest()}>
                인증 요청
              </Button>
            </div>
            <div>
              {signupForm.formState.errors.email?.message && signupForm.getValues('email') !== ''
                ? signupForm.formState.errors.email.message
                : null}
            </div>
          </div>

          <div className="flex justify-between gap-6">
            <Input
              placeholder="인증번호를 입력해 주세요."
              className=" flex-grow"
              disabled
              {...signupForm.register('authnumber')}
            />
            <Button variant={'primary'} type="button" className="">
              인증 하기
            </Button>
          </div>
          <Input
            placeholder="사용하실 닉네임을 입력해 주세요."
            {...signupForm.register('nickname')}
          />
          <Input
            type={'password'}
            placeholder="비밀번호를 입력해 주세요. (영문, 숫자, 특수문자 포함 8자 이상)."
            {...signupForm.register('password')}
          />
          <Input
            type={'password'}
            placeholder="다시 한번 비밀번호를 입력해 주세요"
            {...signupForm.register('checkpassword')}
          />
          <Button variant={'primary'} size="lg" type="submit">
            Sign up
          </Button>
        </form>
        <div className=" my-6 flex flex-col items-center justify-center gap-y-6">
          <Paragraph>또는</Paragraph>
          <Paragraph>
            이미 회원이신가요?{' '}
            <a href="/" className=" text-blue-500">
              로그인하기
            </a>
          </Paragraph>
        </div>
      </div>
    </main>
  );
};

export default SignUpPage;
