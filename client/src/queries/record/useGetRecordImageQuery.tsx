import { useQuery } from '@tanstack/react-query';
import instance from '../axiosinstance';

const getImages = async (recordId: number | string) =>
  await instance.get(`/api/records/${recordId}/img`).then((res) => res.data);

const useGetRecordImageQuery = (recordId: number | string) => {
  return useQuery({
    queryKey: ['/records/imgs', recordId],
    queryFn: () =>
      getImages(recordId).then((data) => {
        return data.images;
      }),
    useErrorBoundary: false,
    suspense: false,
  });
};

export default useGetRecordImageQuery;
