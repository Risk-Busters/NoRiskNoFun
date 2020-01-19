export interface IUserGamification {
  id?: number;
  pointsScore?: number;
  userLogin?: string;
  userId?: number;
  // TODO: mega pfusch hier
  achievements?: string[];
}

export const defaultValue: Readonly<IUserGamification> = {};
