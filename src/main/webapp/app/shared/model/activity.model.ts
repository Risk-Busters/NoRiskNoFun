import { Moment } from 'moment';
import { IUser } from 'app/shared/model/user.model';

export interface IActivity {
  id?: number;
  activityDescriptionKey?: string;
  targetUrl?: string;
  date?: Moment;
  users?: IUser[];
}

export const defaultValue: Readonly<IActivity> = {};
