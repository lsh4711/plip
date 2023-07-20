import { SIGNOUT_VALIDATION_STRING } from '@/datas/constants';
import { z } from 'zod';

export const signoutSchema = z
  .object({
    signout: z.string(),
  })
  .superRefine(({ signout }, ctx) => {
    if (signout !== SIGNOUT_VALIDATION_STRING) {
      ctx.addIssue({
        code: z.ZodIssueCode.custom,
        message: '문장을 똑같이 작성해주세요',
        path: ['signout'],
      });
    }
  });

export type SignoutType = z.infer<typeof signoutSchema>;
