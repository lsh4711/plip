import { cn } from '@/utils';
import { VariantProps, cva } from 'class-variance-authority';

import React from 'react';

const toastVariants = cva(
  `
   rounded-lg text-sm py-2 px-6 my-1 min-w-[250px] flex justify-center items-center text-center
   bg-white bg-opacity-60 shadow-md smooth
  `,
  {
    variants: {
      type: {
        default: 'text-zinc-400',
        warning: 'text-red-400 ',
        success: 'text-sky-400',
      },
    },
    defaultVariants: {
      type: 'default',
    },
  }
);

export type ToastProp = VariantProps<typeof toastVariants>;

interface ToastItemProps extends VariantProps<typeof toastVariants> {
  children: React.ReactNode;
}

const ToastItem = ({ type, children }: ToastItemProps) => {
  return <li className={cn(toastVariants({ type }))}>{children}</li>;
};

export default ToastItem;
