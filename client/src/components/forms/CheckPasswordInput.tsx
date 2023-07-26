import { UseFormReturn } from 'react-hook-form';

import { Input, Paragraph } from '@/components';
import { ChangePasswordType } from '@/schema/changePasswordSchema';

interface CheckPasswordInputProps {
  returnForm: UseFormReturn<ChangePasswordType, any, undefined>;
}

const CheckPasswordInput = ({ returnForm }: CheckPasswordInputProps) => {
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

export default CheckPasswordInput;
