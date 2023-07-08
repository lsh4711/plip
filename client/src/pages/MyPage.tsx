import { Button, HeadingParagraph, Input, Paragraph } from '@/components';
import { useState } from 'react';
import { ReactComponent as EditImage } from '@/assets/icons/camera.svg';
import { ReactComponent as EditIcon } from '@/assets/icons/edit.svg';
import { ReactComponent as CloseIcon } from '@/assets/icons/close.svg';
import { ReactComponent as DoneIcon } from '@/assets/icons/check.svg';

import { Link } from 'react-router-dom';

import Avatar from '@/components/common/Avatar';
interface MyPageProps {}

const MyPage = ({}: MyPageProps) => {
  const nickname = '전설유길종전설성지현'; // 임시 변수
  const [isEditNickName, setIsEditNickName] = useState(false);
  const [isEditEmail, setIsEditEmail] = useState(false);
  const [isRequestedEmailAuthentication, setIsRequestedEmailAuthentication] = useState(false);

  const [userEmail, setUserEmail] = useState('ryubogum@plip.io');

  const onEditHandler = (
    state: boolean,
    setState: React.Dispatch<React.SetStateAction<boolean>>
  ) => {
    setState(!state);
  };

  return (
    <div className=" flex w-full">
      <nav className=" sticky right-0 top-0 h-screen border-r border-zinc-400">하이욤</nav>
      <article className="w-full p-[2.5rem]">
        <HeadingParagraph variant={'darkgray'} size={'md'} className="mb-32">
          프로필 수정
        </HeadingParagraph>
        <div className="flex justify-center">
          <form className="flex w-[500px] flex-col gap-y-6">
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
              <div className="flex">
                <Input
                  value={nickname}
                  placeholder="변경할 닉네임을 작성해주세요."
                  className="h-[52px]"
                  disabled={!isEditNickName}
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
                      {/* <Button
                        type="button"
                        hovercolor={'default'}
                        onClick={onEditNickNameHandler}
                        className="p-0"
                      >
                        <CloseIcon />
                      </Button>
                      <Button
                        type="button"
                        hovercolor={'default'}
                        onClick={onEditNickNameHandler}
                        className="p-0"
                      >
                        <DoneIcon />
                      </Button> */}
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
            </div>

            {/* edit email */}
            <div className="flex gap-4">
              <Input
                value={userEmail}
                placeholder="이메일을 입력해 주세요."
                className="w-[300px]"
                disabled={!isEditEmail}
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
                    onClick={() =>
                      onEditHandler(
                        isRequestedEmailAuthentication,
                        setIsRequestedEmailAuthentication
                      )
                    }
                  >
                    요청하기
                  </Button>
                </>
              )}
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
                />
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                <p></p>
              </Paragraph>
            </div>

            <div className="">
              <div className=" flex">
                <Input
                  type={'password'}
                  placeholder="다시 한번 비밀번호를 입력해 주세요"
                  className=" flex-grow"
                />
              </div>
              <Paragraph variant={'red'} size="xs" className=" mt-1">
                <p></p>
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

export default MyPage;
