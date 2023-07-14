export const regions = [
  'seoul',
  'incheon',
  'gwangju',
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
    coords: { lat: 37.540705, lng: 126.956764 },
  },
  incheon: {
    id: 2,
    regionName: '인천',
    imgUrl: '/region/incheon.webp',
    coords: { lat: 37.469221, lng: 126.573234 },
  },
  gwangju: {
    id: 3,
    regionName: '광주',
    imgUrl: '/region/gwangju.webp',
    coords: { lat: 35.126033, lng: 126.831302 },
  },
  daejeon: {
    id: 4,
    regionName: '대전',
    imgUrl: '/region/daejeon.webp',
    coords: { lat: 36.321655, lng: 127.378953 },
  },
  ulsan: {
    id: 5,
    regionName: '울산',
    imgUrl: '/region/ulsan.webp',
    coords: { lat: 35.519301, lng: 129.239078 },
  },
  daegu: {
    id: 6,
    regionName: '대구',
    imgUrl: '/region/daegu.webp',
    coords: { lat: 35.798838, lng: 128.583052 },
  },
  busan: {
    id: 7,
    regionName: '부산',
    imgUrl: '/region/busan.webp',
    coords: { lat: 35.198362, lng: 129.053922 },
  },
  gyeonggi: {
    id: 8,
    regionName: '경기도',
    imgUrl: '/region/gyeonggi.webp',
    coords: { lat: 37.2893525, lng: 127.0535312 },
  },
  gangwon: {
    id: 9,
    regionName: '강원도',
    imgUrl: '/region/gangwon.webp',
    coords: { lat: 37.555837, lng: 128.209315 },
  },
  chungbuk: {
    id: 10,
    regionName: '충청북도',
    imgUrl: '/region/chungbuk.webp',
    coords: { lat: 36.628503, lng: 127.929344 },
  },
  chungnam: {
    id: 11,
    regionName: '충청남도',
    imgUrl: '/region/chungnam.webp',
    coords: { lat: 36.557229, lng: 126.6734285 },
  },
  gyeongbuk: {
    id: 12,
    regionName: '경상북도',
    imgUrl: '/region/gyeongbuk.webp',
    coords: { lat: 36.248647, lng: 128.664734 },
  },
  gyeongnam: {
    id: 13,
    regionName: '경상남도',
    imgUrl: '/region/gyeongnam.webp',
    coords: { lat: 35.259787, lng: 128.664734 },
  },
  jeonbuk: {
    id: 14,
    regionName: '전라북도',
    imgUrl: '/region/jeonbuk.webp',
    coords: { lat: 35.716705, lng: 127.144185 },
  },
  jeonnam: {
    id: 15,
    regionName: '전라남도',
    imgUrl: '/region/jeonnam.webp',
    coords: { lat: 34.8194, lng: 126.893113 },
  },
  jeju: {
    id: 16,
    regionName: '제주도',
    imgUrl: '/region/jeju.webp',
    coords: {
      lat: 33.364805,
      lng: 126.542671,
    },
  },
};
