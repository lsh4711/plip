const syncDate = (date: Date): Date => {
  date?.setDate(date.getDate() + 1);

  return date;
};

export default syncDate;
