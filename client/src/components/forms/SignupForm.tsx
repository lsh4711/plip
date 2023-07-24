import useEmailValidation from '@/hooks/useEmailValidation';
import { useEmailRequestMutation, useEmailValidationMutation, useSignupMutation } from '@/queries';
import { SignupType, signupSchema } from '@/schema/signupSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import Paragraph from '../atom/Paragraph';
import Button from '../atom/Button';
import LoadingSpinner from '../atom/LoadingSpinner';
import Input from '../atom/Input';
import useDebounce from '@/hooks/useDebounce';

const SignupForm = () => {
  const emailRequestMutation = useEmailRequestMutation('signup');
  const emailValidationMutation = useEmailValidationMutation();
  const signupMutation = useSignupMutation();

  const [isNicknameValid, setIsNicknameValid] = React.useState({
    isSuccess: true,
    message: '',
  });
  const { emailRequestState, setEmailRequestState, authCodeState, setAuthCodeState } =
    useEmailValidation();

  const signupForm = useForm<SignupType>({
    mode: 'all',
    resolver: zodResolver(signupSchema),
  });

  const onSubmit: SubmitHandler<SignupType> = (data) => {
    if (!emailRequestState.isSuccess) return;
    if (!authCodeState.disabled) return;
    if (signupMutation.status === 'loading') return;

    signupMutation
      .mutateAsync(data)
      .then((res) => {
        setIsNicknameValid({ isSuccess: true, message: '' });
      })
      .catch(() => {
        setIsNicknameValid({ isSuccess: false, message: '닉네임이 중복되었습니다.' });
      });
  };

  const emailCredentialRequest = () => {
    if (signupForm.formState.errors.email?.message !== undefined) return;
    if (signupForm.getValues('email') === '') return;
    if (emailRequestMutation.status === 'loading') return;
    if (emailRequestMutation.status === 'success') return;

    console.log(emailRequestMutation.status);
    emailRequestMutation
      .mutateAsync(signupForm.getValues('email'))
      .then((res) => {
        setAuthCodeState({ disabled: false, message: '' });
      })
      .catch(() => {
        setAuthCodeState({ disabled: true, message: '잠시 후 다시 시도해주세요' });
      });
  };

  const sendVerificationCodeEmail = () => {
    if (signupForm.getValues('authnumber') === '') return;
    if (signupForm.getValues('authnumber') === undefined) return;
    if (emailValidationMutation.status === 'loading') return;
    if (emailValidationMutation.status === 'success') return;

    emailValidationMutation
      .mutateAsync({
        authcode: signupForm.getValues('authnumber') as string,
        email: signupForm.getValues('email'),
      })
      .then((res) => {
        setEmailRequestState({ isSuccess: true, message: '' });
        setAuthCodeState({ disabled: true, message: '' });
      })
      .catch(() => {
        setEmailRequestState({ isSuccess: false, message: '인증번호가 맞지 않습니다.' });
        setAuthCodeState({ disabled: false, message: '다시 인증을 시도하세요' });
      });
  };

  const resetMutateEmailRequestStatus = useDebounce(() => {
    emailRequestMutation.reset();
  }, 2000);

  const resetAuthNumberStatus = useDebounce(() => {
    emailValidationMutation.reset();
  }, 2000);

  console.log(emailRequestMutation.status);
  return (
    <form className=" flex w-[460px] flex-col gap-y-6" onSubmit={signupForm.handleSubmit(onSubmit)}>
      <div className="">
        <div className="flex justify-between gap-6">
          <Input
            placeholder="이메일을 입력해 주세요."
            className=" flex-grow"
            {...signupForm.register('email', {
              onChange: resetMutateEmailRequestStatus,
            })}
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
          <span>
            {signupForm.formState.errors.email?.message && signupForm.getValues('email') !== ''
              ? signupForm.formState.errors.email.message
              : ''}
          </span>
          <span>{emailRequestState.message !== '' ? emailRequestState.message : ''}</span>
        </Paragraph>
      </div>

      <div className="">
        <div className="flex justify-between gap-6">
          <Input
            placeholder="인증번호를 입력해 주세요."
            className=" flex-grow"
            disabled={authCodeState.disabled}
            {...signupForm.register('authnumber', {
              onChange: resetAuthNumberStatus,
            })}
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
          {authCodeState.disabled ? null : authCodeState.message}
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
          <span> {isNicknameValid ? null : '중복된 닉네임입니다.'}</span>
          <span> {signupForm.formState.errors.nickname?.message}</span>
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
          <span> {signupForm.formState.errors.password?.message}</span>
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
          <span> {signupForm.formState.errors.checkpassword?.message}</span>
        </Paragraph>
      </div>
      <Button variant={'primary'} className=" flex gap-4" size="lg">
        {signupMutation.status === 'loading' ? <LoadingSpinner /> : null}
        <span>Sign up</span>
      </Button>
    </form>
  );
};

export default SignupForm;
