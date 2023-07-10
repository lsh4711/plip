import { regionInfos, regions } from '@/datas/regions';

const getTripTitleWithRegion = (region: (typeof regions)[number]) => {
  return regionInfos[region].regionName + ' 여행';
};

export default getTripTitleWithRegion;
