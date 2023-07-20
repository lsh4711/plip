import { useSignoutMutation } from '@/queries';
import { SignoutType, signoutSchema } from '@/schema/signoutSchema';
import { zodResolver } from '@hookform/resolvers/zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import Input from '../atom/Input';
import Paragraph from '../atom/Paragraph';
import Button from '../atom/Button';

const SignoutForm = () => {
  const signoutMutation = useSignoutMutation();
  const signoutForm = useForm<SignoutType>({
    resolver: zodResolver(signoutSchema),
  });
  const onSubmit: SubmitHandler<SignoutType> = (data) => {
    signoutMutation.mutate();
  };
  return (
    <form className="flex w-[460px] flex-col" onSubmit={signoutForm.handleSubmit(onSubmit)}>
      <div className=" flex flex-col">
        <Input {...signoutForm.register('signout')} />
        <Paragraph variant={'red'} size={'xs'} className=" mt-1">
          {signoutForm.formState.errors.signout?.message}
        </Paragraph>
      </div>
      <Button variant={'primary'} size={'lg'} type="submit" className=" mt-6">
        회원탈퇴하기
      </Button>
    </form>
  );
};

export default SignoutForm;
