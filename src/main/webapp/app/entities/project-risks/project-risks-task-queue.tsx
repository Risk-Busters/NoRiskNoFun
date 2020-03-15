import React from 'react';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import {useParams, useHistory} from 'react-router-dom';
import {Translate} from 'react-jhipster';

function ProjectRisksTaskQueue() {

  const history = useHistory();
  const { id } = useParams();

  const handleClose = event => {
    event.stopPropagation();
    history.push(`/entity/project/${id}/proposed`);
  };

  const handleProposeAnother = event => {
    event.stopPropagation();
    history.push(`/entity/project/${id}/project-risks/new`);
  };

  const handleReviewRisks = event => {
    event.stopPropagation();
    history.push(`/entity/project/${id}/tobediscussed`);
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.title" />
      </ModalHeader>
      <ModalBody id="noRiskNoFunApp.projectRisks.taskQueue.description">
        <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.description" />
      </ModalBody>
      <ModalFooter>
        <Button id="noRiskNoFunApp.projectRisks.taskQueue.another" color="primary" onClick={(e) => handleProposeAnother(e)}>
          <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.another" />
        </Button>
        <Button id="noRiskNoFunApp.projectRisks.taskQueue.reviewProposed" color="secondary" onClick={(e) => handleClose(e)}>
          <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.reviewProposed" />
        </Button>
        <Button id="noRiskNoFunApp.projectRisks.taskQueue.reviewDiscussable" color="secondary" onClick={(e) => handleReviewRisks(e)}>
          <Translate contentKey="noRiskNoFunApp.projectRisks.taskQueue.reviewDiscussable" />
        </Button>
      </ModalFooter>
    </Modal>
  );
}


export default ProjectRisksTaskQueue;
