import { UseFormReturn } from 'react-hook-form';
import { EditProfileTypes } from './MypageForm';
import Paragraph from '../atom/Paragraph';
import Input from '../atom/Input';

interface MyPageEditInputProps {
  returnForm: UseFormReturn<EditProfileTypes, any, undefined>;
}

const MyPageEditInput = ({ returnForm }: MyPageEditInputProps) => {
  return (
    <>
      <div className="flex justify-end">
        <Paragraph variant={'red'} size="xs" className=" mt-1">
          <span>{returnForm.formState.errors.password?.message}</span>
        </Paragraph>
      </div>
      <div className="flex flex-col">
        <div className="flex flex-col md:flex-row md:items-center md:gap-4">
          <span className="w-20 text-sm text-[#4568DC] md:text-end">비밀번호 확인</span>
          <Input
            type={'password'}
            placeholder="다시 한번 비밀번호를 입력해 주세요"
            className="flex-grow py-2"
            {...returnForm.register('checkpassword', {
              onChange: () => console.log(returnForm.formState.errors),
            })}
          />
        </div>
        <div className="flex justify-end">
          <Paragraph variant={'red'} size="xs" className=" mt-1">
            <p>{returnForm.formState.errors.checkpassword?.message}</p>
          </Paragraph>
        </div>
      </div>
    </>
  );
};

export default MyPageEditInput;
