const parsePlanId = (location: string) => {
  const arr = location.split('/');
  return arr[arr.length - 1];
};

export default parsePlanId;
