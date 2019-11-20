import './home.scss';

import React from 'react';
import {Link} from 'react-router-dom';
import {Translate} from 'react-jhipster';
import {connect} from 'react-redux';
import {Alert, Col, Row} from 'reactstrap';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const {account} = props;

  return (
    <Row>
      <Col sm="6" md="9" lg="6">
        <h2>
          <Translate contentKey="home.title">Welcome, Java Hipster!</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">This is your homepage</Translate>
        </p>
        <p>Hello World!</p>
        {account && account.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{username: account.login}}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br/>- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br/>- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account
                yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
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
      <Col sm="6" md="3" lg="6" className="pad">
        <span className="hipster rounded"/>
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
