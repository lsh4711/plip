import { regionInfos, regions } from '@/datas/regions';

const getRegionCenterLng = (region: (typeof regions)[number]) => {
  return regionInfos[region].coords.lng;
};

export default getRegionCenterLng;
