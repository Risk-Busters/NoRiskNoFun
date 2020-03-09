import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps, useParams} from 'react-router-dom';
import {Button, Table} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntity} from '../project-risks/project-risks.reducer';

export interface IRiskResponseProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

function RiskResponse (props: IRiskResponseProps) {

  const {riskId} = useParams();

  useEffect(() => {
    props.getEntity(riskId);
  }, []);

    const { riskResponseList, match } = props;
    return (
      <div>
        <h2 id="risk-response-heading">
          <Translate contentKey="noRiskNoFunApp.riskResponse.home.title">Risk Responses</Translate>
          <Link to={`${match.url}/response/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.riskResponse.home.createLabel">Create a new Risk Response</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {riskResponseList && riskResponseList.length > 0 ? (
            <Table responsive aria-describedby="risk-response-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.riskResponse.type">Type</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.riskResponse.description">Description</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.riskResponse.status">Status</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {riskResponseList.map((riskResponse, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Translate contentKey={`noRiskNoFunApp.RiskResponseType.${riskResponse.type}`} />
                    </td>
                    <td>{riskResponse.description}</td>
                    <td>
                      <Translate contentKey={`noRiskNoFunApp.StatusType.${riskResponse.status}`} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/response/${riskResponse.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/response/${riskResponse.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/response/${riskResponse.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="noRiskNoFunApp.riskResponse.home.notFound">No Risk Responses found</Translate>
            </div>
          )}
        </div>
      </div>
    );
}

const mapStateToProps = ({ projectRisks }: IRootState) => ({
  riskResponseList: projectRisks.entity.riskResponses
});

const mapDispatchToProps = {
  getEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskResponse);
