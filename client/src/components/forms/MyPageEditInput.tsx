import { UseFormReturn } from 'react-hook-form';

import { Input, Paragraph } from '@/components';
import { EditUserType } from '@/schema/editUserSchema';

interface MyPageEditInputProps {
  returnForm: UseFormReturn<EditUserType, any, undefined>;
}

const MyPageEditInput = ({ returnForm }: MyPageEditInputProps) => {
  return (
    <>
      <div className="flex flex-col">
        <div className="flex flex-col md:flex-row md:items-center md:gap-4">
          <span className="w-24 text-sm text-[#4568DC] md:text-end">비밀번호 확인</span>
          <Input
            type={'password'}
            placeholder="다시 한번 비밀번호를 입력해 주세요"
            className="flex-grow py-2"
            {...returnForm.register('checkpassword')}
          />
        </div>
        <div className="flex justify-end">
          <Paragraph variant={'red'} size="xs" className=" mt-1">
            {returnForm.formState.errors.checkpassword?.message}
          </Paragraph>
        </div>
      </div>
    </>
  );
};

export default MyPageEditInput;
