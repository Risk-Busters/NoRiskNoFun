export interface IUserGamification {
  id?: number;
  pointsScore?: number;
  userAchievements?: { name: string }[];
  pointsOverTime?: { date: string; pointsScore: number }[];
}

export const defaultValue: Readonly<IUserGamification> = {};
