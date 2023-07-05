import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import OauthUI from '@/components/helper/OauthUI';

import useThrottle from '@/hooks/useThrottle';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { z } from 'zod';

const SignUpPage = () => {
  const [isEmailValid, setIsEmailValid] = React.useState(true);
  const [isNicknameValid, setIsNicknameValid] = React.useState(true);

  const signupForm = useForm<SingupType>({
    mode: 'all',
    resolver: zodResolver(signupSchema),
  });

  const onSubmit: SubmitHandler<SingupType> = (data) => {
    console.log(data);
    setTimeout(() => {
      setIsNicknameValid(false);
    }, 2000);
  };

  const emailCredentialRequest = useThrottle(() => {
    const authNumberInput = document.querySelector('input[name="authnumber"]') as HTMLInputElement;
    if (authNumberInput === null) return;
    if (signupForm.formState.errors.email?.message !== undefined) return;
    if (signupForm.getValues('email') === '') return;
    /*
      const emailCredentialMutate = useEmailCredentialRequestMutation()
      const postCredentialRequestFn = async () => {
        const result = await postCredentialMutate.mutateAsync(signupForm.getValues('email'))
        응답받은 상태코드에 따라 분기처리
      }
    */

    authNumberInput.disabled = false;
  }, 2000);

  const sendVerificationCodeEmail = useThrottle(() => {
    const authNumberInput = document.querySelector('input[name="authnumber"]') as HTMLInputElement;
    /* 향후 인증하기 API가 구현되었을 때를 대비한 의사코드입니다.
    const emailVerificationMutate = useEmailVerificationMutation() 실제로는 훅이라 최상위에 선언해야함
    const postVerificationCode = async () => {
      const result = await emailVerificationMutate.mutate(signupForm.getValues('authnumber'))
      if(result.status === 성공코드면) {
        authNumberInput.disabled = true
        setIsEmailValid(false)
      }
      if(result.status === 실패코드면 ) setIsEmailValid(true)
    }    
    postVerificationCode()
    */
    setTimeout(() => {
      setIsEmailValid(false);
      authNumberInput.disabled = true;
    }, 2000);
  }, 2000);

  return (
    <main className="mx-auto flex  max-w-[1024px] flex-col items-center justify-center ">
      <div className=" mb-10">
        <HeadingParagraph variant={'darkgray'} size="lg" className=" text-center">
          PliP에 가입하여
          <br />
          나만의 멋진 여행 계획을 만들어 보세요!
        </HeadingParagraph>
      </div>
      <div className="">
        <form
          className=" flex w-[460px] flex-col gap-y-6"
          onSubmit={signupForm.handleSubmit(onSubmit)}
        >
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
              <Button variant={'primary'} type="button" onClick={() => emailCredentialRequest()}>
                인증 요청
              </Button>
            </div>
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              {signupForm.formState.errors.email?.message && signupForm.getValues('email') !== ''
                ? signupForm.formState.errors.email.message
                : null}
            </Paragraph>
          </div>

          <div className="">
            <div className="flex justify-between gap-6">
              <Input
                placeholder="인증번호를 입력해 주세요."
                className=" flex-grow"
                disabled
                {...signupForm.register('authnumber')}
              />

              <Button variant={'primary'} type="button" onClick={() => sendVerificationCodeEmail()}>
                인증 하기
              </Button>
            </div>
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              {isEmailValid ? null : '인증번호가 맞지 않습니다.'}
            </Paragraph>
          </div>

          <div className="">
            <div className=" flex">
              <Input
                placeholder="사용하실 닉네임을 입력해 주세요."
                className=" flex-grow"
                {...signupForm.register('nickname')}
              />
            </div>
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              <p> {isNicknameValid ? null : '중복된 닉네임입니다.'}</p>
              <p> {signupForm.formState.errors.nickname?.message}</p>
            </Paragraph>
          </div>

          <div className="">
            <div className=" flex">
              <Input
                type={'password'}
                placeholder="비밀번호를 입력해 주세요. (영문, 숫자, 특수문자 포함 8자 이상)."
                className=" flex-grow"
                {...signupForm.register('password')}
              />
            </div>
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              <p> {signupForm.formState.errors.password?.message}</p>
            </Paragraph>
          </div>

          <div className="">
            <div className=" flex">
              <Input
                type={'password'}
                placeholder="다시 한번 비밀번호를 입력해 주세요"
                className=" flex-grow"
                {...signupForm.register('checkpassword')}
              />
            </div>
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              <p> {signupForm.formState.errors.checkpassword?.message}</p>
            </Paragraph>
          </div>
          <Button variant={'primary'} size="lg" type="submit">
            Sign up
          </Button>
        </form>
        <OauthUI />
      </div>
    </main>
  );
};

let passwordRegex = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/;
let nicknameRegex = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;
const signupSchema = z
  .object({
    email: z.string().nonempty().email({ message: '유효하지 않은 이메일 양식입니다.' }),
    authnumber: z.string().optional(),
    nickname: z
      .string()
      .nonempty({ message: '닉네임은 필수 입력입니다.' })
      .min(2, { message: '닉네임은 2글자 이상 10글자 이하여야합니다.' })
      .max(10, { message: '닉네임은 2글자 이상 10글자 이하여야합니다.' })
      .regex(nicknameRegex, { message: '닉네임에 특수문자는 포함될 수 없습니다.' }),
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

export type SingupType = z.infer<typeof signupSchema>;

export default SignUpPage;
