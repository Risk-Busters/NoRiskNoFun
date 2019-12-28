import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {TextFormat, Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './project.reducer';
import {APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IProjectDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export class ProjectDetail extends React.Component<IProjectDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
    console.log("MATCH in project detail");
    console.log(this.props.match);
    console.log("ID des Projektes (muss durchgegeben werden an project-risks");
    console.log(this.props.match.params.id);
  }

  render() {
    const {projectEntity} = this.props;
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

      </div>
    );
  }
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
