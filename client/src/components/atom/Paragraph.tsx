import { cn } from '@/utils';
import { cva, VariantProps } from 'class-variance-authority';
import React, { ComponentPropsWithoutRef } from 'react';

const ParagraphVariants = cva(
  `
   smooth duration-300 transition-all
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
        default: 'text-zinc-400 dark:text-zinc-400',
        white: ' text-white',
        blue: 'text-[#3E68FF] dark:text-[#3E68FF]',
        gray: ' text-[#BBBBBB]',
        black: ' text-black',
        red: 'text-red-500',
      },
      weight: {
        default: 'font-normal',
        bold: 'font-bold',
      },
    },
    defaultVariants: {
      size: 'default',
      variant: 'default',
      weight: 'default',
    },
  }
);

interface ParagraphProps
  extends ComponentPropsWithoutRef<'p'>,
    VariantProps<typeof ParagraphVariants> {
  children: React.ReactNode;
  className?: string;
}

const Paragraph = ({
  children,
  className,
  size,
  variant,
  weight,
  ...attributes
}: ParagraphProps) => {
  return (
    <p className={cn(ParagraphVariants({ size, variant, weight }), className)} {...attributes}>
      {children}
    </p>
  );
};

export default Paragraph;
