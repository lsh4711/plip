import { Link } from 'react-router-dom';
import { menus } from '@/datas/menus';
import { ReactComponent as LogoutIcon } from '@/assets/icons/logout.svg';
import useLogoutMutation from '@/queries/useLogoutMutation';
const DropDownMenus = () => {
  const logoutMutation = useLogoutMutation();
  return (
    <div
      className={`absolute right-[-2.25rem] top-9 z-50 flex w-[200px] flex-col rounded-lg border bg-white shadow-md`}
    >
      {menus.map((item, index) => (
        <Link to={item.route} className="w-full border-b-2 p-4 text-black last:border-b-0">
          <div key={index} className="flex w-full gap-2 ">
            {item.icon}
            {item.name}
          </div>
        </Link>
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
