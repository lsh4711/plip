import { cn } from '@/utils';
import { cva } from 'class-variance-authority';
import React from 'react';

interface SortingToolbarProps {}

const ToolbarButtonVariants = cva(
  `
  hover:bg-zinc-200 text-zinc-600 px-3 py-2 
  `,
  {
    variants: {
      location: {
        left: ' border-r border-zinc-400',
        middle: 'border-r border-zinc-400',
        right: '',
      },
      variant: {
        default: '',
        active: 'bg-zinc-200',
      },
    },
    defaultVariants: {
      location: 'middle',
      variant: 'default',
    },
  }
);

const SortingToolbar = ({}: SortingToolbarProps) => {
  return (
    <div className="flex max-w-[236px] rounded-lg border border-zinc-400">
      <button className={cn(ToolbarButtonVariants({ location: 'left' }))}>최신순</button>
      <button className={cn(ToolbarButtonVariants({ location: 'middle' }))}>오래된순</button>
      <button className={cn(ToolbarButtonVariants({ location: 'right' }))}>인기순</button>
    </div>
  );
};

export default SortingToolbar;
