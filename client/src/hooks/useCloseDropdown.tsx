import { ButtonHTMLAttributes, Dispatch, SetStateAction, useEffect, useState } from 'react';

type Props = (
  el: React.RefObject<HTMLDivElement>,
  initialState: boolean
) => [boolean, Dispatch<SetStateAction<boolean>>];

export const useCloseDropdown: Props = (el, initialState) => {
  const [isOpen, setIsOpen] = useState(initialState);

  useEffect(() => {
    const onClickEvent = ({ target }: MouseEvent): void => {
      if (el.current !== null && !el.current.contains(target as Node)) {
        setIsOpen(!isOpen);
      }
    };

    if (isOpen) {
      window.addEventListener('click', onClickEvent);
    }

    return () => {
      window.removeEventListener('click', onClickEvent);
    };
  }, [isOpen, el]);

  return [isOpen, setIsOpen];
};
