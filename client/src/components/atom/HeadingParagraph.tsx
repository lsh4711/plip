import { cn } from '@/utils';
import { cva, VariantProps } from 'class-variance-authority';
import React, { HTMLAttributes } from 'react';

const HeadingVariants = cva(
  `
   font-bold smooth duration-300 transition-all
  `,
  {
    variants: {
      size: {
        default: 'text-[1.125rem]',
        sm: 'text-[1.375rem]',
        md: 'text-[1.625rem]',
        lg: 'text-[1.875rem]',
        xl: 'text-[3.375rem]',
      },
      variant: {
        default: ' text-white',
        blue: 'text-[#4568DC]',
        gray: ' text-[#505050]',
        darkgray: 'text-[#343539]',
      },
    },
    defaultVariants: {
      size: 'default',
      variant: 'default',
    },
  }
);

interface HeadingParagraphProps
  extends HTMLAttributes<HTMLHeadingElement>,
    VariantProps<typeof HeadingVariants> {
  children: React.ReactNode;
  className?: string;
}

const HeadingParagraph = ({
  children,
  size,
  variant,
  className,
  ...attributes
}: HeadingParagraphProps) => {
  return (
    <h2 className={cn(HeadingVariants({ size, variant }), className)} {...attributes}>
      {children}
    </h2>
  );
};

export default HeadingParagraph;
