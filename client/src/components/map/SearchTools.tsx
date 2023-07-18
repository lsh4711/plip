import { CgRedo } from '@react-icons/all-files/cg/CgRedo';
import { useState } from 'react';
import { useDispatch } from 'react-redux';

import { Button, Input } from '@/components';
import RoundButton from '@/components/common/RoundButton';
import { CategoryButtonGroup, SearchResult } from '@/components/map';
import { CategoryNames, categories } from '@/datas/categories';
import { setSearchPlaceResults, setSelectedPlace } from '@/redux/slices/placeSlice';
import { CategoryGroupCode } from '@/types/mapApi/place-types';
import { getDistanceByLatLng } from '@/utils/map';

type Props = {
  currentLng: number;
  currentLat: number;
};

const SearchTools = ({ currentLng, currentLat }: Props) => {
  const [input, setInput] = useState('');
  const [keyword, setKeyword] = useState<any>(null);
  const [categoryCode, setCategoryCode] = useState<CategoryGroupCode>('');
  const [searchCenterPosition, setSearchCenterPosition] = useState({
    lat: currentLng,
    lng: currentLat,
  });

  const dispatch = useDispatch();

  const onResetSearch = () => {
    setInput('');
    setKeyword(null);
    setSearchCenterPosition({ lat: currentLat, lng: currentLng });
    dispatch(setSelectedPlace(null));
    dispatch(setSearchPlaceResults([]));
  };

  return (
    <>
      <div className="pointer-events-none absolute bottom-20 left-6 top-6 z-50 flex flex-col gap-4">
        <div className="flex gap-4">
          <Input
            className="pointer-events-auto h-10 w-80 drop-shadow-xl"
            placeholder="장소를 검색하세요."
            value={input}
            onChange={(e) => {
              setInput(e.target.value);
            }}
            onKeyDown={(e) => {
              if (e.key === 'Enter') {
                dispatch(setSelectedPlace(null));
                if (input.length > 0) {
                  setKeyword(input);
                  setCategoryCode('');
                }
              }
            }}
          />
          <CategoryButtonGroup>
            {CategoryNames.map((categoryName, idx) => (
              <RoundButton
                key={idx}
                onClick={() => {
                  setInput(categories[categoryName].name);
                  setKeyword(categories[categoryName].name);
                  setCategoryCode(categories[categoryName].code);
                  setSearchCenterPosition({ lat: currentLat, lng: currentLng });
                  dispatch(setSelectedPlace(null));
                }}
              >
                {categories[categoryName].icon}
              </RoundButton>
            ))}
          </CategoryButtonGroup>
        </div>
        {keyword && (
          <SearchResult
            keyword={keyword}
            onResetSearch={onResetSearch}
            currentX={searchCenterPosition.lng}
            currentY={searchCenterPosition.lat}
            categoryCode={categoryCode}
          />
        )}
      </div>

      {keyword &&
        getDistanceByLatLng({
          lat1: currentLat,
          lng1: currentLng,
          lat2: searchCenterPosition.lat,
          lng2: searchCenterPosition.lng,
        }) > 1 && (
          <Button
            variant={'optional'}
            hovercolor={'default'}
            className={`fixed bottom-8 left-1/2 z-[99] rounded-3xl py-1 text-[#4568dc] drop-shadow-lg hover:bg-[#eee]`}
            onClick={() => {
              dispatch(setSelectedPlace(null));
              setSearchCenterPosition({ lat: currentLat, lng: currentLng });
            }}
          >
            <div className="flex gap-1">
              <CgRedo size={22} />이 지역 재검색
            </div>
          </Button>
        )}
    </>
  );
};

export default SearchTools;
