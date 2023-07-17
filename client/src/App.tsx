import { Outlet } from 'react-router-dom';
import Header from './components/common/Header';
import ToastContainer from './components/ui/toast/ToastContainer';
import { useEffect } from 'react';
import instance from './queries/axiosinstance';

export default function App() {
  // useEffect(() => {
  //   instance.get('/api/tokens', { withCredentials: true }).then((data) => console.log(data));
  // }, []);
  return (
    <>
      <Header />
      <ToastContainer />
      <Outlet />
    </>
  );
}
