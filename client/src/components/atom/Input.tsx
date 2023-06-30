import { cn } from '@/utils';
import { cva, VariantProps } from 'class-variance-authority';
import { ComponentPropsWithRef, forwardRef, Ref } from 'react';

const InputVariants = cva(
  `
 rounded-full, py-2 , px-4 transition-all duration-300 hover:scale-105
`,
  {
    variants: {
      variant: {
        default: '',
        search: 'bg-zinc-500',
        user: 'bg-zinc-100',
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
