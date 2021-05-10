import {TranslatableStringEndpoint} from "Frontend/generated/TranslatableStringEndpoint";
import TranslatableStringModel from "Frontend/generated/engineer/untitled/switter/entity/TranslatableString"
import {makeAutoObservable, observable} from "mobx";

export class TranslatableStringsStore {
  translations: Array<TranslatableStringModel> = [];

  constructor() {
    makeAutoObservable(
      this,
      {
        translations: observable.shallow,
      },
      {autoBind: true}
    );
  }

  public async getTranslations() {
    this.translations = await TranslatableStringEndpoint.getTranslations();
    console.log(this.translations)
  }
}
