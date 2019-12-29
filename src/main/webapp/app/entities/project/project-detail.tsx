import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Nav, NavItem, NavLink, Row, TabContent, TabPane} from 'reactstrap';
import {TextFormat, Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import classnames from 'classnames';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './project.reducer';
import {APP_LOCAL_DATE_FORMAT} from 'app/config/constants';
import ProjectRisks from "app/entities/project-risks/project-risks";

export interface IProjectDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

function ProjectDetail(props) {

  useEffect(() => {
      props.getEntity(props.match.params.id);
  }, []);

  const {projectEntity} = props;

  const [activeTab, setActiveTab] = useState('1');

  const toggle = tab => {
    if(activeTab !== tab) setActiveTab(tab);
  };

  return (
      <div>
        <Row>
          <Col md="8">
            <h2>
              <Translate contentKey="noRiskNoFunApp.project.detail.title">Project</Translate> [<b>{projectEntity.id}</b>]
            </h2>
            <dl className="jh-entity-details">
              <dt>
              <span id="name">
                <Translate contentKey="noRiskNoFunApp.project.name">Name</Translate>
              </span>
              </dt>
              <dd>{projectEntity.name}</dd>
              <dt>
              <span id="description">
                <Translate contentKey="noRiskNoFunApp.project.description">Description</Translate>
              </span>
              </dt>
              <dd>{projectEntity.description}</dd>
              <dt>
              <span id="start">
                <Translate contentKey="noRiskNoFunApp.project.start">Start</Translate>
              </span>
              </dt>
              <dd>
                <TextFormat value={projectEntity.start} type="date" format={APP_LOCAL_DATE_FORMAT}/>
              </dd>
              <dt>
              <span id="end">
                <Translate contentKey="noRiskNoFunApp.project.end">End</Translate>
              </span>
              </dt>
              <dd>
                <TextFormat value={projectEntity.end} type="date" format={APP_LOCAL_DATE_FORMAT}/>
              </dd>
              <dt>
                <Translate contentKey="noRiskNoFunApp.project.owner">Owner</Translate>
              </dt>
              <dd>{projectEntity.owner ? projectEntity.owner.id : ''}</dd>
              <dt>
                <Translate contentKey="noRiskNoFunApp.project.user">User</Translate>
              </dt>
              <dd>
                {projectEntity.users
                  ? projectEntity.users.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === projectEntity.users.length - 1 ? '' : ', '}
                    </span>
                  ))
                  : null}{' '}
              </dd>
            </dl>
            <Button tag={Link} to="/entity/project" replace color="info">
              <FontAwesomeIcon icon="arrow-left"/>{' '}
              <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
            </Button>
            &nbsp;
            <Button tag={Link} to={`/entity/project/${projectEntity.id}/edit`} replace color="primary">
              <FontAwesomeIcon icon="pencil-alt"/>{' '}
              <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
            </Button>
          </Col>
        </Row>


        <Button tag={Link} to={`/entity/project/${projectEntity.id}/project-risks`} replace color="info">
          <span className="d-none d-md-inline">
              <Translate contentKey="noRiskNoFunApp.project.toProjectRisks" />
            </span>
        </Button>

        <Nav tabs>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '1' })}
              onClick={() => { toggle('1'); }}
            >
              Final
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '2' })}
              onClick={() => { toggle('2'); }}
            >
              Discussions
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classnames({ active: activeTab === '3' })}
              onClick={() => { toggle('3'); }}
            >
              Proposals
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={activeTab}>
          <TabPane tabId="1">
            <ProjectRisks projectRisksList={projectEntity.projectRisks} match={props.match} location={props.location} history={props.history} />
          </TabPane>
          <TabPane tabId="2">
            TODO: 2 Discussions
          </TabPane>
          <TabPane tabId="3">
            TODO: 3 Proposals
          </TabPane>
        </TabContent>
      </div>
    );
}

// TODO: insert directly as component and not as link
// <ProjectRisks projectRisksList={projectEntity.projectRisks} match={this.props.match} />

const mapStateToProps = ({project}: IRootState) => ({
  projectEntity: project.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectDetail);
