import { Button, HeadingParagraph, Input, MypageSideNav, Paragraph } from '@/components';
import { ChangeEvent, useState } from 'react';
import { ReactComponent as EditImage } from '@/assets/icons/camera.svg';
import { ReactComponent as EditIcon } from '@/assets/icons/edit.svg';

import { useForm, SubmitHandler } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { passwordRegex, nicknameRegex } from '@/datas/constants';

import { Link } from 'react-router-dom';

import Avatar from '@/components/common/Avatar';
import useAuthRedirect from '@/hooks/useAuthRedirect';
interface MyPageProps {}

const MyPage = ({}: MyPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  const editForm = useForm<EditProfileTypes>({
    mode: 'all',
    resolver: zodResolver(profileSchema),
  });

  const onSubmit: SubmitHandler<EditProfileTypes> = (data) => {
    console.log(data);
  };

  // is edit?
  const [isEditNickName, setIsEditNickName] = useState(false);
  const [isEditEmail, setIsEditEmail] = useState(false);
  const [isRequestedEmailAuthentication, setIsRequestedEmailAuthentication] = useState(false);

  // valid
  const [isEmailValid, setIsEmailValid] = useState(true);
  const [isNicknameValid, setIsNicknameValid] = useState(true);

  // 임시 상태 변수
  const [userEmail, setUserEmail] = useState('ryubogum@plip.io');
  const [userNickname, setUserNickName] = useState('전설유길종전설성지현');

  const onChangeNickNameHandler = (e: ChangeEvent<HTMLInputElement>) => {
    setUserNickName(e.target.value);
  };

  const onEditHandler = (
    state: boolean,
    setState: React.Dispatch<React.SetStateAction<boolean>>
  ) => {
    setState(!state);
  };

  // 마이페이지 css 재구성 필요합니다. w-full 속성으로 인해 sidenav 공간이 잡아먹히고있어염..

  return (
    <div className=" flex">
      <MypageSideNav />
      <article className="w-full p-[2.5rem]">
        <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-32">
          프로필 수정
        </HeadingParagraph>
        <div className="flex justify-center">
          <form
            className="flex w-[500px] flex-col gap-y-6"
            onSubmit={editForm.handleSubmit(onSubmit)}
          >
            {/* edit avatar & nickname */}
            <div className="flex items-center gap-x-6">
              <div className="relative">
                <Avatar size={128} imgSrc="" />
                <Button
                  variant={'primary'}
                  className="absolute bottom-1 right-2 h-[34px] w-[34px] rounded-full"
                >
                  <Link to="#">
                    <EditImage />
                  </Link>
                </Button>
              </div>
              <div>
                <div className="flex">
                  <Input
                    value={userNickname}
                    placeholder="닉네임을 작성해주세요."
                    className="h-[52px]"
                    disabled={!isEditNickName}
                    {...editForm.register('nickname', { onChange: onChangeNickNameHandler })}
                  />
                  <div>
                    {!isEditNickName ? (
                      <Button
                        type="button"
                        hovercolor={'default'}
                        onClick={() => onEditHandler(isEditNickName, setIsEditNickName)}
                      >
                        <EditIcon width={24} height={24} />
                      </Button>
                    ) : (
                      <div className="ml-[10px] flex items-center gap-4">
                        <Button
                          type="button"
                          variant={'ring'}
                          hovercolor={'default'}
                          onClick={() => onEditHandler(isEditNickName, setIsEditNickName)}
                        >
                          취소
                        </Button>
                        <Button
                          type="button"
                          variant={'primary'}
                          onClick={() => onEditHandler(isEditNickName, setIsEditNickName)}
                        >
                          완료
                        </Button>
                      </div>
                    )}
                  </div>
                </div>
                <Paragraph variant={'red'} size="xs" className=" mt-1">
                  <span> {isNicknameValid ? null : '중복된 닉네임입니다.'}</span>
                  <span> {editForm.formState.errors.nickname?.message}</span>
                </Paragraph>
              </div>
            </div>

            {/* edit email */}
            <div>
              <div className="flex gap-4">
                <Input
                  value={userEmail}
                  placeholder="이메일을 입력해 주세요."
                  className="w-[300px]"
                  disabled={!isEditEmail}
                  {...editForm.register('email', {
                    onChange: (event: React.ChangeEvent<HTMLInputElement>) => {
                      const nowdata = editForm.getValues('email');
                      setUserEmail(nowdata);
                      console.log(nowdata);
                    },
                  })}
                />
                {!isEditEmail ? (
                  <Button
                    type="button"
                    variant={'primary'}
                    onClick={() => onEditHandler(isEditEmail, setIsEditEmail)}
                  >
                    변경
                  </Button>
                ) : (
                  <>
                    <Button
                      type="button"
                      variant={'ring'}
                      hovercolor={'default'}
                      onClick={() => {
                        onEditHandler(isEditEmail, setIsEditEmail);
                        if (isEditEmail && isRequestedEmailAuthentication) {
                          onEditHandler(
                            isRequestedEmailAuthentication,
                            setIsRequestedEmailAuthentication
                          );
                        }
                      }}
                    >
                      취소
                    </Button>
                    <Button
                      type="button"
                      variant={'primary'}
                      onClick={() => {
                        onEditHandler(
                          isRequestedEmailAuthentication,
                          setIsRequestedEmailAuthentication
                        );
                      }}
                      // disabled={
                      //   editForm.formState.errors.email?.message ||
                      //   editForm.getValues('email') !== ''
                      //     ? true
                      //     : false
                      // }
                    >
                      요청하기
                    </Button>
                  </>
                )}
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                {editForm.formState.errors.email?.message && editForm.getValues('email') !== ''
                  ? editForm.formState.errors.email.message
                  : null}
              </Paragraph>
            </div>

            {isRequestedEmailAuthentication && (
              <div className="flex gap-4">
                <Input className="w-[300px]" disabled={!isRequestedEmailAuthentication} />
                <Button type="button" variant={'primary'}>
                  인증하기
                </Button>
              </div>
            )}

            {/* change password */}
            <div className="">
              <div className=" flex">
                <Input
                  type={'password'}
                  placeholder="비밀번호를 입력해 주세요. (영문, 숫자, 특수문자 포함 8자 이상)."
                  className=" flex-grow"
                  {...editForm.register('password')}
                />
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                <span>{editForm.formState.errors.password?.message}</span>
              </Paragraph>
            </div>

            <div className="">
              <div className=" flex">
                <Input
                  type={'password'}
                  placeholder="다시 한번 비밀번호를 입력해 주세요"
                  className=" flex-grow"
                  {...editForm.register('checkpassword')}
                />
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                <p>{editForm.formState.errors.checkpassword?.message}</p>
              </Paragraph>
            </div>
            <div className="flex justify-center gap-4">
              <Button
                type="button"
                variant={'ring'}
                hovercolor={'default'}
                onClick={() => onEditHandler(isEditNickName, setIsEditNickName)}
              >
                취소
              </Button>
              <Button
                type="button"
                variant={'primary'}
                onClick={() => onEditHandler(isEditNickName, setIsEditNickName)}
              >
                완료
              </Button>
            </div>
          </form>
        </div>
      </article>
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
