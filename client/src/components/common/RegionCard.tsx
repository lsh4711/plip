import { cva, VariantProps } from 'class-variance-authority';

import Image from '@/components/atom/Image';
import { cn } from '@/utils';

export const ImageWrapperVariants = cva(
  `
  w-full relative overflow-hidden rounded-lg md:h-full md:min-w-full select-none
  `,
  {
    variants: {
      type: {
        image: '',
        button: ' bg-black hover:-translate-y-1 cursor-pointer',
      },
    },
    defaultVariants: {
      type: 'button',
    },
  }
);

export const LabelVariants = cva(
  `
  absolute text-lg font-bold text-white
  `,
  {
    variants: {
      labelPosition: {
        topLeft: ' left-2 top-2',
        topRight: ' right-2 top-2',
        bottomLeft: ' left-2 bottom-2',
        bottomRight: ' right-2 bottom-2',
      },
    },
    defaultVariants: {
      labelPosition: 'topLeft',
    },
  }
);

interface Props
  extends VariantProps<typeof LabelVariants>,
    VariantProps<typeof ImageWrapperVariants> {
  imgUrl: string;
  label?: string;
  onClick?: () => void;
  isSelected?: boolean;
}

function RegionCard({ label, onClick, isSelected, imgUrl, labelPosition, type }: Props) {
  return (
    <div
      className={
        isSelected
          ? cn(ImageWrapperVariants({ type }), 'outline outline-[6px] outline-[#4568DC]')
          : cn(ImageWrapperVariants({ type }))
      }
      onClick={onClick}
    >
      <Image src={imgUrl} className={type ? 'opacity-100' : 'opacity-90'} />
      {label && <span className={cn(LabelVariants({ labelPosition }))}>{label}</span>}
    </div>
  );
}

export default RegionCard;
