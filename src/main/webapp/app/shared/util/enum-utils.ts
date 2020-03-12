export const enumAverage = (enumArray: string[]) => {
  const counts = enumArray.reduce((accumulator, enumType) => {
    accumulator[enumType] = (accumulator[enumType] || 0) + 1;
    return accumulator;
  }, {});
  const countArray: number[] = Object.values(counts);
  const maxCount = Math.max(...countArray);
  const mostFrequent = Object.keys(counts).filter(k => counts[k] === maxCount);

  if (mostFrequent.length === 0) return 'null';
  return mostFrequent[0];
};
