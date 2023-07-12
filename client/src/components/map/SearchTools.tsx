import { useState } from 'react';

import { Input } from '@/components';
import { CategoryButtonGroup, SearchResult } from '@/components/map';
import { CategoryNames, categories } from '@/datas/categories';
import { GetPlaceByKeywordResponse } from '@/types/mapApi/place-types';
import RoundButton from '../common/RoundButton';

type Props = {};

const SearchTools = ({}: Props) => {
  const [input, setInput] = useState('');
  const [keyword, setKeyword] = useState('');
  const [results, setResults] = useState<
    | Pick<
        GetPlaceByKeywordResponse,
        'place_name' | 'road_address_name' | 'phone' | 'id' | 'category_name'
      >[]
    | null
  >(null);

  const onSearch = (keyword: string) => {
    setKeyword(keyword);
    setResults([]); // TODO
  };

  const onResetSearch = () => {
    setInput('');
    setKeyword('');
    setResults(null);
  };

  return (
    <div className="pointer-events-none absolute bottom-20 left-6 top-6 z-50 flex flex-col gap-4">
      <div className="flex gap-4">
        <Input
          className="pointer-events-auto h-10 w-80 drop-shadow-xl"
          placeholder="장소를 검색하세요."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              if (input.length > 0) {
                onSearch(input);
              } else {
                setResults(null);
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
                onSearch(categories[categoryName].name);
              }}
            >
              {categories[categoryName].icon}
            </RoundButton>
          ))}
        </CategoryButtonGroup>
      </div>
      <SearchResult keyword={keyword} results={results} onResetSearch={onResetSearch} />
    </div>
  );
};

export default SearchTools;
