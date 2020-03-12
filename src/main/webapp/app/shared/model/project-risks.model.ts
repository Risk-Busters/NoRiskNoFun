import { IRiskResponse } from 'app/shared/model/risk-response.model';
import { IProject } from 'app/shared/model/project.model';
import { IRisk } from 'app/shared/model/risk.model';
import { SeverityType } from 'app/shared/model/enumerations/severity-type.model';
import { ProbabilityType } from 'app/shared/model/enumerations/probability-type.model';
import { IUser } from 'app/shared/model/user.model';

export interface IProjectRisks {
  id?: number;
  discussions?: Set<IRiskDiscussion>;
  hasOccured?: boolean;
  riskResponses?: IRiskResponse[];
  project?: IProject;
  risk?: IRisk;
  riskDiscussionStatus?: 'proposed' | 'toBeDiscussed' | 'final';
  likes?: number;
  personInCharge?: IUser;
}

export interface IRiskDiscussionVM {
  projectRiskId: number;
  projectSeverity?: SeverityType;
  projectProbability?: ProbabilityType;
}

export interface IRiskDiscussion {
  user: IUser;
  projectSeverity: any;
  projectProbability: any;
}

export const defaultValue: Readonly<IProjectRisks> = {
  hasOccured: false
};
