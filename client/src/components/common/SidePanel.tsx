import { cn } from '@/utils';
import { cva } from 'class-variance-authority';
import { useState } from 'react';
import SideToggleButton from './SideToggleButton';

export const SidePanelVariants = cva(
  `
  opening flex flex-col fixed h-screen w-96 bg-white drop-shadow-2xl
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
  children: React.ReactNode;
}

function SidePanel({ position, children }: SidePanelProps) {
  const [isOpen, setIsOpen] = useState(true);

  return (
    <div className={cn(SidePanelVariants({ position, close: !isOpen }))}>
      <SideToggleButton isOpen={isOpen} position={position} onClick={() => setIsOpen(!isOpen)} />
      {children}
    </div>
  );
}

export default SidePanel;
