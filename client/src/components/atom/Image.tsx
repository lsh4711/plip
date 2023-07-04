import { cva, VariantProps } from 'class-variance-authority';
import { ComponentPropsWithoutRef } from 'react';

const ImageVariants = cva(
  `
  object-cover duration-300 transition-all
  `,
  {
    variants: {
      size: {
        sm: ' w-[180px] h-[180px]',
        md: ' w-[200px] h-[250px]',
        lg: ' w-[280px] h-[180px]',
        xl: ' w-[300px] h-[200px]',
      },
      hoveropacity: {
        default: '',
        active: 'hover:opacity-70',
      },
      rounded: {
        default: '',
        active: 'rounded-lg',
      },
    },
    defaultVariants: {
      size: 'sm',
      hoveropacity: 'default',
      rounded: 'default',
    },
  }
);

interface ImageProps extends ComponentPropsWithoutRef<'img'>, VariantProps<typeof ImageVariants> {
  src?: string;
}

const Image = ({
  src = '/dummy/dummy300x300.webp',
  hoveropacity,
  rounded,
  size,
  ...attributes
}: ImageProps) => {
  return (
    <img src={src} className={ImageVariants({ hoveropacity, rounded, size })} {...attributes} />
  );
};

export default Image;
