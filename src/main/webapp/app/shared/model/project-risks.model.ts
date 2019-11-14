import { IRiskResponse } from 'app/shared/model/risk-response.model';
import { IProject } from 'app/shared/model/project.model';
import { SeverityType } from 'app/shared/model/enumerations/severity-type.model';
import { ProbabilityType } from 'app/shared/model/enumerations/probability-type.model';

export interface IProjectRisks {
  id?: number;
  projectSeverity?: SeverityType;
  projectProbability?: ProbabilityType;
  hasOccured?: boolean;
  riskResponses?: IRiskResponse[];
  project?: IProject;
}

export const defaultValue: Readonly<IProjectRisks> = {
  hasOccured: false
};
