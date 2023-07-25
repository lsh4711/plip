import { getMessaging } from 'firebase/messaging';
import { getFCMToken } from '../fcm';

export const requestPermission = () => {
  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      console.log('알림 권한이 허용됨');
      const messaging = getMessaging();
      getFCMToken(messaging);
      // FCM 메세지 처리
    } else {
      console.log('알림 권한 허용 안됨');
    }
  });
};
