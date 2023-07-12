import React from 'react';
import { RootState } from '@/redux/store';
import { createPortal } from 'react-dom';
import { ToastProp } from './ToastItem';
import { useDispatch, useSelector } from 'react-redux';
import ToastItem from './ToastItem';

interface ToastContainerProps {}

const ToastContainer = ({}: ToastContainerProps) => {
  const toasts = useSelector((state: RootState) => state.toast);
  const toastElement = document.getElementById('toast') as HTMLElement;
  return (
    <>
      {createPortal(
        toasts.map((item) => (
          <ToastItem key={item.id} type={item.type}>
            {item.content}
          </ToastItem>
        )),
        toastElement
      )}
    </>
  );
};

export default ToastContainer;
