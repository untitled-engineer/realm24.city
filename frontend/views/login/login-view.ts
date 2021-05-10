import { uiStore } from 'Frontend/stores/app-store';
import { customElement, html, internalProperty } from 'lit-element';
import '@vaadin/vaadin-login/vaadin-login-form';
import { AfterEnterObserver, RouterLocation } from '@vaadin/router';
import { View } from '../view';
// ? whf? import {LoginResult} from "./Authentication";
import { LoginResult } from '@vaadin/flow-frontend';
import '@vaadin/vaadin-login/vaadin-login-overlay';
import {login} from 'auth';


@customElement('login-view')
export class LoginView extends View implements AfterEnterObserver {
  @internalProperty()
  private error = false;

  // the url to redirect to after a successful login
  private returnUrl?: string;

  connectedCallback() {
    super.connectedCallback();
    this.classList.add('flex', 'flex-col', 'items-center', 'justify-center');
  }

  render() {
    return html`
      <vaadin-login-overlay 
          opened 
          .error="${this.error}" 
          @login="${this.login}"
          ?disabled=${uiStore.offline}
      >
      </vaadin-login-overlay>
    `;
      /*
    return html`
      <h1>Vaadin CRM</h1>
      <vaadin-login-form
        no-forgot-password
        @login=${this.login}
        .error=${this.error}
        ?disabled=${uiStore.offline}
      >
      </vaadin-login-form>
      ${uiStore.offline
        ? html` <b>You are offline. Login is only available while online.</b> `
        : html` <b>Log in with: user/userpass</b> `}
    `;
       */
  }

  private onSuccess = (result: LoginResult) => {
    // If a login redirect was initiated by opening a protected URL, the server knows where to go (result.redirectUrl).
    // If a login redirect was initiated by the client router, this.returnUrl knows where to go.
    // If login was opened directly, use the default URL provided by the server.
    // As we do not know if the target is a resource or a Fusion view or a Flow view, we cannot just use Router.go
    window.location.href = sessionStorage.getItem("login-redirect-path")
        ||result.redirectUrl
        || this.returnUrl
        || result.defaultUrl
        || '/';
  };

  async login(event: CustomEvent) {
    // use the login helper method from auth.ts, which in turn uses
    // Vaadin provided login helper method to obtain the LoginResult
    const result: LoginResult = await login(event.detail.username, event.detail.password);

    this.error = result.error;

    if (!this.error) {
      this.onSuccess(result);
    }
  }

  // duplicate solved in other place
  onAfterEnter(location: RouterLocation) {
    this.returnUrl = location.redirectFrom;
  }

  // old

  /*
  async login(e: CustomEvent) {
    try {
      await uiStore.login(e.detail.username, e.detail.password);
    } catch (e) {
      this.error = true;
    }
  }
   */
}
