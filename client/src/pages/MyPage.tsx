import { BiEditAlt } from '@react-icons/all-files/bi/BiEditAlt';
import { useState } from 'react';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { z } from 'zod';

import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import Avatar from '@/components/common/Avatar';
import { nicknameRegex, passwordRegex } from '@/datas/constants';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import useInquireUsersQuery from '@/queries/auth/useInquireUsersQuery';
import { zodResolver } from '@hookform/resolvers/zod';

interface MyPageProps {}

const MyPage = ({}: MyPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const { data } = useInquireUsersQuery();

  const editForm = useForm<EditProfileTypes>({
    mode: 'all',
    defaultValues: {
      nickname: data?.data.data.nickname,
      password: '',
      checkpassword: '',
    },
    resolver: zodResolver(profileSchema),
  });

  const onSubmit: SubmitHandler<EditProfileTypes> = (data) => {
    // console.log(data);
  };

  const [isEditMode, setIsEditMode] = useState(false);

  // valid
  const [isNicknameValid, setIsNicknameValid] = useState(true);

  const setEditMode = () => {
    setIsEditMode(true);
    editForm.reset();
  };

  const unsetEditMode = () => {
    setIsEditMode(false);
    editForm.reset();
  };

  return (
    <div className=" flex flex-col">
      <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-6">
        회원 정보
      </HeadingParagraph>
      <div className="flex flex-col items-center justify-center">
        <Avatar size={84} imgSrc="" />
        <Paragraph variant={'black'} className="mt-2">
          {data?.data.data.nickname}
        </Paragraph>
        <form
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

          {/* email */}
          <div className="flex flex-col md:flex-row md:items-center md:gap-4">
            <span className="w-20 text-sm text-[#4568DC] md:text-end">이메일</span>
            <div className="flex flex-grow text-[#343539]">{data?.data.data.email}</div>
          </div>

          <div className="flex flex-col">
            <div className="flex flex-col md:flex-row md:items-center md:gap-4">
              <span className="w-20 text-sm text-[#4568DC] md:text-end">닉네임</span>
              {isEditMode ? (
                <Input
                  placeholder="닉네임을 작성해주세요."
                  className="flex-grow py-2"
                  {...editForm.register('nickname')}
                />
              ) : (
                <div className="flex flex-grow text-[#343539]">{data?.data.data.nickname}</div>
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

          {/* change password */}
          <div className="flex flex-col">
            <div className="flex flex-col md:flex-row md:items-center md:gap-4">
              <span className="w-20 text-sm text-[#4568DC] md:text-end">비밀번호</span>
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
            {isEditMode && (
              <div className="flex justify-end">
                <Paragraph variant={'red'} size="xs" className=" mt-1">
                  <span>{editForm.formState.errors.password?.message}</span>
                </Paragraph>
              </div>
            )}
          </div>

          {isEditMode && (
            <div className="flex flex-col">
              <div className="flex flex-col md:flex-row md:items-center md:gap-4">
                <span className="w-20 text-sm text-[#4568DC] md:text-end">비밀번호 확인</span>
                <Input
                  type={'password'}
                  placeholder="다시 한번 비밀번호를 입력해 주세요"
                  className="flex-grow py-2"
                  {...editForm.register('checkpassword')}
                />
              </div>
              <div className="flex justify-end">
                <Paragraph variant={'red'} size="xs" className=" mt-1">
                  {editForm.formState.errors.checkpassword?.message}
                </Paragraph>
              </div>
            </div>
          )}

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
      </div>
      <div className="flex justify-end">
        <Link to="/mypage/signout" className="text-sm text-[#bbb] hover:text-red-400">
          회원 탈퇴하기
        </Link>
      </div>
    </div>
  );
};

const profileSchema = z
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

export type EditProfileTypes = z.infer<typeof profileSchema>;

export default MyPage;
