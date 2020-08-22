import React, {useEffect, useState} from 'react';
import {getUser} from '../administration/user-management/user-management.reducer';
import {IRootState} from 'app/shared/reducers';
import {RouteComponentProps} from 'react-router-dom';
import {connect} from 'react-redux';
import {Spinner} from 'reactstrap';
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
    if (props.userGamificationEntitiy.pointsOverTime !== undefined) {
      const finalFormatForDiagram = props.userGamificationEntitiy.pointsOverTime.map(Object.values);
      finalFormatForDiagram.unshift(['Date', 'Your Activity']);
      setDiagramStatus(finalFormatForDiagram);
    }
  }, [props.userGamificationEntitiy.pointsOverTime]);

  return (
    <div>
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

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
  currentLogin: storeState.authentication.account.login,
  userGamificationEntitiy: storeState.userGamification.entity
});

const mapDispatchToProps = {
  getUser
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserActivityGraph);
