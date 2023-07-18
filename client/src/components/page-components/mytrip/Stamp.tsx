import StampImg from '@/assets/imgs/stamp.png';

const Stamp = () => {
  return (
    <div>
      <img
        src={StampImg}
        className="absolute left-1/2 top-1/2 translate-x-[-50%] translate-y-[-50%]"
      />
    </div>
  );
};

export default Stamp;
