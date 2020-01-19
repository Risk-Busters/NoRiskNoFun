importScripts('https://storage.googleapis.com/workbox-cdn/releases/4.3.1/workbox-sw.js');
importScripts('https://www.gstatic.com/firebasejs/7.6.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.6.1/firebase-messaging.js');

const firebaseConfig = {
  apiKey: "AIzaSyC5nc4-bbHxNoGQ178UD0E8lRGcziXeYA4",
  authDomain: "norisknofun-id.firebaseapp.com",
  databaseURL: "https://norisknofun-id.firebaseio.com",
  projectId: "norisknofun-id",
  storageBucket: "norisknofun-id.appspot.com",
  messagingSenderId: "878002592319",
  appId: "1:878002592319:web:150c69d4a3eb844107f68a",
  measurementId: "G-QBCFL7G5TB"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload) {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = 'No Risk No Fun';
  const notificationOptions = {
    body: payload.data.message,
    icon: '' // TODO: Add icon / app logo, link
  };

  return self.registration.showNotification(notificationTitle,
    notificationOptions);
});

workbox.core.skipWaiting();
workbox.core.clientsClaim();
workbox.routing.registerRoute(
  new RegExp('/'),
  new workbox.strategies.NetworkFirst()
);

workbox.precaching.precacheAndRoute(self.__precacheManifest);
