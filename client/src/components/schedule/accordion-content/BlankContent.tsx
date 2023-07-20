import * as Accordion from '@radix-ui/react-accordion';

const BlankContent = () => {
  return (
    <Accordion.Content className="AccordionContent overflow-hidden border-t-[1px]">
      <div className="flex items-center justify-center px-4 py-3 text-xs text-[#bbb]">
        추가된 일정이 없습니다.
      </div>
    </Accordion.Content>
  );
};

export default BlankContent;
