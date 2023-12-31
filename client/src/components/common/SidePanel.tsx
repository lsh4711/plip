import { cn } from '@/utils';
import { cva } from 'class-variance-authority';
import { useState } from 'react';
import SideToggleButton from './SideToggleButton';

export const SidePanelVariants = cva(
  `
  opening fixed top-0 z-50 flex h-screen w-72 flex-col bg-white drop-shadow-2xl 2xl:w-[340px]
  `,
  {
    variants: {
      position: {
        left: ' left-0',
        right: ' right-0',
      },
      close: {
        true: '',
        false: '',
      },
    },
    compoundVariants: [
      {
        position: 'left',
        close: true,
        class: '-translate-x-full',
      },
      {
        position: 'right',
        close: true,
        class: 'translate-x-full',
      },
    ],
    defaultVariants: {
      position: 'right',
    },
  }
);

interface SidePanelProps {
  position: 'left' | 'right';
  className?: string;
  children: React.ReactNode;
}

function SidePanel({ position, className, children }: SidePanelProps) {
  const [isOpen, setIsOpen] = useState(true);

  return (
    <div className={cn([SidePanelVariants({ position, close: !isOpen }), className])}>
      <SideToggleButton isOpen={isOpen} position={position} onClick={() => setIsOpen(!isOpen)} />
      {children}
    </div>
  );
}

export default SidePanel;
