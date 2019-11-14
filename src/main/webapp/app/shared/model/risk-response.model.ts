import { IRisk } from 'app/shared/model/risk.model';
import { IProjectRisks } from 'app/shared/model/project-risks.model';
import { RiskResponseType } from 'app/shared/model/enumerations/risk-response-type.model';
import { StatusType } from 'app/shared/model/enumerations/status-type.model';

export interface IRiskResponse {
  id?: number;
  type?: RiskResponseType;
  description?: string;
  status?: StatusType;
  risks?: IRisk[];
  projectRisks?: IProjectRisks[];
}

export const defaultValue: Readonly<IRiskResponse> = {};
