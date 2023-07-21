import DefaultProfile from '../../assets/icons/profile.svg';

interface AvatarProps {
  size?: number;
  imgSrc?: string;
}

const Avatar = ({ size = 32, imgSrc }: AvatarProps) => {
  return (
    <div className={`rounded-full`}>
      <img
        src={imgSrc || DefaultProfile}
        className="object-cover"
        alt="user avatar"
        width={size}
        height={size}
      />
    </div>
  );
};

export default Avatar;
