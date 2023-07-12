type Props = {
  className?: string;
  children: React.ReactNode;
};

const CategoryButtonGroup = ({ className, children }: Props) => {
  return <div className={`pointer-events-auto flex gap-2 ${className}`}>{children}</div>;
};

export default CategoryButtonGroup;
