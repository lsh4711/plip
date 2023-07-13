// https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-response-body-document

export type CategoryGroupCode =
  | ''
  | 'MT1'
  | 'CS2'
  | 'PS3'
  | 'SC4'
  | 'AC5'
  | 'PK6'
  | 'OL7'
  | 'SW8'
  | 'BK9'
  | 'CT1'
  | 'AG2'
  | 'PO3'
  | 'AT4'
  | 'AD5'
  | 'FD6'
  | 'CE7'
  | 'HP8'
  | 'PM9';

export interface GetPlaceByKeywordRequest {
  query: string; //검색을 원하는 질의어
  category_group_code?: CategoryGroupCode; // 카테고리 그룹 코드, 카테고리로 결과 필터링을 원하는 경우 사용
  x?: string; // 중심 좌표의 X 혹은 경도(longitude) 값 특정 지역을 중심으로 검색할 경우 radius와 함께 사용 가능
  y?: string; // 중심 좌표의 Y 혹은 위도(latitude) 값 특정 지역을 중심으로 검색할 경우 radius와 함께 사용 가능
  radius?: number; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용 (단위: 미터(m), 최소: 0, 최대: 20000)
  rect?: string; //	사각형의 지정 범위 내 제한 검색을 위한 좌표. 지도 화면 내 검색 등 제한 검색에서 사용 가능. 좌측 X 좌표, 좌측 Y 좌표, 우측 X 좌표, 우측 Y 좌표 형식
  page?: number; //	결과 페이지 번호 (최소: 1, 최대: 45, 기본값: 1)
  size?: number; //	한 페이지에 보여질 문서의 개수 (최소: 1, 최대: 45, 기본값: 15)
  sort?: string; //결과 정렬 순서 distance 정렬을 원할 때는 기준 좌표로 쓰일 x, y와 함께 사용 distance 또는 accuracy(기본값: accuracy)
}

export interface GetPlaceByKeywordResponse {
  id: string; // 장소 ID
  place_name: string; // 장소명, 업체명
  category_name: string; // 카테고리 이름
  category_group_code: string; // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
  category_group_name: string; // 중요 카테고리만 그룹핑한 카테고리 그룹명
  phone: string; //	전화번호
  address_name: string; // 전체 지번 주소
  road_address_name: string; //	전체 도로명 주소
  x: string; //	X 좌표값, 경위도인 경우 longitude (경도)
  y: string; //	Y 좌표값, 경위도인 경우 latitude(위도)
  place_url: string; //	장소 상세페이지 URL
  distance: string; //중심좌표까지의 거리 (단, x,y 파라미터를 준 경우에만 존재) 단위 meter
}
