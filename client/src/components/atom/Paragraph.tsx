import { cn } from '@/utils';
import React, { ComponentPropsWithoutRef } from 'react';

interface ParagraphProps extends ComponentPropsWithoutRef<'p'> {
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
