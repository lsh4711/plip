interface Distance {
  lat1: number | string;
  lng1: number | string;
  lat2: number | string;
  lng2: number | string;
}

function deg2rad(deg: number) {
  return deg * (Math.PI / 180);
}

function getDistanceByLatLng({ lat1, lng1, lat2, lng2 }: Distance) {
  lat1 = typeof lat1 === 'string' ? parseFloat(lat1) : lat1;
  lng1 = typeof lng1 === 'string' ? parseFloat(lng1) : lng1;
  lat2 = typeof lat2 === 'string' ? parseFloat(lat2) : lat2;
  lng2 = typeof lng2 === 'string' ? parseFloat(lng2) : lng2;

  const R = 6371; // 지구 반지름 (단위: km)
  const dLat = deg2rad(lat2 - lat1);
  const dlng = deg2rad(lng2 - lng1);
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dlng / 2) * Math.sin(dlng / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  const distance = R * c; // 두 지점 간의 거리 (단위: km)

  return distance;
}

export default getDistanceByLatLng;
