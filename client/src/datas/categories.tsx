import { CategoryGroupCode } from '@/types/mapApi/place-types';
import { MdFlag } from '@react-icons/all-files/md/MdFlag';
import { MdHotel } from '@react-icons/all-files/md/MdHotel';
import { MdLocalCafe } from '@react-icons/all-files/md/MdLocalCafe';
import { MdRestaurant } from '@react-icons/all-files/md/MdRestaurant';

export const CategoryNames = ['food', 'cafe', 'attraction', 'accommodation'] as const;

export type Category = {
  name: string;
  icon: React.ReactNode;
  code: CategoryGroupCode;
};

export const categories: Record<(typeof CategoryNames)[number], Category> = {
  food: {
    name: '음식점',
    icon: <MdRestaurant color="#bbb" size={24} />,
    code: 'FD6',
  },
  cafe: {
    name: '카페',
    icon: <MdLocalCafe color="#bbb" size={24} />,
    code: 'CE7',
  },
  attraction: {
    name: '관광명소',
    icon: <MdFlag color="#bbb" size={24} />,
    code: 'AT4',
  },
  accommodation: {
    name: '숙박',
    icon: <MdHotel color="#bbb" size={24} />,
    code: 'AD5',
  },
};

export const allCategories: Record<CategoryGroupCode, string> = {
  '': '카테고리 없음',
  MT1: '대형마트',
  CS2: '편의점',
  PS3: '어린이집, 유치원',
  SC4: '학교',
  AC5: '학원',
  PK6: '주차장',
  OL7: '주유소, 충전소',
  SW8: '지하철역',
  BK9: '은행',
  CT1: '문화시설',
  AG2: '중개업소',
  PO3: '공공기관',
  AT4: '관광명소',
  AD5: '숙박',
  FD6: '음식점',
  CE7: '카페',
  HP8: '병원',
  PM9: '약국',
};
