import { useState } from 'react';

import { Button, Input, Paragraph } from '@/components';
import useEditUserMutation from '@/queries/auth/useEditUserMutation';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import { EditUserType, editUserSchema } from '@/schema/editUserSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { BiEditAlt } from '@react-icons/all-files/bi/BiEditAlt';
import { SubmitHandler, useForm } from 'react-hook-form';
import MyPageEditInput from './MyPageEditInput';

const MypageForm = () => {
  const { data, refetch } = useInquireUsersQuery();
  const mutation = useEditUserMutation();

  const [isEditMode, setIsEditMode] = useState(false);
  const [isNicknameValid, setIsNicknameValid] = useState(true);

  const editForm = useForm<EditUserType>({
    mode: 'all',
    defaultValues: {
      nickname: data?.nickname,
      password: '',
      checkpassword: '',
    },
    resolver: zodResolver(editUserSchema),
  });

  const onSubmit: SubmitHandler<EditUserType> = (data) => {
    mutation.mutateAsync({ nickname: data.nickname, password: data.password }).then((res) => {
      setIsEditMode(false);
      refetch();
    });
  };

  const setEditMode = () => {
    setIsEditMode(true);
    editForm.reset();
  };

  const unsetEditMode = () => {
    setIsEditMode(false);
    editForm.reset();
  };

  return (
    <form
      action=""
      className="relative mb-2 mt-8 flex w-full flex-col gap-y-6 rounded-lg border-[1px] px-4 py-6 "
      onSubmit={editForm.handleSubmit(onSubmit)}
    >
      {!isEditMode && (
        <Button
          type="button"
          variant="optional"
          hovercolor={'default'}
          className="absolute -top-10 right-0 px-3 py-2 text-xs font-medium hover:bg-zinc-200"
          onClick={setEditMode}
        >
          수정
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
              {...editForm.register('nickname')}
            />
          ) : (
            <div className="flex flex-grow text-[#343539]">{data?.nickname}</div>
          )}
        </div>

        {isEditMode && (
          <div className="flex justify-end">
            <Paragraph variant={'red'} size="xs" className=" mt-1">
              <span> {isNicknameValid ? null : '중복된 닉네임입니다.'}</span>
              <span> {editForm.formState.errors.nickname?.message}</span>
            </Paragraph>
          </div>
        )}
      </div>

      <div className="flex flex-col">
        <div className="flex flex-col md:flex-row md:items-center md:gap-4">
          <span className="w-24 text-sm text-[#4568DC] md:text-end">비밀번호</span>
          {isEditMode ? (
            <Input
              type={'password'}
              placeholder="비밀번호를 입력해 주세요. (영문, 숫자, 특수문자 포함 8자 이상)."
              className="flex-grow py-2"
              {...editForm.register('password')}
            />
          ) : (
            <div className="flex flex-grow text-[#343539]">******</div>
          )}
        </div>
        <div className="flex justify-end">
          <Paragraph variant={'red'} size="xs" className=" mt-1">
            <span>{editForm.formState.errors.password?.message}</span>
          </Paragraph>
        </div>
      </div>
      {isEditMode && <MyPageEditInput returnForm={editForm} />}

      {isEditMode && (
        <div className="flex justify-center gap-2">
          <Button
            type="button"
            variant={'ring'}
            hovercolor={'default'}
            className="px-3 py-2 text-sm"
            onClick={unsetEditMode}
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

export default MypageForm;

// const profileSchema = z
//   .object({
//     nickname: z
//       .string()
//       .nonempty({ message: '닉네임은 필수 입력입니다.' })
//       .min(2, { message: '닉네임은 2글자 이상 10글자 이하여야합니다.' })
//       .max(10, { message: '닉네임은 2글자 이상 10글자 이하여야합니다.' })
//       .regex(nicknameRegex, { message: '닉네임에 특수문자는 포함될 수 없습니다.' }),
//     password: z.string().nonempty({ message: '비밀번호는 필수 입력입니다.' }).regex(passwordRegex, {
//       message: '비밀번호는 영문,숫자,특수문자를 모두 포함한 8자 이상이어야 합니다.',
//     }),
//     checkpassword: z.string(),
//   })
//   .superRefine(({ checkpassword, password }, ctx) => {
//     if (checkpassword !== password) {
//       ctx.addIssue({
//         code: z.ZodIssueCode.custom,
//         message: '비밀번호가 서로 일치하는지 확인 해주세요',
//         path: ['checkpassword'],
//       });
//     }
//   });

// export type EditProfileTypes = z.infer<typeof profileSchema>;
