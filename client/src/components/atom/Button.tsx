import { cva, VariantProps } from 'class-variance-authority';
import { ButtonHTMLAttributes, FC } from 'react';

import { cn } from '@/utils';

export const ButtonVariants = cva(
  `
  flex justify-center items-center active:scale-95 rounded-xl 
  text-sm font-bold text-slate-100 transition-all shadow-md
  hover:scale-105 duration-200 smooth
  `,
  {
    variants: {
      variant: {
        default: ' shadow-none active:scale-100',
        grey: ' bg-slate-buttongrey ',
        blue: ' bg-accent-blue',
      },
      size: {
        default: '',
        md: ' w-[6.875rem] h-[2.375rem] text-[1rem] rounded-md',
        lg: 'w-[21.875rem] h-[7.5rem] text-[3rem] rounded-3xl',
        wlg: 'w-[24rem] h-[5.25rem] text-[2rem]',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  }
);

interface ButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof ButtonVariants> {
  label?: string;
  children?: React.ReactElement;
}

const Button: FC<ButtonProps> = ({ variant, size, children, label, className, ...attributes }) => {
  return (
    <button className={`${cn(ButtonVariants({ variant, size }))} ${className}`} {...attributes}>
      {children && children}
      {label && label}
    </button>
  );
};

export default Button;
