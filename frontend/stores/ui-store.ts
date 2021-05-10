import {
  ConnectionState,
  ConnectionStateStore,
} from "@vaadin/flow-frontend/ConnectionState";
import { makeAutoObservable, runInAction } from "mobx";
import { accountStore, uiStore } from "./app-store";
import { isLoggedIn } from "Frontend/auth";

class Message {
  constructor(
      public text = "",
      public error = false,
      public open = false
  ) {

  }
}

export class UiStore {
  loggedIn: boolean;
  message = new Message();
  offline = false;

  constructor() {
    makeAutoObservable(
      this,
      {
        connectionStateListener: false,
        connectionStateStore: false,
        setupOfflineListener: false,
      },
      {autoBind: true}
    );
    this.loggedIn = false;
    // this.isLoggedIn().then((value) => this.loggedIn = value );
    this.setupOfflineListener();
  }

  connectionStateStore?: ConnectionStateStore;

  connectionStateListener = () => {
    this.setOffline(
      this.connectionStateStore?.state === ConnectionState.CONNECTION_LOST
    );
  };

  async isLoggedIn(): Promise<boolean> {
    this.loggedIn = await isLoggedIn();

    return this.loggedIn;
  }

  async setupOfflineListener() {
    const $wnd = window as any;
    if ($wnd.Vaadin?.connectionState) {
      this.connectionStateStore = $wnd.Vaadin
        .connectionState as ConnectionStateStore;
      this.connectionStateStore.addStateChangeListener(
        this.connectionStateListener
      );
      this.connectionStateListener();
    }
  }

  private setOffline(offline: boolean) {
    // Refresh from server when going online
    if (this.offline && !offline) {
      accountStore.initFromServer();
    }
    this.offline = offline;
  }

  /* moved to ../auth
  async login(username: string, password: string): Promise<LoginResult> {
    const result = await serverLogin(username, password);
    console.log(result)

    if (!result.error) {
      this.setLoggedIn(true);

      return result
    }

    throw new Error(result.errorMessage || "Login failed");
  }

  async logout() {
    await serverLogout();
    this.setLoggedIn(false);
    clearCache();
  }

   */

  showSuccess(message: string) {
    this.showMessage(message, false);
  }

  showError(message: string) {
    this.showMessage(message, true);
  }

  private showMessage(text: string, error: boolean) {
    this.message = new Message(text, error, true);
    setTimeout(() => runInAction(() => (this.message = new Message())), 5000);
  }

  public setLoggedIn(loggedIn: boolean) {
    console.log(loggedIn)
    this.loggedIn = loggedIn;
    if (loggedIn) {
      //accountStore.initFromServer();
    }
  }
}
