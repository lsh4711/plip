import { Link } from 'react-router-dom';
import { menus } from '@/datas/menus';
import { ReactComponent as LogoutIcon } from '@/assets/icons/logout.svg';
import useLogoutMutation from '@/queries/useLogoutMutation';
const DropDownMenus = () => {
  const logoutMutation = useLogoutMutation();
  return (
    <div
      className={`absolute right-[-2.25rem] top-9 flex w-[200px] flex-col rounded-lg border bg-white shadow-md`}
    >
      {menus.map((item, index) => (
        <div key={index} className="w-full border-b-2 p-4 last:border-b-0">
          <Link to={item.route} className="flex gap-2 text-black">
            {item.icon}
            {item.name}
          </Link>
        </div>
      ))}
      <div
        className="w-full border-b-2 p-4 last:border-b-0"
        onClick={() => logoutMutation.mutate()}
      >
        <button className="flex gap-2 text-black">
          <LogoutIcon />
          로그아웃
        </button>
      </div>
    </div>
  );
};

export default DropDownMenus;
