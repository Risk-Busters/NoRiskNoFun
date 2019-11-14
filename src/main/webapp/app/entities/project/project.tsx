import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project.reducer';
import { IProject } from 'app/shared/model/project.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Project extends React.Component<IProjectProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { projectList, match } = this.props;
    return (
      <div>
        <h2 id="project-heading">
          <Translate contentKey="noRiskNoFunApp.project.home.title">Projects</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.project.home.createLabel">Create a new Project</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {projectList && projectList.length > 0 ? (
            <Table responsive aria-describedby="project-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.project.name">Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.project.description">Description</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.project.start">Start</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.project.end">End</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.project.owner">Owner</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.project.user">User</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {projectList.map((project, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${project.id}`} color="link" size="sm">
                        {project.id}
                      </Button>
                    </td>
                    <td>{project.name}</td>
                    <td>{project.description}</td>
                    <td>
                      <TextFormat type="date" value={project.start} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={project.end} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{project.owner ? project.owner.id : ''}</td>
                    <td>
                      {project.users
                        ? project.users.map((val, j) => (
                            <span key={j}>
                              {val.id}
                              {j === project.users.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${project.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${project.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${project.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="noRiskNoFunApp.project.home.notFound">No Projects found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ project }: IRootState) => ({
  projectList: project.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Project);
