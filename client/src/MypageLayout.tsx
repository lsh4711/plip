import { Outlet } from 'react-router-dom';

import { MypageSideNav } from './components';

export default function MypageLayout() {
  return (
    <div className="flex h-screen w-screen">
      <MypageSideNav />
      <main className="flex h-full w-full flex-1 flex-col px-8 pb-12 pt-28 md:ml-56">
        <Outlet />
      </main>
    </div>
  );
}
