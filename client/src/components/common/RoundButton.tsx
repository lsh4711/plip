import { ButtonHTMLAttributes } from 'react';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

const RoundButton = ({ children, ...attributes }: Props) => {
  return (
    <button
      className="flex h-10 w-10 items-center justify-center rounded-full border border-[#BBBBBB] bg-white drop-shadow-lg hover:bg-[#eeeeee]"
      {...attributes}
    >
      {children}
    </button>
  );
};

export default RoundButton;
