import { Button, HeadingParagraph, Input } from './components';

export default function App() {
  return (
    <main className="flex flex-col items-center justify-center overflow-x-hidden py-24">
      <div className=" flex">
        <Button>안녕하세요</Button>
        <Button
          variant="primary"
          hovercolor={'active'}
          hoveropacity="active"
          activecolor={'active'}
        >
          Sign up
        </Button>
        <Button variant={'ring'}>Sign in</Button>
        <Button size={'lg'} variant="primary">
          Sign up
        </Button>
      </div>
      <div className="flex flex-col">
        <Input placeholder="email" />
      </div>
      <div className=" flex flex-col">
        <HeadingParagraph size={'xl'} variant="gray">
          이번 국내 여행,
          <br /> 보다 알찬 여행으로 만들고 싶다면? <br /> GitHub로 여행 계획을 디자인해 보세요
        </HeadingParagraph>
        <HeadingParagraph variant={'blue'}>어엄</HeadingParagraph>
      </div>
    </main>
  );
}
