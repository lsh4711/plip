import { CategoryGroupCode } from '@/types/mapApi/place-types';
import { useInfiniteQuery } from '@tanstack/react-query';

export interface UseSearchPlaceQueryBaseProps {
  currentX: number;
  currentY: number;
}

interface SearchPlaceResults {
  places: kakao.maps.services.PlacesSearchResult;
  pagination: kakao.maps.Pagination;
}

export interface SearchPlaceByKeyword extends UseSearchPlaceQueryBaseProps {
  keyword: string;
  pageParam?: number;
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
        radius: 2000,
        page: pageParam,
      }
    );
  });
};

export interface SearchPlaceByCategory extends UseSearchPlaceQueryBaseProps {
  categoryCode: CategoryGroupCode;
  pageParam?: number;
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
        page: pageParam,
      }
    );
  });
};

const useSearchPlaceQuery = ({
  keyword,
  currentX,
  currentY,
  categoryCode,
}: SearchPlaceByCategory & SearchPlaceByKeyword) => {
  let queryFn;

  if (categoryCode === '') {
    queryFn = ({ pageParam = 1 }) =>
      searchPlaceByKeyword({ pageParam, keyword, currentX, currentY });
  } else {
    queryFn = ({ pageParam = 1 }) =>
      searchPlaceByCategory({ pageParam, currentX, currentY, categoryCode });
  }

  return useInfiniteQuery({
    queryKey: ['searchPlace', keyword, currentX, currentY, categoryCode],
    queryFn,
    getNextPageParam: (lastPage, allPages) => {
      return (
        lastPage.pagination.current < lastPage.pagination.last && lastPage.pagination.current + 1
      );
    },
    suspense: false,
  });
};

export default useSearchPlaceQuery;
