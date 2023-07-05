import { useEffect } from 'react';
import { Outlet, useMatch } from 'react-router-dom';
import Header from './components/common/Header';
import Home from './pages/Home';

export default function App() {
  const url = useMatch('/');
  const isHome = url?.pattern.end;

  // console.log(isHome);

  return (
    <>
      {isHome ? (
        <Home />
      ) : (
        <>
          <Header />
          <main className="flex flex-col items-center justify-center overflow-x-hidden py-24">
            <Outlet />
          </main>
        </>
      )}
    </>
  );
}
