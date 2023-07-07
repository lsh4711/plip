import { HeadingParagraph, Input, Paragraph } from '@/components';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

interface FindPasswordProps {}

const FindPassword = ({}: FindPasswordProps) => {
  const findPasswordForm = useForm<FindPasswordType>({
    mode: 'all',
    resolver: zodResolver(findPasswordSchema),
  });
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
          <form action="" className=" flex w-[460px] flex-col gap-y-6">
            <div className=" flex flex-col">
              <Input />
            </div>
            <Input />
          </form>
        </div>
      </div>
    </main>
  );
};

export default FindPassword;

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
