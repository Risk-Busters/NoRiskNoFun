export interface IProjectActivity {
  projectActivityToday?: number;
  projectActivityOverTime?: { date: string; pointsScore: number }[];
}

export const defaultValue: Readonly<IProjectActivity> = {};
