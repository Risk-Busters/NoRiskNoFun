import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps, useHistory, useParams} from 'react-router-dom';
import {Button, Col, Nav, NavItem, NavLink, Progress, Row, TabContent, TabPane} from 'reactstrap';
import {TextFormat, Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import classnames from 'classnames';
import {IRootState} from 'app/shared/reducers';
import {getEntity} from './project.reducer';
import {getProjectActivity} from './project-activity.reducer';
import {APP_LOCAL_DATE_FORMAT} from 'app/config/constants';
import ProjectRisks from "app/entities/project-risks/project-risks";
import moment from "moment";

export interface IProjectDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

enum RiskUrlType {
  FINAL = 'final',
  DISCUSSION = 'tobediscussed',
  PROPOSED = 'proposed'
}

const ProjectDetail: React.FC<IProjectDetailProps> = (props) => {

  const [activeTab, setActiveTab] = useState('final');
  const [timeStatus, setTimeStatus] = useState(0);
  const { risktype } = useParams();
  const history = useHistory();

  useEffect(() => {
    props.getProjectActivity(props.match.params.id);
  }, []);

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

  const {projectEntity, projectActivityEntity} = props;

  useEffect(() => {
    if (projectEntity.end !== undefined && projectEntity.start !== undefined) {
      const now = moment();
      const startDate = moment(projectEntity.start);
      const endDate = moment(projectEntity.end);

      const totalMillisInRange = endDate.valueOf() - startDate.valueOf();
      const elapsedMillis = now.valueOf() - startDate.valueOf();
      const percentageRounded =  Math.round(Math.max(0, Math.min(100, 100 * (elapsedMillis / totalMillisInRange))));

      setTimeStatus(percentageRounded);
    }
  }, [projectEntity]);

  return (
      <div>
        {projectEntity ? (
          <Row>
            <Col md="8">
              <h2>
                <Translate contentKey="noRiskNoFunApp.project.detail.title">Project</Translate> <b>{projectEntity.name}</b>
              </h2>
              <dl className="jh-entity-details">
                <dt>
              <span id="description">
                <Translate contentKey="noRiskNoFunApp.project.description">Description</Translate>
              </span>
                </dt>
                <dd>{projectEntity.description}</dd>
                <dt>
                  <span id="projectDates">
                    <Translate contentKey="noRiskNoFunApp.project.projectDates"/>
                  </span>
                </dt>
                <dd>
                  <Row xs="2">
                    <Col>
                      <div className="text-left">
                        <TextFormat value={projectEntity.start} type="date" format={APP_LOCAL_DATE_FORMAT} />
                      </div>
                    </Col>
                    <Col>
                      <div className="text-right">
                        <TextFormat value={projectEntity.end} type="date" format={APP_LOCAL_DATE_FORMAT} />
                      </div>
                    </Col>
                  </Row>
                  <Progress value={timeStatus} >
                    {timeStatus} %
                  </Progress>
                </dd>
                <dt>
                  <Translate contentKey="noRiskNoFunApp.project.projectActivity" />
                </dt>
                <dd>{projectActivityEntity.projectActivityToday}</dd>
                <dt>
                  <Translate contentKey="noRiskNoFunApp.project.owner">Owner</Translate>
                </dt>
                <dd>{projectEntity.owner ? `${projectEntity.owner.firstName} ${projectEntity.owner.lastName}` : ''}</dd>
                <dt>
                  <Translate contentKey="noRiskNoFunApp.project.user">User</Translate>
                </dt>
                <dd>
                  {projectEntity.users
                    ? projectEntity.users.map((val, i) => (
                      <span key={val.id}>
                      <a href={`/profile/${val.login}`}> {val.firstName && val.lastName ? `${val.firstName} ${val.lastName}` : val.login}</a>
                        {i === projectEntity.users.length - 1 ? '' : ', '}
                    </span>
                    ))
                    : null}{' '}
                </dd>
              </dl>
              <Button tag={Link} to="/entity/project" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />{' '}
                <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
              </Button>
              &nbsp;
              <Button tag={Link} to={`/entity/project/${projectEntity.id}/edit`} replace color="primary">
                <FontAwesomeIcon icon="pencil-alt" />{' '}
                <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
              </Button>
            </Col>
          </Row>
        ):(
          <div className="alert alert-warning">
            <Translate contentKey="noRiskNoFunApp.project.detail.accessDenied"/>
          </div>
        )}

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
            <ProjectRisks riskDiscussionStatus={"tobediscussed"} match={props.match} location={props.location} history={props.history} />
          </TabPane>
          <TabPane tabId={RiskUrlType.PROPOSED}>
            <ProjectRisks riskDiscussionStatus={"proposed"} match={props.match} location={props.location} history={props.history} />
          </TabPane>
        </TabContent>
      </div>
    );
};

const mapStateToProps = ({project, projectActivity}: IRootState) => ({
  projectEntity: project.entity,
  projectActivityEntity: projectActivity.entity
});

const mapDispatchToProps = {getEntity, getProjectActivity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectDetail);
