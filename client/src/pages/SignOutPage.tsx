import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { useSignoutMutation } from '@/queries';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { z } from 'zod';

interface SignOutPageProps {}

const SIGNOUT_VALIDATION_STRING = '성지현그녀는감히전설이라고할수있다';

const SignOutPage = ({}: SignOutPageProps) => {
  const signoutMutation = useSignoutMutation();
  const signoutForm = useForm<SignoutType>({
    resolver: zodResolver(signoutSchema),
  });
  const onSubmit: SubmitHandler<SignoutType> = (data) => {
    signoutMutation.mutate();
  };
  return (
    <main className=" mx-auto flex max-w-[1024px] flex-col">
      <div className=" mt-12 flex flex-col items-center justify-center">
        <HeadingParagraph variant={'darkgray'} size={'lg'}>
          회원탈퇴
        </HeadingParagraph>
        <Paragraph className=" mb-3 mt-6">
          회원탈퇴를 원하시면 아래 문장을 똑같이 작성해주세요
        </Paragraph>
        <Paragraph variant={'red'} weight={'bold'} className=" my-4">
          {SIGNOUT_VALIDATION_STRING}
        </Paragraph>
        <div className="flex w-[460px] flex-col">
          <form className="flex w-[460px] flex-col" onSubmit={signoutForm.handleSubmit(onSubmit)}>
            <div className=" flex flex-col">
              <Input {...signoutForm.register('signout')} />
              <Paragraph variant={'red'} size={'xs'} className=" mt-1">
                {signoutForm.formState.errors.signout?.message}
              </Paragraph>
            </div>
            <Button variant={'primary'} size={'lg'} type="submit" className=" mt-6">
              회원탈퇴하기
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

export default SignOutPage;

const signoutSchema = z
  .object({
    signout: z.string(),
  })
  .superRefine(({ signout }, ctx) => {
    if (signout !== SIGNOUT_VALIDATION_STRING) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: '문장을 똑같이 작성해주세요',
        path: ['signout'],
      });
    }
  });

type SignoutType = z.infer<typeof signoutSchema>;
