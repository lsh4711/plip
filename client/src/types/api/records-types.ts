//POST : /records
export interface RecordRequest {
  title: string;
  content: string;
}

//GET : /records/{record-id}
export interface RecordGetRequest {
  recordId: number | string;
}

export interface Record {
  recordId: number | string;
  title: string;
  content: string;
  memberId: number | string;
  createdAt: string | Date;
  modifiedAt: string | Date;
}

//GET : /records/{record-id}
export interface RecordGetResponse {
  data: Record;
}

//GET : /records?page=1&size=10

export interface GetRecordsBymemberIdResponse extends Record {}

//GET : /records/place/{place-id}
export interface GetRecordsByPlaceIdRequest {
  'place-id': string | number;
}

//GET : /records/place/{place-id}

export interface GetRecordsByPlaceIdResponse extends Record {}
