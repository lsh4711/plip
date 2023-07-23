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
import MypageForm from '@/components/forms/MypageForm';

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
        <MypageForm />
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
