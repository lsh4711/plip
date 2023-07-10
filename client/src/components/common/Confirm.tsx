import { Button, HeadingParagraph, Paragraph } from '@/components';
import { DialogButtonGroup, DialogContainer } from '@/components/common/Dialog';

export type ConfirmProps = {
  type: 'default' | 'warning';
  title: string;
  content?: string;
  primaryLabel: string;
  secondaryLabel?: string;
  onClickPrimaryButton?: () => void;
  onClickSecondaryButton?: () => void;
  isOpen: boolean;
  onClose: () => void;
};

function Confirm({
  type,
  title,
  content,
  primaryLabel,
  secondaryLabel,
  onClickPrimaryButton,
  onClickSecondaryButton,
  isOpen,
  onClose,
}: ConfirmProps) {
  return (
    <DialogContainer isOpen={isOpen} className="w-2/3 rounded-lg bg-white p-6 md:w-[540px]">
      <HeadingParagraph variant={type === 'default' ? 'blue' : 'red'} size={'sm'}>
        {title}
      </HeadingParagraph>
      <Paragraph variant={'black'} size={'default'} className="mt-3 whitespace-pre-line">
        {content}
      </Paragraph>

      <DialogButtonGroup>
        <Button
          variant={type === 'default' ? 'primary' : 'warn'}
          hovercolor={'default'}
          hoveropacity={'active'}
          onClick={() => {
            onClickPrimaryButton ? onClickPrimaryButton() : onClose();
          }}
        >
          {primaryLabel}
        </Button>
        {secondaryLabel && (
          <Button
            variant={'optional'}
            hovercolor={'default'}
            hoveropacity={'active'}
            onClick={() => {
              onClickSecondaryButton ? onClickSecondaryButton() : onClose();
            }}
          >
            {secondaryLabel}
          </Button>
        )}
      </DialogButtonGroup>
    </DialogContainer>
  );
}

export default Confirm;
