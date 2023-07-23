type Props = {
  imgSrc: string;
};

const RecordImage = ({ imgSrc }: Props) => {
  return (
    <div className="h-[300px]">
      <img src={imgSrc} alt="" className="h-full  w-full rounded-t-lg object-cover" />
    </div>
  );
};

export default RecordImage;
