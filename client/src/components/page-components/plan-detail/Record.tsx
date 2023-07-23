import instance from '@/queries/axiosinstance';

import { Record as RecordType } from '@/types/api/records-types';
import { useEffect, useState } from 'react';
import { ReactComponent as MapMarker } from '@/assets/icons/map-marker.svg';

import ImageSlider from '../../common/ImageSlider';
import { useMapDetailContext } from '@/contexts/MapDetailProvider';
import RecordImage from './RecordImage';
import { regionInfos } from '@/datas/regions';
import TextContent from '@/components/common/TextContent';
import { getFromNow } from '@/utils/date';

type Props = {
  content: RecordType;
};

const Record = ({ content }: Props) => {
  const [imgs, setImgs] = useState<string[]>([]);
  const { scheduleInfo } = useMapDetailContext();

  const { region, korRegion } = scheduleInfo;
  const { imgUrl } = regionInfos[region];
  const { recordId, createdAt } = content;

  useEffect(() => {
    instance.get(`/api/records/${recordId}/img`).then((res) => {
      setImgs(res.data.images);
    });
  }, [recordId]);

  return (
    <div>
      <div className="h-min-[300px] mb-4 w-full">
        {imgs.length > 0 ? (
          <>
            <ImageSlider imgs={imgs} />
          </>
        ) : (
          <RecordImage imgSrc={imgUrl} />
        )}
      </div>
      <div className="flex justify-between px-4">
        <span className="flex items-center text-sm font-semibold text-[#4568DC]">
          <MapMarker width={12} height={12} fill="#4568DC" />
          {korRegion}, {content.placeName}
        </span>

        <span className="text-xs text-slate-400">{getFromNow(createdAt as Date)}</span>
      </div>
      <TextContent content={content.content} styles={'p-4'} />
    </div>
  );
};

export default Record;
