import update from 'immutability-helper';
import { useCallback, useEffect, useState } from 'react';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { useDispatch } from 'react-redux';

import { EditmodeContent } from '@/components/schedule/accordion-content';
import { editSchedule } from '@/redux/slices/scheduleSlice';
import { ScheduledPlaceBase } from '@/types/api/schedules-types';

type DndContents = {
  contents: ScheduledPlaceBase[];
  dayNumber: number;
};

const DndContents = ({ contents, dayNumber }: DndContents) => {
  const [isEditActionEnd, setIsEditActionEnd] = useState(false);
  const [placelist, setPlaceList] = useState(contents);

  const dispatch = useDispatch();

  useEffect(() => {
    setPlaceList(contents);
  }, [contents]);

  useEffect(() => {
    if (isEditActionEnd) {
      dispatch(editSchedule({ dayNumber, schedule: placelist }));
      setIsEditActionEnd(false);
    }
  }, [isEditActionEnd]);

  const removeItem = (visitNumber: number) => {
    setPlaceList((prevList) => prevList.filter((_, idx) => visitNumber !== idx));
    setIsEditActionEnd(true);
  };

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
        setIsEditActionEnd={setIsEditActionEnd}
        removeItem={removeItem}
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
