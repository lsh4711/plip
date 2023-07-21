type Props = {
  className?: string;
  children: React.ReactNode;
};

const CategoryButtonGroup = ({ className, children }: Props) => {
  return <div className={`pointer-events-auto hidden gap-2 lg:flex ${className}`}>{children}</div>;
};

export default CategoryButtonGroup;
