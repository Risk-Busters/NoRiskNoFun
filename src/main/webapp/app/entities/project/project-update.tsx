import React from 'react';
import './project-update.scss';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvCheckbox, AvCheckboxGroup, AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './project.reducer';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProjectUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProjectUpdateState {
  isNew: boolean;
  idsuser: any[];
  ownerId: string;
}

class ProjectUpdate extends React.Component<IProjectUpdateProps, IProjectUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsuser: [],
      ownerId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id,
    };
  }


  UNSAFE_componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { projectEntity } = this.props;
      const entity = {
        ...projectEntity,
        ...values,
        users: mapIdList(values.users)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };



  handleClose = () => {
    this.props.history.goBack();
  };


  render() {
    const { projectEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="noRiskNoFunApp.project.home.createOrEditLabel">
              <Translate contentKey="noRiskNoFunApp.project.home.createOrEditLabel">Create or edit a Project</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : projectEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="project-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="project-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="project-name">
                    <Translate contentKey="noRiskNoFunApp.project.name">Name</Translate>
                  </Label>
                  <AvField
                    id="project-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="project-description">
                    <Translate contentKey="noRiskNoFunApp.project.description">Description</Translate>
                  </Label>
                  <AvField id="project-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="startLabel" for="project-start">
                    <Translate contentKey="noRiskNoFunApp.project.start">Start</Translate>
                  </Label>
                  <AvField
                    id="project-start"
                    type="date"
                    className="form-control"
                    name="start"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="endLabel" for="project-end">
                    <Translate contentKey="noRiskNoFunApp.project.end">End</Translate>
                  </Label>
                  <AvField
                    id="project-end"
                    type="date"
                    className="form-control"
                    name="end"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="project-owner">
                    <Translate contentKey="noRiskNoFunApp.project.owner">Owner</Translate>
                  </Label>
                  <AvInput id="project-owner" type="select" className="form-control" name="owner.id">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.firstName && otherEntity.lastName ? `${otherEntity.firstName} ${otherEntity.lastName}` : otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="project-user">
                    <Translate contentKey="noRiskNoFunApp.project.user"/>
                  </Label><br/>
                </AvGroup>
                <AvGroup>
                  <div className="members">
                   <AvCheckboxGroup name="users" value={projectEntity.users && projectEntity.users.map(e => e.id)}>
                        {users
                          ? users.map(otherEntity => (
                            <AvCheckbox key={otherEntity.id} label={otherEntity.firstName && otherEntity.lastName ? `${otherEntity.firstName} ${otherEntity.lastName}` : otherEntity.login} value={otherEntity.id}/>
                          ))
                          : null}
                      </AvCheckboxGroup>
                  </div>
                </AvGroup>
                <Button id="cancel-save" onClick={() => this.handleClose()} replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  projectEntity: storeState.project.entity,
  loading: storeState.project.loading,
  updating: storeState.project.updating,
  updateSuccess: storeState.project.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectUpdate);
