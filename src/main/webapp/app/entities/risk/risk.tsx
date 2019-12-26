import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Table} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntities} from './risk.reducer';

export interface IRiskProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export class Risk extends React.Component<IRiskProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const {riskList, match} = this.props;
    return (
      <div>
        <h2 id="risk-heading">
          <Translate contentKey="noRiskNoFunApp.risk.home.title"/>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus"/>
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.risk.home.createLabel"/>
          </Link>
        </h2>
        <div className="table-responsive">
          {riskList && riskList.length > 0 ? (
            <Table responsive aria-describedby="risk-heading">
              <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id"/>
                </th>
                <th>
                  <Translate contentKey="noRiskNoFunApp.risk.name"/>
                </th>
                <th>
                  <Translate contentKey="noRiskNoFunApp.risk.description"/>
                </th>
                <th>
                  <Translate contentKey="noRiskNoFunApp.risk.severity"/>
                </th>
                <th>
                  <Translate contentKey="noRiskNoFunApp.risk.probability"/>
                </th>
                <th>
                  <Translate contentKey="noRiskNoFunApp.risk.inRiskpool"/>
                </th>
                <th>
                  <Translate contentKey="noRiskNoFunApp.risk.riskResponse"/>
                </th>
                <th/>
              </tr>
              </thead>
              <tbody>
              {riskList.map((risk, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${risk.id}`} color="link" size="sm">
                      {risk.id}
                    </Button>
                  </td>
                  <td>{risk.name}</td>
                  <td>{risk.description}</td>
                  <td>
                    {risk.severity == null ? (
                      <Translate contentKey={`noRiskNoFunApp.risk.notSet`}/>
                    ) : (<Translate contentKey={`noRiskNoFunApp.SeverityType.${risk.severity}`}/>)}
                  </td>
                  <td>
                    {risk.probability == null ? (
                      <Translate contentKey={`noRiskNoFunApp.risk.notSet`}/>
                    ) : (<Translate contentKey={`noRiskNoFunApp.ProbabilityType.${risk.probability}`}/>)}
                  </td>
                  <td>{risk.inRiskpool ? 'true' : 'false'}</td>
                  <td>
                    {risk.riskResponses.length !== 0
                      ? risk.riskResponses.map((val, j) => (
                        <span key={j}>
                              <Link to={`risk-response/${val.id}`}>{val.id}</Link>
                          {j === risk.riskResponses.length - 1 ? '' : ', '}
                            </span>
                      ))
                      : <Translate contentKey={`noRiskNoFunApp.risk.notSet`}/>}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${risk.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye"/>{' '}
                        <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view"/>
                          </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${risk.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt"/>{' '}
                        <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit"/>
                          </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${risk.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash"/>{' '}
                        <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete"/>
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
              <Translate contentKey="noRiskNoFunApp.risk.home.notFound"/>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({risk}: IRootState) => ({
  riskList: risk.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Risk);
