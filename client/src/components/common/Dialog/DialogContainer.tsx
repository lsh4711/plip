import Backdrop from '@/components/atom/Backdrop';
import { createPortal } from 'react-dom';

type Props = {
  isOpen: boolean;
  className: string;
  children: React.ReactNode;
};

const DialogContainer = ({ isOpen, className, children }: Props) => {
  if (!isOpen) {
    return null;
  }

  return createPortal(
    <>
      <Backdrop />
      <div
        className={`absolute left-1/2 top-1/2 z-50 -translate-x-1/2 -translate-y-1/2 ${className}`}
      >
        {children}
      </div>
    </>,
    document.querySelector('#root')!
  );
};

export default DialogContainer;
