import Contact from "Frontend/generated/engineer/untitled/switter/entity/Contact";
import ContactModel from "Frontend/generated/engineer/untitled/switter/entity/ContactModel";
import { accountStore } from "Frontend/stores/app-store";
import { makeAutoObservable, observable } from "mobx";

class ListViewStore {
  selectedContact: Contact | null = null;
  filterText = "";

  constructor() {
    makeAutoObservable(
      this,
      { selectedContact: observable.ref },
      { autoBind: true }
    );
  }

  updateFilter(filterText: string) {
    this.filterText = filterText;
  }

  setSelectedContact(contact: Contact) {
    this.selectedContact = contact;
  }

  editNew() {
    this.selectedContact = ContactModel.createEmptyValue();
  }

  cancelEdit() {
    this.selectedContact = null;
  }

  async save(contact: Contact) {
    // await accountStore.saveContact(contact);
    this.cancelEdit();
  }

  async delete() {
    if (this.selectedContact) {
      // await accountStore.deleteContact(this.selectedContact);
      this.cancelEdit();
    }
  }

  get filteredContacts() {
    const filter = new RegExp(this.filterText, "i");
    const contacts = accountStore.user.authorities;
    return contacts.filter((contact) =>
      filter.test(`${contact}`)
    );
  }
}

export const listViewStore = new ListViewStore();
