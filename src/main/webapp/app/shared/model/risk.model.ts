import { IRiskResponse } from 'app/shared/model/risk-response.model';
import { SeverityType } from 'app/shared/model/enumerations/severity-type.model';
import { ProbabilityType } from 'app/shared/model/enumerations/probability-type.model';

export interface IRisk {
  id?: number;
  name?: string;
  description?: string;
  severity?: SeverityType;
  probability?: ProbabilityType;
  inRiskpool?: boolean;
  riskResponses?: IRiskResponse[];
}

export const defaultValue: Readonly<IRisk> = {
  inRiskpool: false
};
