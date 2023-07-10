import { UseSelector, useSelector } from 'react-redux/es/hooks/useSelector';
import React from 'react';
import { RootState } from '@/redux/store';

const useTypeSelector = () => {
  const selector = useSelector((state: RootState) => state);
  return selector;
};

export default useTypeSelector;
