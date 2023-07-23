//https://medium.com/@didewinuwara/disable-zoom-in-and-zoom-out-for-reactjs-bf9d47a5a661

import { useEffect } from 'react';

function usePreventZoom(scrollCheck = true, keyboardCheck = true) {
  const handleKeydown = (e: any) => {
    if (keyboardCheck && e.ctrlKey && (e.key == '=' || e.key == '+' || e.key == '-')) {
      e.preventDefault();
    }
  };

  const handleWheel = (e: any) => {
    if (scrollCheck && e.ctrlKey) {
      e.preventDefault();
    }
  };

  useEffect(() => {
    document.addEventListener('keydown', handleKeydown);
    document.addEventListener('wheel', handleWheel, { passive: false });

    return () => {
      document.removeEventListener('keydown', handleKeydown);
      document.removeEventListener('wheel', handleWheel);
    };
  }, [scrollCheck, keyboardCheck]);
}

export default usePreventZoom;
