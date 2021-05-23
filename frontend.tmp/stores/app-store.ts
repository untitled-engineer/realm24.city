import { UiStore } from "./ui-store";
import { AccountStore } from "./account-store";
import {TranslatableStringsStore} from "Frontend/stores/transatable-strings-store";


export class AppStore {
  uiStore = new UiStore();
  accountStore = new AccountStore();
  translatableStringsStore = new TranslatableStringsStore();
  applicationName = "";
  currentViewTitle = "";
  location = "";
}

export const appStore = new AppStore();
export const uiStore = appStore.uiStore;
export const accountStore = appStore.accountStore;
export const translatableStringsStore = appStore.translatableStringsStore;
