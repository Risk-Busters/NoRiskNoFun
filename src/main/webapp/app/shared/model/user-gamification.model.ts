import { AchievmentType } from 'app/shared/model/enumerations/achievment-type.model';

export interface IUserGamification {
  id?: number;
  userId?: number;
  pointsScore?: number;
  achievements?: AchievmentType[];
}

export const defaultValue: Readonly<IUserGamification> = {};
