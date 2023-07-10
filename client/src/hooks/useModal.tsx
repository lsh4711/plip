import { Modal, ModalDispatchContext } from '@/contexts/modal/ModalContext';
import { useContext } from 'react';

function useModal() {
  const { open } = useContext(ModalDispatchContext);

  const openModal = (render: Modal['render']) => {
    open(render);
  };

  return [openModal];
}

export default useModal;
