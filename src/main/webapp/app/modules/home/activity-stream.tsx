import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Jumbotron, Col, Row } from 'reactstrap';
import { Translate, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from '../../entities/activity/activity.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IActivityProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IActivityState = IPaginationBaseState;

export class ActivityStream extends React.Component<IActivityProps, IActivityState> {
  state: IActivityState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { activityList, match } = this.props;
    return (
      <div>
        <h2 id="activity-heading">
          <Translate contentKey="noRiskNoFunApp.activity.home.title">Latest Activities</Translate>
          <p className="lead"><Translate contentKey="noRiskNoFunApp.activity.home.text">What is happening right now?</Translate></p>
        </h2>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {activityList && activityList.length > 0 ?
                activityList.map((activity, i) => (
                  <Jumbotron key={`entity-${activity.id}`} style={{padding: '10px', marginBottom: '10px'}}>
                    <p className="float-md-right"><TextFormat type="date" value={activity.date} format={APP_LOCAL_DATE_FORMAT} /></p>
                    <h4 className="lead">{activity.activityDescriptionKey}</h4>
                    <p><Translate contentKey="noRiskNoFunApp.activity.home.placeholderText">You want to know what this is all about?</Translate></p>
                    <Button tag={Link} to={activity.targetUrl} color="primary"><Translate contentKey="noRiskNoFunApp.activity.home.targetButton">No Activities found</Translate></Button>
                  </Jumbotron>
                  ))
             : (
              <div className="alert alert-warning">
                <Translate contentKey="noRiskNoFunApp.activity.home.notFound">No Activities found</Translate>
              </div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ activity }: IRootState) => ({
  activityList: activity.entities,
  totalItems: activity.totalItems,
  links: activity.links,
  entity: activity.entity,
  updateSuccess: activity.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ActivityStream);
