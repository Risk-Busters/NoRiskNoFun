import React, { useState, useEffect } from 'react';
import { getUser } from '../administration/user-management/user-management.reducer';
import { IRootState } from 'app/shared/reducers';
import { RouteComponentProps, Redirect } from 'react-router-dom';
import { connect } from 'react-redux';

export interface IProfileProps extends StateProps, DispatchProps, RouteComponentProps<{ login?: string }> {}

export const Profile = (props: IProfileProps) => {
  const getUserLogin = (): string => {
    if (props.match.params.login) {
      return props.match.params.login;
    }
    props.history.push(`${props.match.url}/${props.currentLogin}/`);
    return props.currentLogin;
  };

  useEffect(() => {
    props.getUser(getUserLogin());
  }, []);

  const { user } = props;

  return (
    <>
      <div>{user.firstName}</div>
    </>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
  currentLogin: storeState.authentication.account.login
});

const mapDispatchToProps = { getUser };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Profile);
