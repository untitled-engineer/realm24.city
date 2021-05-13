import { makeAutoObservable, observable, runInAction } from "mobx";

import Company from "Frontend/generated/engineer/untitled/switter/entity/Company";
import Contact from "Frontend/generated/engineer/untitled/switter/entity/Contact";
import Status from "Frontend/generated/engineer/untitled/switter/entity/Status";
import * as endpoint from "Frontend/generated/AccountEndpoint";
import CrmDataModel from "Frontend/generated/engineer/untitled/switter/endpoint/AccountEndpoint/AccountDataModel";
import { cacheable } from "./cacheable";
import { uiStore } from "./app-store";

export class AccountStoreTMP {
  contacts: Contact[] = [];
  companies: Company[] = [];
  statuses: Status[] = [];

  constructor() {
    makeAutoObservable(
      this,
      {
        initFromServer: false,
        contacts: observable.shallow,
        companies: observable.shallow,
        statuses: observable.shallow,
      },
      { autoBind: true }
    );

    this.initFromServer();
  }

  async initFromServer() {
    const data = await cacheable(
      endpoint.getCrmData,
      "crm",
      CrmDataModel.createEmptyValue()
    );

    runInAction(() => {
      this.contacts = data.contacts;
      this.companies = data.companies;
      this.statuses = data.statuses;
    });
  }

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
}
