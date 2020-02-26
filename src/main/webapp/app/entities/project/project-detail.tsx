import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps, useParams, useHistory} from 'react-router-dom';
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

enum RiskUrlType {
  FINAL = 'final',
  DISCUSSION = 'toBeDiscussed',
  PROPOSED = 'proposed'
}

const ProjectDetail: React.FC<IProjectDetailProps> = (props) => {

  const [activeTab, setActiveTab] = useState('final');
  const { risktype } = useParams();
  const history = useHistory();

  useEffect(() => {
      props.getEntity(props.match.params.id);
      if (risktype && Object.values(RiskUrlType).includes(risktype as RiskUrlType)) {
        setActiveTab(risktype.toLowerCase());
      } else {
        setActiveTab(RiskUrlType.FINAL);
      }
  }, []);

  useEffect(() => {
    let newTabUrl = location.pathname;
    if (risktype) {
      newTabUrl = newTabUrl.replace(risktype, activeTab);
    } else {
      newTabUrl += newTabUrl.endsWith('/') ? activeTab : `/${activeTab}`;
    }
    history.push(newTabUrl);
  }, [activeTab]);

  const {projectEntity} = props;

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

        <Nav tabs>
          <NavItem>
            <NavLink className={classnames({ active: activeTab === RiskUrlType.FINAL })} onClick={() => setActiveTab(RiskUrlType.FINAL)} >
              <Translate contentKey="noRiskNoFunApp.project.riskCategories.final"/>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink className={classnames({ active: activeTab === RiskUrlType.DISCUSSION })} onClick={() => setActiveTab(RiskUrlType.DISCUSSION)} >
              <Translate contentKey="noRiskNoFunApp.project.riskCategories.toBeDiscussed"/>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink className={classnames({ active: activeTab === RiskUrlType.PROPOSED })} onClick={() => setActiveTab(RiskUrlType.PROPOSED)} >
              <Translate contentKey="noRiskNoFunApp.project.riskCategories.proposed"/>
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={activeTab}>
          <TabPane tabId={RiskUrlType.FINAL}>
            <ProjectRisks riskDiscussionStatus={"final"} match={props.match} location={props.location} history={props.history} />
          </TabPane>
          <TabPane tabId={RiskUrlType.DISCUSSION}>
            <ProjectRisks riskDiscussionStatus={"toBeDiscussed"} match={props.match} location={props.location} history={props.history} />
          </TabPane>
          <TabPane tabId={RiskUrlType.PROPOSED}>
            <ProjectRisks riskDiscussionStatus={"proposed"} match={props.match} location={props.location} history={props.history} />
          </TabPane>
        </TabContent>
      </div>
    );
};

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
