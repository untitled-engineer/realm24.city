import {makeAutoObservable, observable, runInAction} from "mobx";
import * as endpoint from "Frontend/generated/UserInfoEndpoint";
import UserProfileModel from "Frontend/generated/engineer/untitled/switter/UserProfileModel";
import UserProfile from "Frontend/generated/engineer/untitled/switter/UserProfile";
import {cacheable} from "./cacheable";
import {Authentication, AUTHENTICATION_KEY} from "Frontend/auth";

let authentication: Authentication | undefined = undefined;

export class AccountStore {

  authentication: Authentication | null = null;

  user: UserProfile;

  constructor() {
    this.user = UserProfileModel.createEmptyValue();

    makeAutoObservable(
      this,
      {
        initFromServer: false,
        user: observable.shallow,
      },
      {autoBind: true}
    );

    this.initFromServer().then(value => console.log(value));
  }

  async initFromServer() {
    const data = await cacheable(
      endpoint.getUserInfo,
      "userInfo",
      UserProfileModel.createEmptyValue()
    );
    runInAction(() => {
      const tmp: UserProfile = UserProfileModel.createEmptyValue();
      tmp.username = data.username.toString();
      data.authorities.valueOf().forEach(function (auth: any) {
        tmp.authorities.push(auth);
      })
      tmp.avatar = data.avatar.toString();
      const authentication = {
        user: {...tmp},
        timestamp: new Date().getTime(),
      };
      localStorage.setItem(AUTHENTICATION_KEY, JSON.stringify(authentication));
      this.user = {...tmp};
    });
  }

  /*
  async saveContact(contact: Contact) {
    try {
      this.saveLocal(await endpoint.saveContact(contact));
      uiStore.showSuccess("Contact saved.");
    } catch (e) {
      console.log(e);
      uiStore.showError("Contact save failed.");
    }
  }

  async deleteContact(contact: Contact) {
    if (!contact.id) return;

    try {
      await endpoint.deleteContact(contact.id);
      this.deleteLocal(contact);
      uiStore.showSuccess("Contact deleted.");
    } catch (e) {
      console.log(e);
      uiStore.showError("Failed to delete contact.");
    }
  }

  private saveLocal(saved: Contact) {
    const contactExists = this.contacts.some((c) => c.id === saved.id);
    if (contactExists) {
      this.contacts = this.contacts.map((existing) => {
        if (existing.id === saved.id) {
          return saved;
        } else {
          return existing;
        }
      });
    } else {
      this.contacts.push(saved);
    }
  }

  private deleteLocal(contact: Contact) {
    this.contacts = this.contacts.filter((c) => c.id !== contact.id);
  }
   */
}
