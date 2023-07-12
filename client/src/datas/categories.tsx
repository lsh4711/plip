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
