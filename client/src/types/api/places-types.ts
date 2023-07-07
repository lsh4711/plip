//GET : /places
export interface getPlacesResponse {
  place_id: string | number;
  api_id: string | number;
  name: string;
  address: string;
  latitude: number;
  lognitude: number;
  bookmark: boolean;
  der: number;
}

//POST : /places/{place-id}/records
export interface GetRecordsByPlaceIdResponse {
  recordId: number;
  title: string;
  content: string;
  memberId: number;
  createdAt: string | Date;
  modifiedAt: string | Date;
}
