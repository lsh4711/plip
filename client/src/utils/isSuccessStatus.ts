const isSuccessStatus = (response: Response) => {
  const isSuccess = response.status.toString().split('')[0];
  return isSuccess === '2' ? true : false;
};

export default isSuccessStatus;
