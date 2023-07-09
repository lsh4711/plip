import { createContext } from 'react';

export type Modal = {
  id: number;
  isOpen: boolean;
  render: (params: { isOpen: boolean; close: () => void }) => React.ReactNode;
};

export type ModalDispatch = {
  open: (render: Modal['render']) => void;
  close: (id: Modal['id']) => void;
};

export const ModalDispatchContext = createContext<ModalDispatch>({
  open: () => {},
  close: () => {},
});

export const ModalStateContext = createContext<Map<number, Modal>>(new Map());
