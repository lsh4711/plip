importScripts("https://www.gstatic.com/firebasejs/10.0.0/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/10.0.0/firebase-messaging-compat.js");

const config = {
  apiKey: "AIzaSyBbvdZQf6To6CgoCKdakV8_MN5gQcS8zMo",
  authDomain: "test-bc584.firebaseapp.com",
  projectId: "test-bc584",
  storageBucket: "test-bc584.appspot.com",
  messagingSenderId: "298406541244",
  appId: "1:298406541244:web:c79dfe11d95664bd899db9",
  measurementId: "G-RB9C934VXH"
};

firebase.initializeApp(config);

const messaging = firebase.messaging();
