import { BiEditAlt } from '@react-icons/all-files/bi/BiEditAlt';
import { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';

import { Button, Input, Paragraph } from '@/components';
import useChangeNicknameMutation from '@/queries/auth/useChangeNicknameMutation';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import { ChangeNicknameType, changeNicknameSchema } from '@/schema/changeNicknameSchema';
import { zodResolver } from '@hookform/resolvers/zod';

const ChangeNicknameForm = () => {
  const { data, refetch } = useInquireUsersQuery();
  const mutation = useChangeNicknameMutation();

  const [isEditMode, setIsEditMode] = useState(false);
  const [isNicknameValid, setIsNicknameValid] = useState(true);

  const changeNicknameForm = useForm<ChangeNicknameType>({
    mode: 'all',
    defaultValues: {
      nickname: data?.nickname,
    },
    resolver: zodResolver(changeNicknameSchema),
  });

  const onSubmit: SubmitHandler<ChangeNicknameType> = (data) => {
    mutation.mutateAsync({ nickname: data.nickname }).then((res) => {
      setIsEditMode(false);
      changeNicknameForm.reset({ nickname: data.nickname });
      refetch();
    });
  };

  return (
    <form
      action=""
      className="relative mb-2 mt-8 flex w-full flex-col gap-y-6 rounded-lg border-[1px] px-4 py-6 "
      onSubmit={changeNicknameForm.handleSubmit(onSubmit)}
    >
      {!isEditMode && (
        <Button
          type="button"
          variant="optional"
          hovercolor={'default'}
          className="absolute -top-10 right-0 px-3 py-2 text-xs font-medium hover:bg-zinc-200"
          onClick={() => setIsEditMode(true)}
        >
          닉네임 변경
          <BiEditAlt size={15} />
        </Button>
      )}

      <div className="flex flex-col md:flex-row md:items-center md:gap-4">
        <span className="w-24 text-sm text-[#4568DC] md:text-end">이메일</span>
        <div className="flex flex-grow text-[#343539]">{data?.email}</div>
      </div>

      <div className="flex flex-col">
        <div className="flex flex-col md:flex-row md:items-center md:gap-4">
          <span className="w-24 text-sm text-[#4568DC] md:text-end">닉네임</span>
          {isEditMode ? (
            <Input
              placeholder="닉네임을 작성해주세요."
              className="flex-grow py-2"
              {...changeNicknameForm.register('nickname')}
            />
          ) : (
            <div className="flex flex-grow text-[#343539]">{data?.nickname}</div>
          )}
        </div>

        {isEditMode && (
          <div className="flex justify-end">
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              <span> {isNicknameValid ? null : '중복된 닉네임입니다.'}</span>
              <span> {changeNicknameForm.formState.errors.nickname?.message}</span>
            </Paragraph>
          </div>
        )}
      </div>

      {isEditMode && (
        <div className="flex justify-center gap-2">
          <Button
            type="button"
            variant={'ring'}
            hovercolor={'default'}
            className="px-3 py-2 text-sm"
            onClick={() => {
              setIsEditMode(false);
              changeNicknameForm.reset();
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

export default ChangeNicknameForm;
