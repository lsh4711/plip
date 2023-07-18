import { useQuery } from '@tanstack/react-query';

import { CategoryGroupCode } from '@/types/mapApi/place-types';

export interface UseSearchPlaceQueryBaseProps {
  pageParam?: number;
  currentX: number;
  currentY: number;
}

interface SearchPlaceResults {
  places: kakao.maps.services.PlacesSearchResult;
  pagination: kakao.maps.Pagination;
}

export interface SearchPlaceByKeyword extends UseSearchPlaceQueryBaseProps {
  keyword: string;
}

const searchPlaceByKeyword = ({ pageParam, keyword, currentX, currentY }: SearchPlaceByKeyword) => {
  const placeService = new kakao.maps.services.Places();

  return new Promise<SearchPlaceResults>((resolve, reject) => {
    placeService.keywordSearch(
      keyword,
      (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
          resolve({ places: data, pagination });
        }
        if (status === kakao.maps.services.Status.ERROR) {
          reject(new Error('에러 발생'));
        }
        if (status === kakao.maps.services.Status.ZERO_RESULT) {
          reject(new Error('검색 결과 없음'));
        }
      },
      {
        x: currentX,
        y: currentY,
        radius: 20000,
        page: pageParam,
      }
    );
  });
};

export interface SearchPlaceByCategory extends UseSearchPlaceQueryBaseProps {
  categoryCode: CategoryGroupCode;
}

const searchPlaceByCategory = ({
  categoryCode,
  pageParam,
  currentX,
  currentY,
}: SearchPlaceByCategory) => {
  const placeService = new kakao.maps.services.Places();

  return new Promise<SearchPlaceResults>((resolve, reject) => {
    placeService.categorySearch(
      categoryCode,
      (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
          resolve({ places: data, pagination });
        }
        if (status === kakao.maps.services.Status.ERROR) {
          reject(new Error('에러 발생'));
        }
        if (status === kakao.maps.services.Status.ZERO_RESULT) {
          reject(new Error('검색 결과 없음'));
        }
      },
      {
        x: currentX,
        y: currentY,
        radius: 20000,
        page: pageParam,
      }
    );
  });
};

const useSearchPlaceQuery = ({
  pageParam,
  keyword,
  currentX,
  currentY,
  categoryCode,
}: SearchPlaceByCategory & SearchPlaceByKeyword) => {
  let queryFn;

  if (categoryCode === '') {
    queryFn = () => searchPlaceByKeyword({ pageParam, keyword, currentX, currentY });
  } else {
    queryFn = () => searchPlaceByCategory({ pageParam, currentX, currentY, categoryCode });
  }

  return useQuery({
    queryKey: ['searchPlace', keyword, currentX, currentY, categoryCode, pageParam],
    queryFn,
    keepPreviousData: true,
    suspense: false,
  });
};

export default useSearchPlaceQuery;
