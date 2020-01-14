import './home.scss';

import React from 'react';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Translate} from 'react-jhipster';
import {connect} from 'react-redux';
import {Alert, Col, Row, Container} from 'reactstrap';
import {Notification} from "app/modules/notification/notification";
import ActivityStream from "app/modules/home/activity-stream";

export interface IHomeProp extends StateProps, RouteComponentProps<{ url: string }> {};

export const Home = (props: IHomeProp) => {
  const {account} = props;

  return (
    <Container fluid="true">
        {account && account.login ? (
          <>
          <Row sm="12">
              <h2>
                <Translate contentKey="home.logged.title" interpolate={{username: account.login}}/>
              </h2>
          </Row>
          <Row sm="12" className="flex-md-row-reverse">
            <Col sm="12" md="12" lg="4">
              <h2>
                <Translate contentKey="home.logged.information">Information</Translate>
                <p className="lead"><Translate contentKey="home.logged.informationText">Valuable information about your application can be found here!</Translate></p>
              </h2>
              <Notification/>
            </Col>
            <Col sm="12" md="12" lg="8">
              <ActivityStream match={props.match} location={props.location} history={props.history} />
            </Col>
          </Row>
          </>
        ) : (
          <Row>
          <Col sm="12" md="12" lg="8">
            <h2>
              <Translate contentKey="home.title"/>
            </h2>
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
          </Col>
          </Row>
        )}
    </Container>

  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
