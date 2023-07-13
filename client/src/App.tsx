import { Outlet } from 'react-router-dom';
import { ErrorBoundary } from 'react-error-boundary';
import Header from './components/common/Header';
import useInstance from './hooks/useInstance';
import ToastContainer from './components/ui/toast/ToastContainer';
import ErrorFallback from './components/helper/ErrorFallback';

export default function App() {
  useInstance();

  return (
    <>
      <Header />
      <ToastContainer />
      <ErrorBoundary FallbackComponent={ErrorFallback}>
        <Outlet />
      </ErrorBoundary>
    </>
  );
}
