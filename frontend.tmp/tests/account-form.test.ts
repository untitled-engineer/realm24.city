import { expect } from "@esm-bundle/chai";
import sinon from "sinon";
import { fixture, html } from "@open-wc/testing-helpers";
import { customElement } from "lit-element";
import { TextFieldElement } from "@vaadin/vaadin-text-field";
import { ComboBoxElement } from "@vaadin/vaadin-combo-box";
import { ButtonElement } from "@vaadin/vaadin-button";
import { AccountForm } from "Frontend/views/account-grid/account-form";
import "../views/account-grid/account-form.ts";

@customElement("test-account-form")
class TestContactForm extends AccountForm {
  validateSpy = sinon.spy(this.binder, "validate");
  submitSpy = sinon.spy(this.binder, "submitTo");
}

const setValue = (field: TextFieldElement, value: string) => {
  field.value = value;
  field.dispatchEvent(new CustomEvent("change"));
};

const selectItem = (field: ComboBoxElement, item: number) => {
  field.selectedItem = field.items ? field.items[item] : null;
  field.dispatchEvent(new CustomEvent("change"));
};

describe("account-form", () => {
  let form: TestContactForm;
  let fields: Array<TextFieldElement>;
  let combos: Array<ComboBoxElement>;
  let buttons: Array<ButtonElement>;

  beforeEach(async () => {
    form = await fixture(html`<test-contact-form></test-contact-form>`);
    fields = Array.from(form.querySelectorAll("vaadin-text-field"));
    combos = Array.from(form.querySelectorAll("vaadin-combo-box"));
    buttons = Array.from(form.querySelectorAll("vaadin-button"));
  });

  it("saves contact when binder is valid", async () => {
    setValue(fields[0], "John");
    setValue(fields[1], "Smith");
    setValue(fields[2], "john@gmail.com");
    selectItem(combos[0], 0);
    selectItem(combos[1], 0);

    buttons[0].click();
    await form.validateSpy.returnValues[0];

    expect(form.submitSpy.calledOnce).to.be.true;
  });
});
