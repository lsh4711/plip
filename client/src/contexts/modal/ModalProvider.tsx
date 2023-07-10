import ModalRenderer from '@/components/ui/ModalRenderer';
import React, { useMemo, useState } from 'react';
import { Modal, ModalDispatchContext, ModalStateContext } from './ModalContext';

type Props = {
  children: React.ReactNode;
};

let id = 1;

const ModalProvider = ({ children }: Props) => {
  const [openedModals, setOpenedModals] = useState<Map<number, Modal>>(new Map());

  const open = (render: Modal['render']) => {
    let modalId = id++;
    setOpenedModals((prev) => new Map([...prev, [modalId, { id: modalId, isOpen: true, render }]]));
  };

  const close = (id: Modal['id']) => {
    setOpenedModals((prev) => {
      const newState = new Map(prev);
      newState.delete(id);
      return newState;
    });
  };

  const dispatch = useMemo(() => ({ open, close }), []);

  return (
    <ModalStateContext.Provider value={openedModals}>
      <ModalDispatchContext.Provider value={dispatch}>
        {children}
        <ModalRenderer />
      </ModalDispatchContext.Provider>
    </ModalStateContext.Provider>
  );
};

export default ModalProvider;
