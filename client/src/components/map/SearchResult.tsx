import { MdClose } from '@react-icons/all-files/md/MdClose';

import { GetPlaceByKeywordResponse } from '@/types/mapApi/place-types';

type ResultInfoProps = {
  children: React.ReactNode;
};

const ResultInfo = ({ children }: ResultInfoProps) => {
  return (
    <div className="item fixed flex w-full items-center justify-between gap-1 px-4 py-2 text-xs text-[#343539]">
      {children}
    </div>
  );
};

interface ResultItemProps {
  order: number;
  placeName: string;
  address: string;
  category: string;
  phone: string;
}

const ResultItem = ({ order, placeName, address, category, phone }: ResultItemProps) => {
  return (
    <div className="flex flex-col gap-1 border-t-[1px] border-solid px-4 py-2">
      <div className="flex items-center gap-2">
        <span className="font-extrabold text-[#4568DC]">{order + 1}</span>
        <span className="font-bold">{placeName}</span>
        <span className="text-xs text-[#bbb]">{category || ''}</span>
      </div>
      <span className="text-xs">{address}</span>
      <span className={`text-xs ${phone ? 'text-[#343539]' : 'text-[#bbb]'}`}>
        {phone ? phone : '전화번호 미등록'}
      </span>
    </div>
  );
};

type SearchResultProps = {
  keyword: string;
  results:
    | Pick<
        GetPlaceByKeywordResponse,
        'place_name' | 'road_address_name' | 'phone' | 'id' | 'category_name'
      >[]
    | null;
  onResetSearch: () => void;
};

const SearchResult = ({ keyword, results, onResetSearch }: SearchResultProps) => {
  return (
    <>
      {results && (
        <div className="pointer-events-auto left-0 top-12 min-h-fit w-80 overflow-y-scroll rounded-lg border-[1px] border-solid border-[#bbb] bg-white drop-shadow-lg">
          <ResultInfo>
            <div>
              <span className="mr-1 inline-block max-w-[8rem] overflow-hidden text-ellipsis align-top font-bold text-[#4568DC]">
                {keyword}
              </span>
              검색 결과
              <span> {results.length}</span>건
            </div>
            <MdClose size={14} color="#bbb" onClick={onResetSearch} className="cursor-pointer" />
          </ResultInfo>
          <div className="mt-8 h-fit">
            {results.length ? (
              results.map(({ id, place_name, road_address_name, category_name, phone }, idx) => (
                <ResultItem
                  key={id}
                  order={idx}
                  placeName={place_name}
                  address={road_address_name}
                  category={category_name}
                  phone={phone}
                />
              ))
            ) : (
              <div className="flex items-center justify-center border-t-[1px] border-solid border-[#bbb] p-4 text-sm text-[#bbb]">
                검색 결과가 없습니다.
              </div>
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default SearchResult;
