import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { z } from 'zod';

interface FindPasswordProps {}

const FindPasswordPage = ({}: FindPasswordProps) => {
  const findPasswordForm = useForm<FindPasswordType>({
    mode: 'all',
    resolver: zodResolver(findPasswordSchema),
  });

  const onSubmit: SubmitHandler<FindPasswordType> = (data) => {
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
            onSubmit={findPasswordForm.handleSubmit(onSubmit)}
          >
            <div className=" flex flex-col">
              <Input {...findPasswordForm.register('password')} type="password" />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {findPasswordForm.formState.errors.password?.message}
              </Paragraph>
            </div>
            <div className=" flex flex-col">
              <Input {...findPasswordForm.register('checkpassword')} type="password" />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {findPasswordForm.formState.errors.checkpassword?.message}
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

export default FindPasswordPage;

let passwordRegex = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/;

const findPasswordSchema = z
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

type FindPasswordType = z.infer<typeof findPasswordSchema>;
