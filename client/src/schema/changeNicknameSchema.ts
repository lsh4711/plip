import { z } from 'zod';

import { nicknameRegex } from '@/datas/constants';

export const changeNicknameSchema = z.object({
  nickname: z
    .string()
    .nonempty({ message: '닉네임은 필수 입력입니다.' })
    .min(2, { message: '닉네임은 2글자 이상 10글자 이하여야합니다.' })
    .max(10, { message: '닉네임은 2글자 이상 10글자 이하여야합니다.' })
    .regex(nicknameRegex, { message: '닉네임에 특수문자는 포함될 수 없습니다.' }),
});

export type ChangeNicknameType = z.infer<typeof changeNicknameSchema>;
