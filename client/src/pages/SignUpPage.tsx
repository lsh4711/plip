import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import OauthUI from '@/components/helper/OauthUI';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { z } from 'zod';
import { passwordRegex, nicknameRegex } from '@/datas/constants';
import useEmailRequestMutation from '@/queries/useEmailRequestMutation';
import useEmailValidationMutation from '@/queries/useEmailValidationMutation';
import { useSignupMutation } from '@/queries';
import LoadingSpinner from '@/components/atom/LoadingSpinner';
import useDebounce from '@/hooks/useDebounce';
import useToast from '@/hooks/useToast';
import useEmailValidation from '@/hooks/useEmailValidation';

const SignUpPage = () => {
  const [isNicknameValid, setIsNicknameValid] = React.useState({
    isSuccess: true,
    message: '',
  });
  const { emailRequestState, setEmailRequestState, authCodeState, setAuthCodeState } =
    useEmailValidation();

  const emailRequestMutation = useEmailRequestMutation('signup');
  const emailValidationMutation = useEmailValidationMutation();
  const signupMutation = useSignupMutation();
  const signupForm = useForm<SignupType>({
    mode: 'all',
    resolver: zodResolver(signupSchema),
  });

  const onSubmit: SubmitHandler<SignupType> = (data) => {
    if (!emailRequestState.isSuccess) return;
    if (!authCodeState.disabled) return;

    const onSubmitFn = async () => {
      const response = await signupMutation.mutateAsync(data);
      console.log(response);
      if (response.status === 201) {
        setIsNicknameValid({ isSuccess: true, message: '' });
      } else {
        setIsNicknameValid({ isSuccess: false, message: '닉네임이 중복되었습니다.' });
      }
    };
    onSubmitFn();
  };

  const emailCredentialRequest = useDebounce(() => {
    if (signupForm.formState.errors.email?.message !== undefined) return;
    if (signupForm.getValues('email') === '') return;

    const postCredentialRequestFn = async () => {
      const response = await emailRequestMutation.mutateAsync(signupForm.getValues('email'));
      if (response.status === 200) {
        setAuthCodeState({ disabled: false, message: '' });
      }
    };
    postCredentialRequestFn();
  }, 500);

  const sendVerificationCodeEmail = useDebounce(() => {
    const postVerificationCode = async () => {
      if (signupForm.getValues('authnumber') === '') return;
      if (signupForm.getValues('authnumber') === undefined) return;

      const response = await emailValidationMutation.mutateAsync({
        authcode: signupForm.getValues('authnumber') as string,
        email: signupForm.getValues('email'),
      });
      if (response.status === 200) {
        setEmailRequestState({ isSuccess: true, message: '' });
        setAuthCodeState({ disabled: true, message: '' });
      } else {
        setEmailRequestState({ isSuccess: false, message: '인증번호가 맞지 않습니다.' });
        setAuthCodeState({ disabled: false, message: '다시 인증을 시도하세요' });
      }
    };
    postVerificationCode();
  }, 500);
  console.log(emailRequestMutation.status);

  return (
    <main className="mx-auto flex max-w-[1024px] flex-col  ">
      <div className=" mt-12 flex flex-col items-center justify-center">
        <div className="mb-10">
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
                  {...signupForm.register('email')}
                />
                <Button
                  variant={'primary'}
                  className=" flex gap-2"
                  size="lg"
                  type="button"
                  onClick={() => emailCredentialRequest()}
                >
                  {emailRequestMutation.status === 'loading' ? <LoadingSpinner /> : null}
                  <span>인증 요청</span>
                </Button>
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                <p>
                  {signupForm.formState.errors.email?.message &&
                  signupForm.getValues('email') !== ''
                    ? signupForm.formState.errors.email.message
                    : null}
                </p>
                <p>{emailRequestState.message !== '' ? emailRequestState.message : null}</p>
              </Paragraph>
            </div>

            <div className="">
              <div className="flex justify-between gap-6">
                <Input
                  placeholder="인증번호를 입력해 주세요."
                  className=" flex-grow"
                  disabled={authCodeState.disabled}
                  {...signupForm.register('authnumber')}
                />

                <Button
                  variant={'primary'}
                  type="button"
                  onClick={() => sendVerificationCodeEmail()}
                  className=" flex gap-2"
                >
                  {emailValidationMutation.status === 'loading' ? <LoadingSpinner /> : null}
                  <span>인증 하기</span>
                </Button>
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                {emailRequestState.isSuccess ? null : emailRequestState.message}
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
            <Button variant={'primary'} className=" flex gap-4" size="lg">
              {signupMutation.status === 'loading' ? <LoadingSpinner /> : null}
              <span>Sign up</span>
            </Button>
          </form>
          <OauthUI />
        </div>
      </div>
    </main>
  );
};

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

export type SignupType = z.infer<typeof signupSchema>;

export default SignUpPage;
