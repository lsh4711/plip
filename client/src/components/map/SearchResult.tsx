import {
  SearchPlaceByCategory,
  SearchPlaceByKeyword,
  default as useSearchPlaceQuery,
} from '@/queries/search/useKeywordSearchQuery';
import { MdClose } from '@react-icons/all-files/md/MdClose';
import { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';

import { setSearchPlaceResults, setSelectedPlace } from '@/redux/slices/placeSlice';
import { CategoryGroupCode } from '@/types/mapApi/place-types';

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
  placeId: number;
  placeName: string;
  address: string;
  categoryGroupName: string;
  category: CategoryGroupCode;
  phone: string;
  latitude: string;
  longitude: string;
};

const ResultItem = ({
  placeId,
  order,
  placeName,
  address,
  categoryGroupName,
  category,
  phone,
  latitude,
  longitude,
}: ResultItemProps) => {
  const dispatch = useDispatch();

  return (
    <div
      className="flex cursor-pointer flex-col gap-1 border-t-[1px] border-solid px-4 py-2"
      onClick={() =>
        dispatch(
          setSelectedPlace({
            apiId: placeId,
            name: placeName,
            address,
            latitude,
            longitude,
            category,
            bookmark: false, // TODO : 추후 변경
            phone,
          })
        )
      }
    >
      <div className="flex items-center gap-2">
        <span className="font-extrabold text-[#4568DC]">{order + 1}</span>
        <span className="overflow-hidden text-ellipsis whitespace-nowrap font-bold">
          {placeName}
        </span>
        <span className="shrink-0 text-xs text-[#bbb]">{categoryGroupName || ''}</span>
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

  const dispatch = useDispatch();

  useEffect(() => {
    if (data) {
      dispatch(setSearchPlaceResults(data.places));
    }
  }, [data]);

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
            <div className="flex items-center justify-center border-t-[1px] border-solid border-[#bbb] p-4 text-sm text-[#bbb]">
              검색에 실패했어요. 잠시 후에 다시 시도해 주세요.
            </div>
          ) : data?.places.length ? (
            <div>
              {data.places.map(
                (
                  {
                    id,
                    place_name,
                    address_name,
                    category_group_code,
                    category_group_name,
                    phone,
                    x,
                    y,
                  },
                  idx
                ) => (
                  <ResultItem
                    key={id}
                    placeId={Number(id)}
                    order={idx}
                    placeName={place_name}
                    address={address_name}
                    categoryGroupName={category_group_name}
                    category={category_group_code as CategoryGroupCode}
                    phone={phone}
                    latitude={y}
                    longitude={x}
                  />
                )
              )}
              {data.pagination.first! !== data.pagination.last! && (
                <div className="flex w-full items-center justify-center gap-3 border-t-[1px] p-2">
                  {Array.from({ length: data.pagination.last! }).map((_, idx) => (
                    <button
                      key={idx}
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
