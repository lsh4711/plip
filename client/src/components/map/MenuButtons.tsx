import { GiShare } from '@react-icons/all-files/gi/GiShare';
import { MdAddLocation } from '@react-icons/all-files/md/MdAddLocation';
import { MdHome } from '@react-icons/all-files/md/MdHome';
import { useNavigate, useParams } from 'react-router-dom';

import { ReactComponent as ProfileIcon } from '@/assets/icons/profile.svg';
import useModal from '@/hooks/useModal';
import { useState } from 'react';
import Confirm from '../common/Confirm';
import RoundButton from '../common/RoundButton';
import SharesButtons from '../common/SharesButtons';

const MenuButtons = () => {
  const navigate = useNavigate();
  const [openModal] = useModal();
  const { id } = useParams();

  const [isClickedShare, setIsClickedShare] = useState(false);

  return (
    <div className="absolute bottom-6 left-6 z-50 flex gap-2">
      <RoundButton
        onClick={() =>
          openModal(({ isOpen, close }) => (
            <Confirm
              isOpen={isOpen}
              onClose={close}
              type={'warning'}
              title={'주의'}
              content="저장되지 않은 계획은 사라집니다. 마이페이지로 이동할까요?"
              primaryLabel={'이동하기'}
              secondaryLabel="취소"
              onClickPrimaryButton={() => {
                navigate('/mypage/mytrip');
                close();
              }}
              onClickSecondaryButton={close}
            />
          ))
        }
      >
        <ProfileIcon className="h-6 w-6 rounded-full bg-white 2xl:h-8 2xl:w-8" />
      </RoundButton>
      <RoundButton
        onClick={() =>
          openModal(({ isOpen, close }) => (
            <Confirm
              isOpen={isOpen}
              onClose={close}
              type={'warning'}
              title={'주의'}
              content="저장되지 않은 계획은 사라집니다. 홈으로 이동할까요?"
              primaryLabel={'이동하기'}
              secondaryLabel="취소"
              onClickPrimaryButton={() => {
                navigate('/');
                close();
              }}
              onClickSecondaryButton={close}
            />
          ))
        }
      >
        <MdHome size={24} color="#bbb" />
      </RoundButton>
      <RoundButton onClick={() => setIsClickedShare(!isClickedShare)}>
        <GiShare size={24} color="#bbb" />
      </RoundButton>
      {isClickedShare && <SharesButtons scheduleId={Number(id)} />}
      <RoundButton>
        <MdAddLocation size={24} color="#bbb" />
      </RoundButton>
    </div>
  );
};

export default MenuButtons;
