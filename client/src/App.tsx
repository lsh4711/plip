import { Outlet } from 'react-router-dom';
import Header from './components/common/Header';
import ToastContainer from './components/ui/toast/ToastContainer';
import { useEffect } from 'react';
import useGetAccessTokenQuery from './queries/auth/useGetAccessTokenQuery';

export default function App() {
  // const getTokenQuery = useGetAccessTokenQuery();
  // useEffect(() => {
  //   getTokenQuery.refetch();
  // }, []);
  return (
    <>
      <Header />
      <ToastContainer />
      <Outlet />
    </>
  );
}
