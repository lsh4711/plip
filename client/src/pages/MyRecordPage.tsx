import { Button, Image, Paragraph } from '@/components';
import { Link } from 'react-router-dom';

interface MyRecordPageProps {}

const buttonArray = [
  {
    subject: '나의 일정',
    slug: '/mypage/mytrip',
  },
  {
    subject: '마이페이지',
    slug: '/mypage',
  },
  {
    subject: '나의 발자취',
    slug: '/mypage/footprint',
  },
  {
    subject: '북마크',
    slug: '',
  },
  {
    subject: '나의 일지',
    slug: '/mypage/myrecord',
  },
];

const MyRecordPage = ({}: MyRecordPageProps) => {
  return (
    <main className=" flex">
      <nav className=" sticky left-0 right-0 top-0 h-screen w-[18.75rem] border-r border-zinc-400 pt-[6rem]">
        <div className=" flex flex-col items-end gap-y-[1.5rem] pr-6">
          {buttonArray.map((item, idx) => (
            <Button className=" text-[1.25rem] font-normal text-zinc-800 ">
              <Link to={item.slug}>{item.subject}</Link>
            </Button>
          ))}
        </div>
      </nav>
      <div className=" flex flex-col pl-16 pt-12">
        <div className="flex max-w-[243px] rounded-lg border border-zinc-400">
          <button className=" border-r border-zinc-400 px-3 py-2 text-zinc-600 hover:bg-zinc-200">
            최신순
          </button>
          <button className=" border-r border-zinc-400 px-3 py-2 text-zinc-600 hover:bg-zinc-200">
            오래된 순
          </button>
          <button className="px-3 py-2 text-zinc-600 hover:bg-zinc-200">인기순</button>
        </div>

        <div className=" mt-6 grid grid-cols-3">
          <div className=" relative">
            <Image size={'lg'} rounded="active" hoveropacity={'active'} />
            <div className=" absolute bottom-0 left-0 mb-6 ml-6">
              <p className=" text-lg font-semibold text-white">협재해수욕장</p>
              <p className=" text-xs font-thin text-white">제주도</p>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
};

export default MyRecordPage;
