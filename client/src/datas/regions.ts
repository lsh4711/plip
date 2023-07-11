export const regions = [
  'seoul',
  'incheon',
  'daejeon',
  'ulsan',
  'daegu',
  'busan',
  'gyeonggi',
  'gangwon',
  'chungbuk',
  'chungnam',
  'gyeongbuk',
  'gyeongnam',
  'jeonbuk',
  'jeonnam',
  'jeju',
] as const;

export type PositionType = {
  isLoad?: boolean;
  lat: number;
  lng: number;
};

export type RegionInfo = {
  id: number;
  regionName: string;
  imgUrl: string;
  coords: PositionType;
};

export const regionInfos: Record<(typeof regions)[number], RegionInfo> = {
  seoul: {
    id: 1,
    regionName: '서울',
    imgUrl: '/region/seoul.webp',
    coords: { lat: 37.5666612, lng: 126.9783785 },
  },
  incheon: {
    id: 2,
    regionName: '인천',
    imgUrl: '/region/incheon.webp',
    coords: { lat: 37.4562557, lng: 126.7052062 },
  },
  daejeon: {
    id: 3,
    regionName: '대전',
    imgUrl: '/region/daejeon.webp',
    coords: { lat: 36.3504567, lng: 127.3848187 },
  },
  ulsan: {
    id: 4,
    regionName: '울산',
    imgUrl: '/region/ulsan.webp',
    coords: { lat: 35.5396224, lng: 129.3115276 },
  },
  daegu: {
    id: 5,
    regionName: '대구',
    imgUrl: '/region/daegu.webp',
    coords: { lat: 35.8715411, lng: 128.601505 },
  },
  busan: {
    id: 6,
    regionName: '부산',
    imgUrl: '/region/busan.webp',
    coords: { lat: 35.179665, lng: 129.0747635 },
  },
  gyeonggi: {
    id: 7,
    regionName: '경기도',
    imgUrl: '/region/gyeonggi.webp',
    coords: { lat: 37.2893525, lng: 127.0535312 },
  },
  gangwon: {
    id: 8,
    regionName: '강원도',
    imgUrl: '/region/gangwon.webp',
    coords: { lat: 37.8853984, lng: 127.7297758 },
  },
  chungbuk: {
    id: 9,
    regionName: '충청북도',
    imgUrl: '/region/chungbuk.webp',
    coords: { lat: 36.6358093, lng: 127.4913338 },
  },
  chungnam: {
    id: 10,
    regionName: '충청남도',
    imgUrl: '/region/chungnam.webp',
    coords: { lat: 36.6598307, lng: 126.6734285 },
  },
  gyeongbuk: {
    id: 11,
    regionName: '경상북도',
    imgUrl: '/region/gyeongbuk.webp',
    coords: { lat: 36.5760207, lng: 128.5055956 },
  },
  gyeongnam: {
    id: 12,
    regionName: '경상남도',
    imgUrl: '/region/gyeongnam.webp',
    coords: { lat: 35.2382905, lng: 128.692398 },
  },
  jeonbuk: {
    id: 13,
    regionName: '전라북도',
    imgUrl: '/region/jeonbuk.webp',
    coords: { lat: 35.8203989, lng: 127.1087521 },
  },
  jeonnam: {
    id: 14,
    regionName: '전라남도',
    imgUrl: '/region/jeonnam.webp',
    coords: { lat: 34.8162186, lng: 126.4629242 },
  },
  jeju: {
    id: 15,
    regionName: '제주도',
    imgUrl: '/region/jeju.webp',
    coords: {
      lat: 33.3616661,
      lng: 126.5291666,
    },
  },
};
