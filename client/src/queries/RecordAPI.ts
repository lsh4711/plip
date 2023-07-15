import axios from 'axios';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';

type RequestTypes = {
  param: string;
  formData?: FileList[];
  content?: string;
};

export default class RecordAPI {
  readonly fileClient;

  constructor() {
    this.fileClient = axios.create({
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
    return this.fileClient.post(`${param}/img`, formData);
  }

  async onPostRecord(param: number, content: string, formData: FormData) {
    return this.#postRecord(param, content).then((res) => {
      const recordUrl = res.headers.location;
      console.log(recordUrl);
      if (res.status >= 400) {
        throw Error('일지 작성에 실패하였습니다.');
      }

      return this.#postImages(recordUrl, formData);
    });
  }

  async onDeleteRecord() {}

  async onEditRecord() {}
}
