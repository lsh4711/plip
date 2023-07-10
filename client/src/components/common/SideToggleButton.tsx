import { BsChevronDoubleLeft } from '@react-icons/all-files/bs/BsChevronDoubleLeft';
import { BsChevronDoubleRight } from '@react-icons/all-files/bs/BsChevronDoubleRight';
import { cva, VariantProps } from 'class-variance-authority';
import { ButtonHTMLAttributes } from 'react';

import { cn } from '@/utils';

export const SideToggleButtonVariants = cva(
  `
  flex h-16 w-5 items-center justify-center 2xl:h-20 2xl:w-6
  absolute top-1/2 -translate-y-1/2
  border-solid border-[#bbb] bg-white
  `,
  {
    variants: {
      position: {
        right: ' left-0 -translate-x-full rounded-l-md border-0 border-r-[0.1px]',
        left: ' right-0 translate-x-full rounded-r-md border-0 border-l-[0.1px]',
      },
    },
    defaultVariants: {
      position: 'right',
    },
  }
);

interface SideToggleButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof SideToggleButtonVariants> {
  isOpen: boolean;
}

function SideToggleButton({ isOpen, position, className, ...attributes }: SideToggleButtonProps) {
  return (
    <button className={cn(SideToggleButtonVariants({ position }), className)} {...attributes}>
      {(position === 'right' && isOpen) || (position === 'left' && !isOpen) ? (
        <BsChevronDoubleRight color="#4568DC" size={16} />
      ) : (
        <BsChevronDoubleLeft color="#4568DC" size={16} />
      )}
    </button>
  );
}

export default SideToggleButton;
