import React, {useEffect, useState} from 'react';
import {Alert, Button} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {isPermissionGranted, requestNotificationPermission} from "app/shared/util/notification-utils";
import {store} from "app/index";

export const Notification = () => {

  const [permissionGranted, setPermissionGranted] = useState(false);

  useEffect(() => {
    setPermissionGranted(isPermissionGranted());
  }, []);

  const requestPermission = () => {
    setPermissionGranted(requestNotificationPermission(store));
  };

  return (
    <>
    { !permissionGranted && (
        <Alert color="info">
          <p><Translate contentKey="notification.button.text">It seems like notifications are not enabled. Enable them now to stay informed about the latest activities within your own projects or the ones you participate in.</Translate></p>
          <Button color="primary" onClick={() => requestPermission()}><Translate contentKey="notification.button.buttonText">Enable Notifications</Translate></Button>
        </Alert>
    )}
    </>
  )
};
