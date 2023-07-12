import { z } from 'zod';

export const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;

export const resetPasswordSchema = z.object({
  username: z
    .string()
    .min(1, { message: '이메일을 입력해주세요' })
    .email({ message: '유효하지 않은 이메일 양식입니다.' }),
  password: z.string().regex(passwordRegex),
});

export type ResetPasswordType = z.infer<typeof resetPasswordSchema>;
