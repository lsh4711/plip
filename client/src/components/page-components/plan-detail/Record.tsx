import instance from '@/queries/axiosinstance';

import { Record as RecordType } from '@/types/api/records-types';
import { useEffect, useState } from 'react';

import ImageSlider from '../../common/ImageSlider';

type Props = {
  content: RecordType;
};

const Record = ({ content }: Props) => {
  const [imgs, setImgs] = useState<string[]>([]);

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
          '디폴트 이미지'
        )}
      </div>
      <div className="p-4">{content.content}</div>
    </div>
  );
};

export default Record;
