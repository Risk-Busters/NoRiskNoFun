import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';
import { IProjectRisks } from 'app/shared/model/project-risks.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: string;
  start?: Moment;
  end?: Moment;
  owner?: IUser;
  projectRisks?: IProjectRisks[];
  users?: IUser[];
}

export const defaultValue: Readonly<IProject> = {};
