import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './activity.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IActivityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ActivityDetail extends React.Component<IActivityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { activityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.activity.detail.title">Activity</Translate> [<b>{activityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="description">
                <Translate contentKey="noRiskNoFunApp.activity.description">Description</Translate>
              </span>
            </dt>
            <dd>{activityEntity.description}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="noRiskNoFunApp.activity.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={activityEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/activity" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ activity }: IRootState) => ({
  activityEntity: activity.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActivityDetail);
