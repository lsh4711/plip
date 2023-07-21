import { menus } from '@/datas/menus';
import { FcUnlock } from '@react-icons/all-files/fc/FcUnlock';
import { Link } from 'react-router-dom';

import useLogoutMutation from '@/queries/auth/useLogoutMutation';
import { cn } from '@/utils';
import { VariantProps, cva } from 'class-variance-authority';

const DropDownMenusVariants = cva(
  `
  absolute z-50 flex flex-col bg-white shadow-md
  `,
  {
    variants: {
      variant: {
        pc: 'w-[200px] right-[-1.25rem] top-9 rounded-lg border',
        mobile: 'top-[76px] left-0 right-0 rounded-b-lg',
      },
    },
    defaultVariants: {
      variant: 'pc',
    },
  }
);

interface DropDownMenus extends VariantProps<typeof DropDownMenusVariants> {}

const DropDownMenus = ({ variant }: DropDownMenus) => {
  const logoutMutation = useLogoutMutation();
  return (
    <div className={cn(DropDownMenusVariants({ variant }))}>
      {menus.map((item, index) => (
        <Link
          key={item.name}
          to={item.route}
          className="border-b-[1.5px] p-4 text-black last:border-b-0"
        >
          <div key={index} className="flex w-full items-center gap-2">
            {item.icon}
            {item.name}
          </div>
        </Link>
      ))}
      <div
        className="w-full border-b-2 p-4 last:border-b-0"
        onClick={() => {
          logoutMutation.mutate('');
        }}
      >
        <button className="flex gap-2 text-black">
          <FcUnlock size={20} />
          로그아웃
        </button>
      </div>
    </div>
  );
};

export default DropDownMenus;
