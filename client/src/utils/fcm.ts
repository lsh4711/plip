import instance from '@/queries/axiosinstance';
import { initializeApp } from 'firebase/app';
import { Messaging, getMessaging, getToken, onMessage } from 'firebase/messaging';

const config = {
  apiKey: import.meta.env.VITE_FB_API_KEY,
  authDomain: import.meta.env.VITE_FB_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FB_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FB_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FB_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FB_APP_ID,
  measurementId: import.meta.env.VITE_FB_MEASUREMENT_ID,
};

const app = initializeApp(config);
export const messaging = getMessaging();

export const getFCMToken = () => {
  getToken(messaging, {
    vapidKey: import.meta.env.VITE_FB_VAPID_KEY,
  })
    .then((token) => {
      console.log(token);
      if (token) {
        instance
          .post('/api/pushs/write', {
            pushToken: token,
          })
          .then((res) => {
            console.log(res);
          });
      } else {
        console.log('No registration token available. Request permission to generate one.');
      }
    })
    .catch((err) => {
      console.log('An error occurred while retrieving token. ', err);
    });
};
