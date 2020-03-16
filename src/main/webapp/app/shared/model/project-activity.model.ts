export interface IProjectActivity {
  projectActivityBasedOnUserScore?: number;
  projectActivityOverTime?: { date: string; pointsScore: number }[];
}

export const defaultValue: Readonly<IProjectActivity> = {};
