import { BiEditAlt } from '@react-icons/all-files/bi/BiEditAlt';
import { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';

import { Button, Input, Paragraph } from '@/components';
import CheckPasswordInput from '@/components/forms/CheckPasswordInput';
import useChangePasswordMutation from '@/queries/auth/useChangePasswordMutation';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import { ChangePasswordType, changePasswordSchema } from '@/schema/changePasswordSchema';
import { zodResolver } from '@hookform/resolvers/zod';

const ChangePasswordForm = () => {
  const { refetch } = useInquireUsersQuery();
  const mutation = useChangePasswordMutation();

  const [isEditMode, setIsEditMode] = useState(false);

  const changePasswordForm = useForm<ChangePasswordType>({
    mode: 'all',
    defaultValues: {
      password: '',
      checkpassword: '',
    },
    resolver: zodResolver(changePasswordSchema),
  });

  const onSubmit: SubmitHandler<ChangePasswordType> = (data) => {
    mutation.mutateAsync({ password: data.password }).then((res) => {
      setIsEditMode(false);
      changePasswordForm.reset();
      refetch();
    });
  };

  return (
    <form
      action=""
      className="relative mb-2 mt-4 flex w-full flex-col gap-y-6 rounded-lg border-[1px] px-4 py-6 "
      onSubmit={changePasswordForm.handleSubmit(onSubmit)}
    >
      {!isEditMode && (
        <Button
          type="button"
          variant="optional"
          hovercolor={'default'}
          className="absolute -top-10 right-0 px-3 py-2 text-xs font-medium hover:bg-zinc-200"
          onClick={() => setIsEditMode(true)}
        >
          비밀번호 변경
          <BiEditAlt size={15} />
        </Button>
      )}

      <div className="flex flex-col">
        <div className="flex flex-col md:flex-row md:items-center md:gap-4">
          <span className="w-24 text-sm text-[#4568DC] md:text-end">비밀번호</span>
          {isEditMode ? (
            <Input
              type={'password'}
              placeholder="비밀번호를 입력해 주세요. (영문, 숫자, 특수문자 포함 8자 이상)."
              className="flex-grow py-2"
              {...changePasswordForm.register('password')}
            />
          ) : (
            <div className="flex flex-grow text-[#343539]">******</div>
          )}
        </div>
        <div className="flex justify-end">
          <Paragraph variant={'red'} size="xs" className=" mt-1">
            <span>{changePasswordForm.formState.errors.password?.message}</span>
          </Paragraph>
        </div>
      </div>
      {isEditMode && <CheckPasswordInput returnForm={changePasswordForm} />}

      {isEditMode && (
        <div className="flex justify-center gap-2">
          <Button
            type="button"
            variant={'ring'}
            hovercolor={'default'}
            className="px-3 py-2 text-sm"
            onClick={() => {
              setIsEditMode(false);
              changePasswordForm.reset();
            }}
          >
            취소
          </Button>
          <Button type="submit" variant={'primary'} className="px-3 py-2 text-sm">
            완료
          </Button>
        </div>
      )}
    </form>
  );
};

export default ChangePasswordForm;
