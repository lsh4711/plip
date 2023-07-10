import { Outlet, useMatch } from 'react-router-dom';

import Header from './components/common/Header';
import useInstance from './hooks/useInstance';
import { useEffect } from 'react';

export default function App() {
  const url = useMatch('/');
  const isHome = url?.pattern.end;
  useInstance();
  return (
    <>
      <Header isHome={isHome} />
      {isHome ? (
        <Outlet />
      ) : (
        <main className="flex flex-col items-center justify-center overflow-x-hidden py-24">
          <Outlet />
        </main>
      )}
    </>
  );
}
