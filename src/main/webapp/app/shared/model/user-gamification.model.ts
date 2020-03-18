export interface IUserGamification {
  id?: number;
  activityScoreBasedOnPoints?: number;
  userAchievements?: { name: string }[];
  pointsOverTime?: { date: string; pointsScore: number }[];
}

export const defaultValue: Readonly<IUserGamification> = {};
