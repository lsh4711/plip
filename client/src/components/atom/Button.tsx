import { cva, VariantProps } from 'class-variance-authority';
import { ButtonHTMLAttributes } from 'react';

import { cn } from '@/utils';

export const ButtonVariants = cva(
  `
  flex justify-center items-center rounded-xl 
  text-base font-bold transition-all
  duration-200 smooth
  `,
  {
    variants: {
      variant: {
        default: ' text-[#343539]',
        ring: ' ring-1 ring-[#bdbdbd] text-[#bdbdbd]',
        primary: 'bg-[#4568DC] text-white dark:text-white',
        optional: ' text-[#bbbbbb] bg-[#F6F8FC]',
        warn: 'bg-[#F05E54] text-white dark:text-white',
      },
      size: {
        default: 'py-[0.625rem] px-4 ',
        lg: 'py-[0.844rem] px-4',
        full: ' flex items-center justify-center w-full py-[0.625rem]',
      },
      hovercolor: {
        default: '',
        active: 'hover:bg-gradient-to-r hover:from-[#4568dc] hover:to-[#b06ab3] ',
      },
      hoveropacity: {
        default: '',
        active: ' hover:opacity-70',
      },
      activecolor: {
        default: '',
        active: ' active:hover:bg-blue-700 hover:opacity-100',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
      hovercolor: 'active',
      hoveropacity: 'default',
      activecolor: 'default',
    },
  }
);

interface ButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof ButtonVariants> {
  children: React.ReactNode;
}

const Button = ({
  variant,
  size,
  hovercolor,
  hoveropacity,
  activecolor,
  children,
  className,
  ...attributes
}: ButtonProps) => {
  return (
    <button
      className={cn(
        ButtonVariants({ variant, size, hoveropacity, hovercolor, activecolor }),
        className
      )}
      {...attributes}
    >
      {children}
    </button>
  );
};

export default Button;
