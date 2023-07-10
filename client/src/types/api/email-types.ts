//POST : /email/signup
export interface EmailPostRequest {
  email: string;
}

//POST : /email/auth
export interface AuthPostRequest {
  email: string;
  authCode: string;
}
