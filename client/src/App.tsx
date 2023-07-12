import { Outlet, useLocation, useMatch } from 'react-router-dom';
import { ErrorBoundary } from 'react-error-boundary';
import Header from './components/common/Header';
import useInstance from './hooks/useInstance';
import { useEffect } from 'react';
import ToastContainer from './components/ui/toast/ToastContainer';
import useError from './hooks/useError';
import ErrorFallback from './components/helper/ErrorFallback';

export default function App() {
  const url = useMatch('/');
  const isHome = url?.pattern.end;
  useInstance();
  return (
    <>
      <Header isHome={isHome} />
      <ToastContainer />
      <ErrorBoundary FallbackComponent={ErrorFallback}>
        {isHome ? (
          <Outlet />
        ) : (
          <main className="flex flex-col items-center justify-center overflow-x-hidden py-24">
            <Outlet />
          </main>
        )}
      </ErrorBoundary>
    </>
  );
}
