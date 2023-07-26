const getNextDate = (date: Date): Date => {
  date?.setDate(date.getDate() + 1);

  return date;
};

export default getNextDate;
