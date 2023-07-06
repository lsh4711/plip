import { Link } from 'react-router-dom';
import { menus } from '@/datas/menus';

interface DropDownProps {
  isOpen?: boolean;
}

const DropDownMenus = ({ isOpen }: DropDownProps) => {
  console.log(isOpen);
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
    </div>
  );
};

export default DropDownMenus;
