import update from 'immutability-helper';
import { useCallback, useState } from 'react';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

import { EditmodeContent } from '@/components/schedule/accordion-content';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';

type DndContents = {
  contents: ScheduledPlaceBase[];
  dayNumber: number;
};

const DndContents = ({ contents, dayNumber }: DndContents) => {
  const [placelist, setPlaceList] = useState(contents);
  console.log(placelist);

  const moveItem = useCallback((dragIndex: number, hoverIndex: number) => {
    setPlaceList((prevItems) =>
      update(prevItems, {
        $splice: [
          [dragIndex, 1],
          [hoverIndex, 0, prevItems[dragIndex]],
        ],
      })
    );
  }, []);

  const renderItem = useCallback(
    (name: string, apiId: number, visitNumber: number) => (
      <EditmodeContent
        key={apiId} // react key
        id={apiId} // TODO Check 같은 장소 여러개인 경우 키 겹침
        visitNumber={visitNumber}
        name={name}
        dayNumber={dayNumber}
        moveItem={moveItem}
      />
    ),
    []
  );
  return (
    <DndProvider backend={HTML5Backend}>
      <div>
        {placelist.map(({ name, apiId }, visitNumber) => renderItem(name, apiId, visitNumber))}
      </div>
    </DndProvider>
  );
};

export default DndContents;
