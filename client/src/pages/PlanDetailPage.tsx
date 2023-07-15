import { Button } from '@/components';
import useToast from '@/hooks/useToast';

interface PlanDetailPageProps {}

const PlanDetailPage = ({}: PlanDetailPageProps) => {
  const toats = useToast();

  return (
    <>
      <Button
        variant={'primary'}
        onClick={() => {
          console.log('clicked');
          toats({ content: 'ddd', type: 'success' });
        }}
      >
        토스트
      </Button>
    </>
  );
};

export default PlanDetailPage;
