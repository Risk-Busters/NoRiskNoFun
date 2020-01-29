import { AchievementType } from 'app/shared/model/enumerations/achievment-type.model';

export interface IUserGamification {
  id?: number;
  pointsScore?: number;
  userAchievements?: { name: string }[];
}

export const defaultValue: Readonly<IUserGamification> = {};

// TODO: replace string with specific types
