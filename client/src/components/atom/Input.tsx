import { cn } from '@/utils';
import { cva, VariantProps } from 'class-variance-authority';
import { ComponentPropsWithRef, forwardRef, Ref } from 'react';

const InputVariants = cva(
  `
  py-[14.5px] pl-[17px] placeholder:text-[#BBBBBB] border border-[#BBBBBB] rounded-lg w-[460px]
   text-zinc-500
`,
  {
    variants: {
      variant: {
        default: '',
      },
    },
    defaultVariants: {
      variant: 'default',
    },
  }
);

interface InputProps extends ComponentPropsWithRef<'input'>, VariantProps<typeof InputVariants> {
  className?: string;
}

const Input = (
  { type, className, variant, ...attributes }: InputProps,
  ref: Ref<HTMLInputElement>
) => {
  return (
    <input
      type={type}
      {...attributes}
      ref={ref}
      className={cn(InputVariants({ variant }), className)}
    />
  );
};

export default forwardRef(Input);
