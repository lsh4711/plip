import React from 'react';

interface CenteringWrapperProps {
  children: React.ReactNode;
}

const CenteringWrapper = ({ children }: CenteringWrapperProps) => {
  return (
    <main className="flex flex-col items-center justify-center overflow-x-hidden py-24">
      {children}
    </main>
  );
};

export default CenteringWrapper;
