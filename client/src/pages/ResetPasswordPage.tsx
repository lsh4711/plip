import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import LoadingSpinner from '@/components/atom/LoadingSpinner';
import useDebounce from '@/hooks/useDebounce';
import useEmailValidation from '@/hooks/useEmailValidation';
import {
  useEmailRequestMutation,
  useEmailValidationMutation,
  useResetPasswordMutation,
} from '@/queries';
import { ResetPasswordType, resetPasswordSchema } from '@/schema/resetPasswordSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';

interface ResetPasswordProps {}

const ResetPasswordPage = ({}: ResetPasswordProps) => {
  const emailRequestMutation = useEmailRequestMutation('pw');
  const emailValidationMutation = useEmailValidationMutation();
  const { emailRequestState, setEmailRequestState, authCodeState, setAuthCodeState } =
    useEmailValidation();
  const resetPasswordMutation = useResetPasswordMutation();
  const resetPasswordForm = useForm<ResetPasswordType>({
    mode: 'all',
    resolver: zodResolver(resetPasswordSchema),
  });

  const onSubmit: SubmitHandler<ResetPasswordType> = (data) => {
    resetPasswordMutation.mutate(data);
  };

  const emailCredentialRequest = useDebounce(() => {
    if (resetPasswordForm.formState.errors.email?.message !== undefined) return;
    if (resetPasswordForm.getValues('email') === '') return;

    emailRequestMutation
      .mutateAsync(resetPasswordForm.getValues('email'))
      .then((res) => {
        setAuthCodeState({ disabled: false, message: '' });
      })
      .catch(() => {
        setAuthCodeState({ disabled: true, message: '잠시 후 다시 시도해주세요' });
      });
  }, 500);

  const sendVerificationCodeEmail = useDebounce(() => {
    if (resetPasswordForm.getValues('authnumber') === '') return;
    if (resetPasswordForm.getValues('authnumber') === undefined) return;

    emailValidationMutation
      .mutateAsync({
        authcode: resetPasswordForm.getValues('authnumber') as string,
        email: resetPasswordForm.getValues('email'),
      })
      .then((res) => {
        setEmailRequestState({ isSuccess: true, message: '' });
        setAuthCodeState({ disabled: true, message: '' });
      })
      .catch(() => {
        setEmailRequestState({ isSuccess: false, message: '인증번호가 맞지 않습니다.' });
        setAuthCodeState({ disabled: false, message: '다시 인증을 시도하세요' });
      });
  }, 500);

  return (
    <main className=" mx-auto flex h-[80%] max-w-[1024px] flex-col  items-center justify-center">
      <HeadingParagraph variant={'darkgray'} size="lg" className=" mb-10 text-center">
        비밀번호를 잊어버리시다니
        <br />
        정신차리세요
      </HeadingParagraph>
      <form
        className=" flex w-[460px] flex-col gap-y-6 "
        onSubmit={resetPasswordForm.handleSubmit(onSubmit)}
      >
        <div className="">
          <div className="flex justify-between gap-6">
            <Input
              placeholder="비밀번호를 찾을 이메일을 입력해 주세요."
              className=" flex-grow"
              {...resetPasswordForm.register('email')}
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
              {resetPasswordForm.formState.errors.email?.message &&
              resetPasswordForm.getValues('email') !== ''
                ? resetPasswordForm.formState.errors.email.message
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
              {...resetPasswordForm.register('authnumber')}
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
              type={'password'}
              placeholder="재설정할 비밀번호 입력. (영문, 숫자, 특수문자 포함 8자 이상)."
              className=" flex-grow"
              {...resetPasswordForm.register('password')}
            />
          </div>
          <Paragraph variant={'red'} size="xs" className=" mt-1">
            <span> {resetPasswordForm.formState.errors.password?.message}</span>
          </Paragraph>
        </div>

        <div className="">
          <div className=" flex">
            <Input
              type={'password'}
              placeholder="다시 한번 비밀번호를 입력해 주세요"
              className=" flex-grow"
              {...resetPasswordForm.register('checkpassword')}
            />
          </div>
          <Paragraph variant={'red'} size="xs" className=" mt-1">
            <span> {resetPasswordForm.formState.errors.checkpassword?.message}</span>
          </Paragraph>
        </div>
        <Button variant={'primary'} className=" flex gap-4" size="lg">
          {resetPasswordMutation.status === 'loading' ? <LoadingSpinner /> : null}
          <span>Sign up</span>
        </Button>
      </form>
    </main>
  );
};

export default ResetPasswordPage;
