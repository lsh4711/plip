import { Outlet } from 'react-router-dom';
import Header from './components/common/Header';
import ToastContainer from './components/ui/toast/ToastContainer';

import useGetAccessTokenQuery from './queries/auth/useGetAccessTokenQuery';

export default function App() {
  const getAccessToken = useGetAccessTokenQuery();

  return (
    <>
      <Header />
      <ToastContainer />
      <Outlet />
    </>
  );
}
