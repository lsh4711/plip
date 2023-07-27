import { useWindowSize } from 'react-use';
import Confetti from 'react-confetti';

import { HeadingParagraph, Paragraph } from '@/components';
import useEventQuery from '@/queries/useEventQuery';

const EventPage = () => {
  const { data, isError, error } = useEventQuery();

  const { width, height } = useWindowSize();

  return (
    <main className="mt-[76px] flex h-full w-full flex-col items-center justify-center">
      {isError ? (
        <Paragraph variant={'blue'} size={'sm'}>
          이벤트 참여를 위해 {error.response?.data}
        </Paragraph>
      ) : data?.win ? (
        <div className="mt-16 flex h-fit flex-col items-center justify-center p-36">
          <Confetti width={width} height={height} initialVelocityY={-10} />
          <HeadingParagraph variant={'blue'} size={'xl'}>
            🎉 축하합니다 🎉
          </HeadingParagraph>
          <HeadingParagraph variant={'blue'} size={'md'}>
            선착순 방문 이벤트에 당첨되셨습니다!
          </HeadingParagraph>
          <img
            src={`data:image/jpeg;base64,${data?.giftCodeImage}`}
            alt="기프티콘"
            className="my-8 w-48"
          />
          <Paragraph variant={'darkgray'} size={'sm'} className="font-semibold">
            나의 방문 순서 : <span className="gradient-text">{data?.ranking}</span>번째
          </Paragraph>
          <Paragraph variant={'darkgray'} size={'sm'} className="font-semibold">
            🫶 <span className="gradient-text">PliP</span>에 방문해 주셔서 감사드립니다 🫶
          </Paragraph>
        </div>
      ) : (
        <>
          <HeadingParagraph variant={'darkgray'} size={'lg'} className="mb-2 mt-52">
            아쉽지만 이벤트가 종료되었습니다.
          </HeadingParagraph>
          <Paragraph variant={'darkgray'} size={'sm'} className="font-semibold">
            🫶 그래도 <span className="gradient-text">PliP</span>에 방문해 주셔서 진심으로
            감사드립니다 🫶
          </Paragraph>
        </>
      )}
    </main>
  );
};

export default EventPage;
