import React, {useEffect, useState} from 'react';
import {getUser} from '../administration/user-management/user-management.reducer';
import {IRootState} from 'app/shared/reducers';
import {RouteComponentProps} from 'react-router-dom';
import {connect} from 'react-redux';
import {Spinner} from 'reactstrap';
import {getUserGamification} from "app/entities/user-gamification/user-gamification.reducer";
import {Chart} from "react-google-charts";
import {IUserGamification} from "app/shared/model/user-gamification.model";
import {translate} from "react-jhipster";

export interface IUserActivityGraphProps extends StateProps, DispatchProps, IUserGamification, RouteComponentProps<{ login?: string }> {
}

export const UserActivityGraph = (props: IUserActivityGraphProps) => {

  const [diagramStatus, setDiagramStatus] = useState([]);

  const getUserLogin = (): string => {
    if (props.match.params.login) {
      return props.match.params.login;
    }
    props.history.push(`${props.match.url}/${props.currentLogin}/`);
    return props.currentLogin;
  };

  useEffect(() => {
    props.getUser(getUserLogin());
  }, []);

  useEffect(() => {
    props.getUserGamification(props.user.id);
  }, []);

  const {user} = props;
  const {userGamificationEntitiy} = props;

  useEffect(() => {
    if (userGamificationEntitiy.pointsOverTime !== undefined) {
      const finalFormatForDiagram = userGamificationEntitiy.pointsOverTime.map(Object.values);
      finalFormatForDiagram.unshift(['Date', 'Your Activity']);
      setDiagramStatus(finalFormatForDiagram);
    }
  }, [userGamificationEntitiy.pointsOverTime]);

  const getProfileName = (): string => {
    return user.firstName && user.lastName ? `${user.firstName} ${user.lastName} (${user.login})` : user.login;
  };

  return (
    <div>
      <h4>Your Activity</h4>
      <Chart
        width={'500px'}
        height={'300px'}
        chartType="AreaChart"
        loader={<Spinner color="primary"/>}
        data={diagramStatus}
        options={{
          title: translate("noRiskNoFunApp.userGamification.diagrams.userdiagramTitle"),
          hAxis: {
            title: translate("noRiskNoFunApp.userGamification.diagrams.time"),
            titleTextStyle: {color: '#333'}
          },
          vAxis: {
            title: translate("noRiskNoFunApp.userGamification.diagrams.activity"), minValue: 0
          },
          // For the legend to fit, we make the chart area smaller
          chartArea: {width: '50%', height: '70%'},
          // lineWidth: 25
        }}
        // For tests
        rootProps={{'data-testid': '1'}}
      />
    </div>
  );
};

const mapStateToProps = (storeState: IRootState, {userGamification}: IRootState) => ({
  user: storeState.userManagement.user,
  currentLogin: storeState.authentication.account.login,
  userGamificationEntitiy: storeState.userGamification.entity
});

const mapDispatchToProps = {
  getUser,
  getUserGamification
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserActivityGraph);
