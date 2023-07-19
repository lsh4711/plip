import * as Accordion from '@radix-ui/react-accordion';
import { MdRemoveCircleOutline } from '@react-icons/all-files/md/MdRemoveCircleOutline';
import { VscMenu } from '@react-icons/all-files/vsc/VscMenu';
import type { Identifier, XYCoord } from 'dnd-core';
import { Dispatch, SetStateAction, useRef } from 'react';
import { useDrag, useDrop } from 'react-dnd';

type EditmodeContent = {
  id: number;
  name: string;
  visitNumber: number;
  dayNumber: number;
  moveItem: (dragIndex: number, hoverIndex: number) => void;
  setIsEditActionEnd: Dispatch<SetStateAction<boolean>>;
  removeItem: (visitNumber: number) => void;
};

type DragContent = {
  visitNumber: number;
  id: string;
  type: string;
};

const EditmodeContent = ({
  id,
  name,
  visitNumber,
  dayNumber,
  moveItem,
  setIsEditActionEnd,
  removeItem,
}: EditmodeContent) => {
  const ref = useRef<any>(null);
  const [{ handlerId }, drop] = useDrop<DragContent, void, { handlerId: Identifier | null }>({
    accept: `content-${dayNumber}`,
    collect(monitor) {
      return {
        handlerId: monitor.getHandlerId(),
      };
    },
    hover(item: DragContent, monitor) {
      if (!ref.current) {
        return;
      }
      const dragIndex = item.visitNumber;
      const hoverIndex = visitNumber;

      if (dragIndex === hoverIndex) {
        return;
      }

      const hoverBoundingRect = ref.current?.getBoundingClientRect();
      const hoverMiddleY = (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;
      const clientOffset = monitor.getClientOffset();
      const hoverClientY = (clientOffset as XYCoord).y - hoverBoundingRect.top;

      if (dragIndex < hoverIndex && hoverClientY < hoverMiddleY) {
        return;
      }

      if (dragIndex > hoverIndex && hoverClientY > hoverMiddleY) {
        return;
      }

      moveItem(dragIndex, hoverIndex);

      item.visitNumber = hoverIndex;
    },
  });

  const [{ isDragging }, drag] = useDrag({
    type: `content-${dayNumber}`,
    item: () => {
      return { id, visitNumber };
    },
    collect: (monitor: any) => ({
      isDragging: monitor.isDragging(),
    }),
    end: () => {
      setIsEditActionEnd(true);
    },
  });

  const opacity = isDragging ? 0 : 1;
  drag(drop(ref));

  return (
    <Accordion.Content
      key={id} // react key
      className="AccordionContent cursor-move select-none overflow-hidden border-t-[1px]"
      ref={ref}
      style={{ opacity }}
      data-handler-id={handlerId}
    >
      <div className="relative flex items-center justify-between px-4 py-3">
        <button className="absolute -translate-x-1">
          <MdRemoveCircleOutline color="#FF3535" onClick={() => removeItem(visitNumber)} />
        </button>
        <div className="mx-4 flex text-xs 2xl:text-sm">{name}</div>
        <VscMenu color="#bbb" className="w-3 2xl:w-4" />
      </div>
    </Accordion.Content>
  );
};

export default EditmodeContent;
