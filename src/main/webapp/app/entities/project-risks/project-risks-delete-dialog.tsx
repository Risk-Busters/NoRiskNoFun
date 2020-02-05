import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IProjectRisks } from 'app/shared/model/project-risks.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './project-risks.reducer';

export interface IProjectRisksDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ riskId: string }> {}

export class ProjectRisksDeleteDialog extends React.Component<IProjectRisksDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.riskId);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.projectRisksEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { projectRisksEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="noRiskNoFunApp.projectRisks.delete.question">
          <Translate contentKey="noRiskNoFunApp.projectRisks.delete.question" interpolate={{ id: projectRisksEntity.id }}>
            Are you sure you want to delete this ProjectRisks?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-projectRisks" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ projectRisks }: IRootState) => ({
  projectRisksEntity: projectRisks.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisksDeleteDialog);
