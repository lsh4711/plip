import React from 'react';

interface SortingToolbarProps {}

const SortingToolbar = ({}: SortingToolbarProps) => {
  return (
    <div className="flex max-w-[243px] rounded-lg border border-zinc-400">
      <button className=" border-r border-zinc-400 px-3 py-2 text-zinc-600 hover:bg-zinc-200">
        최신순
      </button>
      <button className=" border-r border-zinc-400 px-3 py-2 text-zinc-600 hover:bg-zinc-200">
        오래된 순
      </button>
      <button className="px-3 py-2 text-zinc-600 hover:bg-zinc-200">인기순</button>
    </div>
  );
};

export default SortingToolbar;
