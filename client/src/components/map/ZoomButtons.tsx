import { MdAdd } from '@react-icons/all-files/md/MdAdd';
import { MdRemove } from '@react-icons/all-files/md/MdRemove';

import { Button } from '@/components';
import { cn } from '@/utils';

type Props = {
  onClickZoomIn: () => void;
  onClickZoomOut: () => void;
  className?: string;
};

const ZoomButtons = ({ onClickZoomIn, onClickZoomOut, className }: Props) => {
  return (
    <div className={cn(['flex flex-col gap-1', className])}>
      <Button
        variant={'optional'}
        hovercolor={'default'}
        hoveropacity={'default'}
        onClick={onClickZoomIn}
        className="border border-[#BBBBBB] bg-white p-2 drop-shadow-lg hover:bg-[#eee]"
      >
        <MdAdd size={24} />
      </Button>
      <Button
        variant={'optional'}
        hovercolor={'default'}
        hoveropacity={'default'}
        onClick={onClickZoomOut}
        className="border border-[#BBBBBB] bg-white p-2 drop-shadow-lg hover:bg-[#eee]"
      >
        <MdRemove size={24} />
      </Button>
    </div>
  );
};

export default ZoomButtons;
