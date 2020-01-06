import './home.scss';

import React from 'react';
import {Link} from 'react-router-dom';
import {Translate} from 'react-jhipster';
import {connect} from 'react-redux';
import {Alert, Col, Row} from 'reactstrap';
import Activity from 'app/entities/activity/';
import {Notification} from "app/modules/notification/notification";

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const {account} = props;

  return (
    <Row>
      <Col sm="12" md="9" lg="6">
        <h2>
          <Translate contentKey="home.title"/>
        </h2>
        {account && account.login ? (
          <div>
            <Notification/>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{username: account.login}}/>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix"/>
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"/>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix"/>
            </Alert>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount"/>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link"/>
              </Link>
            </Alert>
          </div>
        )}

        <h3>Color concept:</h3>
        <h2>Overall</h2>
        <Alert color="primary">
          This is a primary alert — check it out!
        </Alert>
        <Alert color="secondary">
          This is a secondary alert — check it out!
        </Alert>
        <h2>Risk classification</h2>
        <Alert color="critical">
          critical
        </Alert>
        <Alert color="check">
          check
        </Alert>
        <Alert color="ok">
          ok
        </Alert>

        <h2>Further default colors</h2>
        <Alert color="success">
          This is a success alert — check it out!
        </Alert>
        <Alert color="danger">
          This is a danger alert — check it out!
        </Alert>
        <Alert color="warning">
          This is a warning alert — check it out!
        </Alert>
        <Alert color="info">
          This is a info alert — check it out!
        </Alert>
        <Alert color="light">
          This is a light alert — check it out!
        </Alert>
        <Alert color="dark">
          This is a dark alert — check it out!
        </Alert>

      </Col>
    </Row>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
