import { Outlet } from 'react-router-dom';
import Header from './components/common/Header';
import ToastContainer from './components/ui/toast/ToastContainer';
import '@/utils/fcm.ts';
import useToast from './hooks/useToast';
import { messaging } from '@/utils/fcm';
import { onMessage } from 'firebase/messaging';
import { requestPermission } from './utils/browser/requestNotification';

export default function App() {
  const toast = useToast();
  requestPermission();

  /**
   * 푸시 알림 관련 함수입니다.
   * @param {Messaging} messaging
   * @callback
   * @return {void}
   **/
  onMessage(messaging, (payload) => {
    const body = payload.notification?.body;
    toast({ content: `${body}`, type: 'default' });
  });

  return (
    <>
      <Header />
      <ToastContainer />
      <Outlet />
    </>
  );
}
