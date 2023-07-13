// 유효성 검증
export const passwordRegex = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/;
export const nicknameRegex = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;

// 여행 일지 관련
export const maxImages = 15;
export const maxRecordCharacters = 300;
