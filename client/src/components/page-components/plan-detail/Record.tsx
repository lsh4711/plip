import instance from '@/queries/axiosinstance';

import { Record as RecordType } from '@/types/api/records-types';
import { useEffect, useState } from 'react';

import ImageSlider from '../../common/ImageSlider';
import { useMapDetailContext } from '@/contexts/MapDetailProvider';
import RecordImage from './RecordImage';
import { regionInfos, regions } from '@/datas/regions';

type Props = {
  content: RecordType;
};

const Record = ({ content }: Props) => {
  const [imgs, setImgs] = useState<string[]>([]);
  const { scheduleInfo } = useMapDetailContext();

  const { region, places } = scheduleInfo;
  const { imgUrl } = regionInfos[region];
  if (region) {
    console.log(region);
  }
  // const { imgUrl } = regionInfos[region];

  useEffect(() => {
    instance.get(`/api/records/${content.recordId}/img`).then((res) => {
      setImgs(res.data.images);
    });
  }, [imgs]);

  return (
    <div>
      <div className=" h-min-[300px] w-full">
        {imgs.length > 0 ? (
          <>
            <ImageSlider imgs={imgs} />
          </>
        ) : (
          <RecordImage imgSrc={imgUrl} />
        )}
      </div>
      <div className="p-4">{content.content}</div>
    </div>
  );
};

export default Record;
