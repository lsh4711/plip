import { regionInfos, regions } from '@/datas/regions';

const getRegionCenterLat = (region: (typeof regions)[number]) => {
  return regionInfos[region].coords.lat;
};

export default getRegionCenterLat;
