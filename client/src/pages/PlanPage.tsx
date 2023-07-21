import dayjs from 'dayjs';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { Button, HeadingParagraph } from '@/components';
import Confirm from '@/components/common/Confirm';
import DatePicker from '@/components/common/DatePicker';
import RegionCard from '@/components/common/RegionCard';
import { regionInfos, regions } from '@/datas/regions';
import useModal from '@/hooks/useModal';
import usePlanMutation from '@/queries/plan/usePlanMutation';
import { getFormatDateString, getTripPeriod, getTripTitleWithRegion } from '@/utils/date';
import parsePlanId from '@/utils/parsePlanId';

interface PlanPageProps {}

const PlanPage = ({}: PlanPageProps) => {
  const [startDate, setStartDate] = useState<Date | null>();
  const [endDate, setEndDate] = useState<Date | null>();
  const [selectedRegion, setSelectedRegion] = useState<(typeof regions)[number] | null>(null);

  const [openModal] = useModal();
  const navigate = useNavigate();
  const mutation = usePlanMutation();

  const openConfirm = () => {
    if (startDate && endDate && selectedRegion) {
      openModal(({ isOpen, close }) => (
        <Confirm
          type={'default'}
          title={'다음 일정으로 계획을 생성할까요?'}
          content={`${getTripTitleWithRegion(selectedRegion)} (${getFormatDateString(
            startDate,
            true,
            'dot'
          )} ~ ${getFormatDateString(endDate, true, 'dot')}, ${getTripPeriod(startDate, endDate)})`}
          primaryLabel={'확인'}
          secondaryLabel="취소"
          onClickPrimaryButton={() => {
            close();
            createPlan();
          }}
          isOpen={isOpen}
          onClose={close}
        />
      ));
    } else {
      openModal(({ isOpen, close }) => (
        <Confirm
          type={'warning'}
          title={'알림'}
          content={'여행 날짜와 여행지를 선택했는지 확인해 주세요.'}
          primaryLabel={'확인'}
          isOpen={isOpen}
          onClose={close}
        />
      ));
    }
  };

  const createPlan = () => {
    mutation
      .mutateAsync({
        title: getTripTitleWithRegion(selectedRegion!),
        region: selectedRegion!,
        content: null,
        startDate: startDate!,
        endDate: endDate!,
        places: null,
      })
      .then((res) => {
        navigate(`/plan/map/${parsePlanId(res.headers.location)}`);
      })
      .catch((e) => {
        console.log(e);
      });
  };

  return (
    <main className="smooth relative mt-[76px] flex h-full w-full max-w-7xl flex-col px-8 py-12 transition-all duration-300">
      <div className="flex justify-between">
        <HeadingParagraph
          size={'md'}
          variant={'darkgray'}
          className="text-lg font-normal md:text-2xl"
        >
          계획 중이신 여행에 대해 알려주세요.
        </HeadingParagraph>
        <div className="fixed bottom-0 left-0 right-0 z-10 bg-gradient-to-t from-white from-60% to-white/0 px-8 pb-6 pt-3 md:static md:w-auto md:p-0">
          <Button
            variant={'primary'}
            activecolor={'active'}
            className="w-full"
            onClick={openConfirm}
          >
            계획 작성하기
          </Button>
        </div>
      </div>
      <section className="mb-3 mt-7">
        <HeadingParagraph size={'sm'} variant={'blue'}>
          언제 떠나시나요?
        </HeadingParagraph>
        <div className="mt-4 flex flex-col items-end md:flex-row md:items-center">
          <DatePicker
            placeholderText="가는 날 선택"
            selected={startDate}
            onChange={(date) => {
              setStartDate(date);
              if (!endDate || dayjs(date).isAfter(endDate)) {
                setEndDate(date);
              }
            }}
          />
          <span className="text-xs text-white md:mx-4 md:text-[#343539]">~</span>
          <DatePicker
            placeholderText="오는 날 선택"
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            minDate={startDate}
          />
          {startDate && endDate && (
            <span className="mt-2 text-[#343539] md:ml-4">{`(${getTripPeriod(
              startDate,
              endDate
            )})`}</span>
          )}
        </div>
      </section>
      <section className="mb-3 mt-7">
        <HeadingParagraph size={'sm'} variant={'blue'}>
          어디로 떠나시나요?
        </HeadingParagraph>
        <div className="mt-4 grid grid-cols-2 gap-4 md:grid-cols-3 lg:grid-cols-5">
          {regions.map((region) => (
            <RegionCard
              key={regionInfos[region].id}
              imgUrl={regionInfos[region].imgUrl}
              label={regionInfos[region].regionName}
              onClick={() => setSelectedRegion(region)}
              isSelected={region === selectedRegion}
              labelPosition={'topLeft'}
            />
          ))}
        </div>
      </section>
    </main>
  );
};

export default PlanPage;
