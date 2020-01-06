const config = {
  VERSION: process.env.VERSION
};

export default config;

export const SERVER_API_URL = process.env.SERVER_API_URL;

export const AUTHORITIES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER'
};

export const messages = {
  DATA_ERROR_ALERT: 'Internal Error'
};

export const firebaseConfig = {
  apiKey: 'AIzaSyC5nc4-bbHxNoGQ178UD0E8lRGcziXeYA4',
  authDomain: 'norisknofun-id.firebaseapp.com',
  databaseURL: 'https://norisknofun-id.firebaseio.com',
  projectId: 'norisknofun-id',
  storageBucket: 'norisknofun-id.appspot.com',
  messagingSenderId: '878002592319',
  appId: '1:878002592319:web:150c69d4a3eb844107f68a',
  measurementId: 'G-QBCFL7G5TB'
};

export const APP_DATE_FORMAT = 'DD/MM/YY HH:mm';
export const APP_TIMESTAMP_FORMAT = 'DD/MM/YY HH:mm:ss';
export const APP_LOCAL_DATE_FORMAT = 'DD/MM/YYYY';
export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const APP_LOCAL_DATETIME_FORMAT_Z = 'YYYY-MM-DDTHH:mm Z';
export const APP_WHOLE_NUMBER_FORMAT = '0,0';
export const APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT = '0,0.[00]';
