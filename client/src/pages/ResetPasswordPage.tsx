import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { z } from 'zod';

interface ResetPasswordProps {}

const ResetPasswordPage = ({}: ResetPasswordProps) => {
  const resetPasswordForm = useForm<ResetPasswordType>({
    mode: 'all',
    resolver: zodResolver(resetPasswordSchema),
  });

  const onSubmit: SubmitHandler<ResetPasswordType> = (data) => {
    console.log(data);
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
              <Input {...resetPasswordForm.register('password')} type="password" />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {resetPasswordForm.formState.errors.password?.message}
              </Paragraph>
            </div>
            <div className=" flex flex-col">
              <Input {...resetPasswordForm.register('checkpassword')} type="password" />
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

let passwordRegex = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/;

const resetPasswordSchema = z
  .object({
    password: z.string().nonempty({ message: '비밀번호는 필수 입력입니다.' }).regex(passwordRegex, {
      message: '비밀번호는 영문,숫자,특수문자를 모두 포함한 8자 이상이어야 합니다.',
    }),
    checkpassword: z.string(),
  })
  .superRefine(({ checkpassword, password }, ctx) => {
    if (checkpassword !== password) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: '비밀번호가 서로 일치하는지 확인 해주세요',
        path: ['checkpassword'],
      });
    }
  });

type ResetPasswordType = z.infer<typeof resetPasswordSchema>;
