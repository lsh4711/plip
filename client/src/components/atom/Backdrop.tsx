type Props = {
  onClose?: () => void;
};

const Backdrop = ({ onClose }: Props) => {
  return <div className="fixed bottom-0 left-0 right-0 top-0 z-50 bg-black/40" onClick={onClose} />;
};

export default Backdrop;
