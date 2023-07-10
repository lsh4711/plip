import { ModalDispatchContext, ModalStateContext } from '@/contexts/modal/ModalContext';
import { Fragment, useContext } from 'react';

function ModalRenderer() {
  const openedModals = useContext(ModalStateContext);
  const { close } = useContext(ModalDispatchContext);

  return (
    <>
      {[...openedModals].map(([key, { id, isOpen, render }]) => {
        return <Fragment key={key}>{render({ isOpen, close: () => close(id) })}</Fragment>;
      })}
    </>
  );
}

export default ModalRenderer;
