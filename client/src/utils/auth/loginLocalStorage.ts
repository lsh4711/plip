class LoginLocalStorage {
  private readonly keyword = 'wasLogin';

  public get getWasLoginFromLocalStorage() {
    const wasLogin = window.localStorage.getItem(this.keyword);

    if (wasLogin === 'true') return true;
    return false;
  }

  public setWasLoginToTrue() {
    window.localStorage.setItem(this.keyword, 'true');
  }

  public setRemoveWasLoginFromLocalStorage() {
    window.localStorage.removeItem(this.keyword);
  }
}

const loginLocalStorage = new LoginLocalStorage();

export default loginLocalStorage;
