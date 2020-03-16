export interface IProjectActivity {
  projectActivityBasedOnUserScore?: number;
  projectActivitiesOverTime?: { date: string; pointsScore: number }[];
}

export const defaultValue: Readonly<IProjectActivity> = {};
