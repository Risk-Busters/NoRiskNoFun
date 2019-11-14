import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './risk-response.reducer';
import { IRiskResponse } from 'app/shared/model/risk-response.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRiskResponseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RiskResponse extends React.Component<IRiskResponseProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { riskResponseList, match } = this.props;
    return (
      <div>
        <h2 id="risk-response-heading">
          <Translate contentKey="noRiskNoFunApp.riskResponse.home.title">Risk Responses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
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
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
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
                      <Button tag={Link} to={`${match.url}/${riskResponse.id}`} color="link" size="sm">
                        {riskResponse.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`noRiskNoFunApp.RiskResponseType.${riskResponse.type}`} />
                    </td>
                    <td>{riskResponse.description}</td>
                    <td>
                      <Translate contentKey={`noRiskNoFunApp.StatusType.${riskResponse.status}`} />
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${riskResponse.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${riskResponse.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${riskResponse.id}/delete`} color="danger" size="sm">
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
}

const mapStateToProps = ({ riskResponse }: IRootState) => ({
  riskResponseList: riskResponse.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskResponse);
