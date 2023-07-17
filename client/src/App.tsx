import { Outlet } from 'react-router-dom';
import Header from './components/common/Header';
import ToastContainer from './components/ui/toast/ToastContainer';
import { useEffect } from 'react';

export default function App() {
  useEffect(() => {});
  return (
    <>
      <Header />
      <ToastContainer />
      <Outlet />
    </>
  );
}
