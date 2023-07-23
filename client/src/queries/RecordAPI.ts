import axios from 'axios';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';

export type PostRecordTypes = {
  param: number;
  formData?: FormData;
  content?: string;
};

export default class RecordAPI {
  #fileClient;

  constructor() {
    this.#fileClient = axios.create({
      baseURL: BASE_URL,
      headers: {
        'Content-Type': 'multipart/form-data',
        Authorization: instance.defaults.headers['Authorization'],
      },
      withCredentials: true,
    });
  }

  async #postRecord(param: number, content: string) {
    return instance.post(`/api/records/${param}`, content);
  }

  async #postImages(param: number, formData: FormData) {
    return this.#fileClient.post(`${param}/img`, formData);
  }

  async onPostRecord({ param, content, formData }: PostRecordTypes) {
    return this.#postRecord(param, content!).then((res) => {
      const recordUrl = res.headers.location;
      if (res.status >= 400) {
        throw Error('일지 작성에 실패하였습니다.');
      }

      console.log(formData);

      if (formData) {
        return this.#postImages(recordUrl, formData!)
          .then((res) => {
            if (res.status >= 400) {
              throw Error('사진 전송에 실패하였습니다.');
            }
          })
          .catch((e) => console.error(e));
      }

      return;
    });
  }

  async onGetRecord({}) {}

  async onDeleteRecord() {}

  async onEditRecord() {}
}
