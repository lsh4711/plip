import instance from '@/queries/axiosinstance';

import { useEffect, useState } from 'react';
import { ReactComponent as MapMarker } from '@/assets/icons/map-marker.svg';

import ImageSlider from '../../common/ImageSlider';
import { useMapDetailContext } from '@/contexts/MapDetailProvider';
import { regionInfos } from '@/datas/regions';
import TextContent from '@/components/common/TextContent';
import { getFromNow } from '@/utils/date';
import useGetRecordImageQuery from '@/queries/record/useGetRecordImageQuery';
import LoadingSpinner from '@/components/atom/LoadingSpinner';
import Button from '@/components/atom/Button';
import useModal from '@/hooks/useModal';
import Confirm from '@/components/common/Confirm';
import useToast from '@/hooks/useToast';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';

type Props = {
  recordRefetch: () => void;
};

const Record = ({ recordRefetch }: Props) => {
  const { selectedPlace } = useSelector((state: RootState) => state.place);
  const { records } = useSelector((state: RootState) => state.records);

  const [imgs, setImgs] = useState<string[]>([]);
  const toast = useToast();
  const [openModal] = useModal();

  const { scheduleInfo } = useMapDetailContext();
  const { region } = scheduleInfo;
  const { imgUrl } = regionInfos[region];
  const { recordId, createdAt, content, placeName } = records[selectedPlace?.schedulePlaceId!][0];

  const { data, isLoading, error, refetch } = useGetRecordImageQuery(recordId);

  // const [isLoading, setIsLoading] = useState(false);

  const onDeleteRecordHandler = (recordId: number) => {
    openModal(({ isOpen, close }) => {
      return (
        <Confirm
          isOpen={isOpen}
          onClose={close}
          content="정말로 삭제하시겠습니까?"
          title="알림"
          type={'warning'}
          primaryLabel="확인"
          secondaryLabel="취소"
          onClickPrimaryButton={() =>
            instance
              .delete(`/api/records/${recordId}`)
              .then((res) => {
                if (res.status === 204) {
                  toast({ content: '일지 삭제 성공', type: 'success' });
                  recordRefetch();
                  close();
                }
              })
              .catch((e) => console.error(e))
          }
        />
      );
    });
  };

  useEffect(() => {
    // setIsLoading(true);
    // const getImages = async () => {
    //   await instance.get(`/api/records/${recordId}/img`).then((res) => {
    //     setIsLoading(false);
    //     setImgs(res.data.images);
    //     console.log(res.data.images);
    //   });
    // };
    // getImages();
  }, [recordId]);

  return (
    <div className="flex flex-col">
      <div className="h-min-[300px] mb-4 w-full">
        {isLoading && (
          <div className="flex h-[300px] w-full items-center justify-center">
            <LoadingSpinner className="h-[64px] w-[64px]" />
          </div>
        )}
        {!isLoading && (
          <ImageSlider
            imgs={data}
            defaultImage={imgUrl}
            recordId={Number(recordId)}
            content={content}
            recordRefetch={recordRefetch}
            imageRefetch={refetch}
          />
        )}
      </div>
      <div className="flex justify-between px-4">
        <span className="w-min-[180px] flex items-center text-sm font-semibold text-[#4568DC]">
          <MapMarker width={12} height={12} fill="#4568DC" />
          {placeName}
        </span>
        <span className="text-xs text-slate-400">{getFromNow(createdAt as Date)}</span>
      </div>
      <TextContent content={content} styles={'p-4 flex-1 overflow-y-scroll'} />
      <Button
        className="mr-4 self-end text-sm text-[#4568DC]"
        variant={'default'}
        hovercolor={'default'}
        onClick={() => onDeleteRecordHandler(Number(recordId))}
      >
        삭제하기
      </Button>
    </div>
  );
};

export default Record;
