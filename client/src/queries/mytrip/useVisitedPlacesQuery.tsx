import { useQuery } from '@tanstack/react-query';
import instance from '../axiosinstance';

const getVisitedPlaces = () =>
  instance
    .get('/api/schedules', {
      withCredentials: true,
    })
    .then((res) => res.data);

const useVisitedPlacesQuery = () => {
  return useQuery({
    queryKey: ['/schedules'],
    queryFn: getVisitedPlaces,
    useErrorBoundary: true,
  });
};

export default useVisitedPlacesQuery;
