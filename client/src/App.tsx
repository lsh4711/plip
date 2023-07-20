import { Outlet } from 'react-router-dom';
import Header from './components/common/Header';
import ToastContainer from './components/ui/toast/ToastContainer';

export default function App() {
  return (
    <>
      <Header />
      <ToastContainer />
      <Outlet />
    </>
  );
}
