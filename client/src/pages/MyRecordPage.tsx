interface MyRecordPageProps {}

const MyRecordPage = ({}: MyRecordPageProps) => {
  return (
    <div className=" flex w-full items-center justify-center">
      <nav className=" sticky right-0 top-0 h-screen border-r border-zinc-400">하이욤</nav>
      <main className=" flex w-full max-w-[1024px] items-center justify-center">엄준식</main>
    </div>
  );
};

export default MyRecordPage;
