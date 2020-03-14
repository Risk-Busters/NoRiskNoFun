export interface IProjectActivity {
  projectActivity?: number;
  projectActivityOverTime?: { date: string; pointsScore: number }[];
}

export const defaultValue: Readonly<IProjectActivity> = {};
