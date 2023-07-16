import {
  SearchPlaceByCategory,
  SearchPlaceByKeyword,
  default as useSearchPlaceQuery,
} from '@/queries/search/useKeywordSearchQuery';
import { MdClose } from '@react-icons/all-files/md/MdClose';
import { useState } from 'react';

type ResultInfoProps = {
  children: React.ReactNode;
};

const ResultInfo = ({ children }: ResultInfoProps) => {
  return (
    <div className="item sticky left-0 top-0 flex w-full items-center justify-between gap-1 border-b-[1px] bg-white px-4 py-2 text-xs text-[#343539]">
      {children}
    </div>
  );
};

type ResultItemProps = {
  order: number;
  placeName: string;
  address: string;
  category: string;
  phone: string;
};

const ResultItem = ({ order, placeName, address, category, phone }: ResultItemProps) => {
  return (
    <div className="flex flex-col gap-1 border-t-[1px] border-solid px-4 py-2">
      <div className="flex items-center gap-2">
        <span className="font-extrabold text-[#4568DC]">{order + 1}</span>
        <span className="overflow-hidden text-ellipsis whitespace-nowrap font-bold">
          {placeName}
        </span>
        <span className="shrink-0 text-xs text-[#bbb]">{category || ''}</span>
      </div>
      <span className="text-xs">{address}</span>
      <span className={`text-xs ${phone ? 'text-[#343539]' : 'text-[#bbb]'}`}>
        {phone ? phone : '전화번호 미등록'}
      </span>
    </div>
  );
};

type SearchResultProps = SearchPlaceByCategory &
  SearchPlaceByKeyword & {
    onResetSearch: () => void;
  };

const SearchResult = ({
  keyword,
  onResetSearch,
  currentX,
  currentY,
  categoryCode,
}: SearchResultProps) => {
  const [currentPage, setCurrentPage] = useState(1);
  const { data, error, isLoading } = useSearchPlaceQuery({
    pageParam: currentPage,
    keyword,
    currentX,
    currentY,
    categoryCode,
  });

  return (
    <>
      <div className="pointer-events-auto relative left-0 min-h-fit w-80 overflow-y-scroll rounded-lg border-[1px] border-solid border-[#bbb] bg-white drop-shadow-lg">
        <ResultInfo>
          <div>
            <span className="mr-1 inline-block max-w-[8rem] overflow-hidden text-ellipsis align-top font-bold text-[#4568DC]">
              {keyword}
            </span>
            {isLoading ? '검색중...' : <span>검색 결과</span>}
          </div>
          <MdClose size={14} color="#bbb" className="cursor-pointer" onClick={onResetSearch} />
        </ResultInfo>
        <div className="h-fit">
          {isLoading ? (
            <div className="flex items-center justify-center border-t-[1px] border-solid border-[#bbb] p-4 text-sm text-[#bbb]">
              검색중...
            </div>
          ) : error ? (
            <div>에러</div> // TODO : 에러 Fallback
          ) : data?.places.length ? (
            <div>
              {data.places.map(
                ({ id, place_name, road_address_name, category_group_name, phone }, idx) => (
                  <ResultItem
                    key={id}
                    order={idx}
                    placeName={place_name}
                    address={road_address_name}
                    category={category_group_name}
                    phone={phone}
                  />
                )
              )}
              {data.pagination.first! !== data.pagination.last! && (
                <div className="flex w-full items-center justify-center gap-3 border-t-[1px] p-2">
                  {Array.from({ length: data.pagination.last! }).map((_, idx) => (
                    <button
                      onClick={() => {
                        if (idx + 1 > currentPage) {
                          setCurrentPage(idx + 1);
                        } else if (idx + 1 < currentPage) {
                          setCurrentPage(idx + 1);
                        }
                      }}
                      className={`cursor-pointer px-1 text-sm hover:opacity-50 ${
                        idx + 1 === currentPage
                          ? 'font-extrabold text-[#4568DC]'
                          : 'font-normal text-[#343539]'
                      }`}
                    >
                      {idx + 1}
                    </button>
                  ))}
                </div>
              )}
            </div>
          ) : (
            <div className="flex items-center justify-center border-t-[1px] border-solid border-[#bbb] p-4 text-sm text-[#bbb]">
              검색 결과가 없습니다.
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default SearchResult;
