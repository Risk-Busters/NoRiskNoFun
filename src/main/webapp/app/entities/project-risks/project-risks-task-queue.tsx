import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import {useParams} from 'react-router-dom';
import {Translate} from 'react-jhipster';

function ProjectRisksTaskQueue() {

  const { id } = useParams();

  const handleClose = event => {
    event.stopPropagation();
    this.props.history.go(2);
  };

  const handleProposeAnother = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  const handleReviewRisks = event => {
    event.stopPropagation();
    this.props.history.push(`/entities/projects/${id}/toBeDiscussed`);
  };

  return (
    <Modal isOpen toggle={this.handleClose}>
      <ModalHeader toggle={this.handleClose}>
        <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.title" />
      </ModalHeader>
      <ModalBody id="noRiskNoFunApp.projectRisks.taskQueue.description">
        <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.description" />
      </ModalBody>
      <ModalFooter>
        <Button id="noRiskNoFunApp.projectRisks.taskQueue.another" color="secondary" onClick={(e) => handleProposeAnother(e)}>
          <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.another" />
        </Button>
        <Button id="noRiskNoFunApp.projectRisks.taskQueue.reviewDiscussable" color="primary" onClick={(e) => handleReviewRisks(e)}>
          <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.reviewProposed" />
        </Button>
        <Button id="noRiskNoFunApp.projectRisks.taskQueue.reviewDiscussable" color="primary" onClick={(e) => handleReviewRisks(e)}>
          <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.reviewDiscussable" />
        </Button>
      </ModalFooter>
    </Modal>
  );
}


export default ProjectRisksTaskQueue;
