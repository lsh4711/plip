import { cn } from '@/utils';
import React, { ComponentPropsWithoutRef } from 'react';

interface HeadingParagraphProps extends ComponentPropsWithoutRef<'h2'> {
  children: React.ReactNode;
  className?: string;
}

const HeadingParagraph = ({ children, className, ...attributes }: HeadingParagraphProps) => {
  return (
    <h2
      className={cn(
        ' smooth mb-4 text-2xl font-extrabold text-yellow-500 md:text-4xl lg:text-5xl',
        className
      )}
      {...attributes}
    >
      {children}
    </h2>
  );
};

export default HeadingParagraph;
