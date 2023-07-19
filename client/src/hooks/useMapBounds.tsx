import { useEffect, useMemo, useRef } from 'react';

const useMapBounds = (positions: any) => {
  const mapRef = useRef<kakao.maps.Map>(null);

  const bounds = useMemo(() => {
    if (!positions.length) return;

    const bounds = new kakao.maps.LatLngBounds();

    positions.forEach((position: any) =>
      bounds.extend(new kakao.maps.LatLng(parseFloat(position.y), parseFloat(position.x)))
    );

    return bounds;
  }, [positions]);

  useEffect(() => {
    const map = mapRef.current;
    if (map && bounds) map.setBounds(bounds);
  }, [positions]);

  return mapRef;
};

export default useMapBounds;
