import axios from 'axios';
import BASE_URL from './BASE_URL';
import instance from './axiosinstance';

export type RecordTypes = {
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

  async #patchRecord(param: number, content: string) {
    return instance.patch(`/api/records/${param}`, { content: content });
  }

  async #postRecord(param: number, content: string) {
    return instance.post(`/api/records/${param}`, content);
  }

  async #patchImages(param: number, formData: FormData) {
    return this.#fileClient.post(`/api/records/${param}/img`, formData);
  }

  async #postImages(param: number, formData: FormData) {
    return this.#fileClient.post(`${param}/img`, formData);
  }

  async onPostRecord({ param, content, formData }: RecordTypes) {
    return this.#postRecord(param, content!).then((res) => {
      const recordUrl = res.headers.location;
      if (res.status >= 400) {
        throw Error('일지 작성에 실패하였습니다.');
      }

      if (formData?.has('images')) {
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

  async onPatchRecord({ param, content, formData }: RecordTypes) {
    return this.#patchRecord(param, content!).then((res) => {
      if (res.status >= 400) {
        throw Error('일지 수정에 실패하였습니다.');
      }

      if (formData?.has('images')) {
        return this.#patchImages(param, formData!)
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

  async onDeleteRecordImage(memberId: number, recordId: number, index: number) {
    // /memberId/recordId/index
    // 이미지 전체 삭제 https://teamdev.shop/test/delete
  }

  async onEditRecord() {}
}
