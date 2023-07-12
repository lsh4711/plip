import React from 'react';

interface WrapperProps {
  children: React.ReactNode;
}

const Wrapper = ({ children }: WrapperProps) => {
  return (
    <div className="flex flex-col items-center justify-center overflow-x-hidden">{children}</div>
  );
};

export default Wrapper;
