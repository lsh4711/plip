import { useState } from 'react';
import { useDispatch } from 'react-redux';

import { Input } from '@/components';
import RoundButton from '@/components/common/RoundButton';
import { CategoryButtonGroup, SearchResult } from '@/components/map';
import { CategoryNames, categories } from '@/datas/categories';
import { setResult } from '@/redux/slices/searchPlaceSlice';
import { CategoryGroupCode } from '@/types/mapApi/place-types';

type Props = {
  currentX: number;
  currentY: number;
};

const SearchTools = ({ currentX, currentY }: Props) => {
  const [input, setInput] = useState('');
  const [keyword, setKeyword] = useState<any>(null);
  const [categoryCode, setCategoryCode] = useState<CategoryGroupCode>('');

  const dispatch = useDispatch();

  const onResetSearch = () => {
    setInput('');
    setKeyword(null);
    dispatch(setResult([]));
  };

  return (
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
          currentX={currentX}
          currentY={currentY}
          categoryCode={categoryCode}
        />
      )}
    </div>
  );
};

export default SearchTools;
