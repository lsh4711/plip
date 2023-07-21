/*
UserLoginResponse Header
"Authorization:Bearer {access-token}"
"Refresh {refresh-token}" 
*/

//POST : /users/signup
export interface UsersSignupRequest {
  email: string;
  password: string;
  nickname: string;
}

//POST : /users/login
export interface UserLoginRequest {
  username: string;
  password: string;
}

//GET : /users/{user-id}
export interface UserGetRequest {
  memberId: number;
  email: string;
  nickname: string;
}

//PATCH : /users
export interface UserPatchRequest {
  nickname: string;
  password: string;
}
