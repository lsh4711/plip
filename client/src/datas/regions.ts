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

export type RegionInfo = {
  id: number;
  regionName: string;
  imgUrl: string;
};

export const regionInfos: Record<(typeof regions)[number], RegionInfo> = {
  seoul: { id: 1, regionName: '서울', imgUrl: '/region/seoul.webp' },
  incheon: { id: 2, regionName: '인천', imgUrl: '/region/incheon.webp' },
  daejeon: { id: 3, regionName: '대전', imgUrl: '/region/daejeon.webp' },
  ulsan: { id: 4, regionName: '울산', imgUrl: '/region/ulsan.webp' },
  daegu: { id: 5, regionName: '대구', imgUrl: '/region/daegu.webp' },
  busan: { id: 6, regionName: '부산', imgUrl: '/region/busan.webp' },
  gyeonggi: { id: 7, regionName: '경기도', imgUrl: '/region/gyeonggi.webp' },
  gangwon: { id: 8, regionName: '강원도', imgUrl: '/region/gangwon.webp' },
  chungbuk: { id: 9, regionName: '충청북도', imgUrl: '/region/chungbuk.webp' },
  chungnam: { id: 10, regionName: '충청남도', imgUrl: '/region/chungnam.webp' },
  gyeongbuk: { id: 11, regionName: '경상북도', imgUrl: '/region/gyeongbuk.webp' },
  gyeongnam: { id: 12, regionName: '경상남도', imgUrl: '/region/gyeongnam.webp' },
  jeonbuk: { id: 13, regionName: '전라북도', imgUrl: '/region/jeonbuk.webp' },
  jeonnam: { id: 14, regionName: '전라남도', imgUrl: '/region/jeonnam.webp' },
  jeju: { id: 15, regionName: '제주도', imgUrl: '/region/jeju.webp' },
};
