import { cn } from '@/utils';
import { cva, VariantProps } from 'class-variance-authority';
import React, { ComponentPropsWithoutRef } from 'react';

const ParagraphVariants = cva(
  `
   font-normal smooth duration-300 transition-all
  `,
  {
    variants: {
      size: {
        xs: 'text-[0.875rem]',
        default: 'text-[1.125rem]',
        sm: 'text-[1.375rem]',
        md: 'text-[1.75rem]',
        xl: 'text-[3.375rem]',
      },
      variant: {
        default: ' text-white',
        blue: 'text-[#3E68FF]',
        gray: ' text-[#BBBBBB]',
        black: ' text-black',
      },
    },
    defaultVariants: {
      size: 'default',
      variant: 'default',
    },
  }
);

interface ParagraphProps
  extends ComponentPropsWithoutRef<'p'>,
    VariantProps<typeof ParagraphVariants> {
  children: React.ReactNode;
  className?: string;
}

const Paragraph = ({ children, className, ...attributes }: ParagraphProps) => {
  return (
    <p
      className={cn(
        ' smooth text-xs text-zinc-400 dark:text-zinc-400 md:text-sm lg:text-base',
        className
      )}
      {...attributes}
    >
      {children}
    </p>
  );
};

export default Paragraph;
