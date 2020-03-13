import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import {getEntity as getProjectRisk} from '../project-risks/project-risks.reducer';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './risk-response.reducer';

export interface IRiskResponseDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ responseId: string; riskId: string }> {}

export class RiskResponseDeleteDialog extends React.Component<IRiskResponseDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.responseId);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.riskResponseEntity.id);
    this.props.getProjectRisk(this.props.match.params.riskId);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { riskResponseEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="noRiskNoFunApp.riskResponse.delete.question">
          <Translate contentKey="noRiskNoFunApp.riskResponse.delete.question" interpolate={{ id: riskResponseEntity.id }}>
            Are you sure you want to delete this RiskResponse?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-riskResponse" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ riskResponse }: IRootState) => ({
  riskResponseEntity: riskResponse.entity
});

const mapDispatchToProps = { getEntity, deleteEntity, getProjectRisk };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskResponseDeleteDialog);
