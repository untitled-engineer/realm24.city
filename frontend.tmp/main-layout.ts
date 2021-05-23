import {css, customElement, html, property} from 'lit-element';
import {Layout} from './views/view';
import '@vaadin/vaadin-app-layout';
import '@vaadin/vaadin-app-layout/vaadin-drawer-toggle';

import {isAuthorizedViewRoute, views} from './routes';
import {accountStore, appStore, uiStore, translatableStringsStore} from './stores/app-store';
import {logout, router} from "Frontend/index";

import '@vaadin/vaadin-avatar';

interface RouteInfo {
  path: string;
  title: string;
}

@customElement('main-layout')
export class MainLayout extends Layout {
  static styles = css`
    :host {
      display: block;
      height: 100%;
      width: 100%;
    }
    
    #login-popup {
      position: absolute;
      right: 13px;
      top: 73px;
      width: 250px;
      height: 305px;
      border-radius: 7px;
      box-shadow: 0 0 10px #bbb;
      padding: 30px;
      text-align: center;
    }
    
    .items-grid {
      grid-template-columns: auto 1fr 50px;
      gap: 4px;
      grid-auto-rows: minmax(auto, auto);
      display: grid !important;
      padding-right: 25px !important;
      padding-top: 6px;
    }
    
    .avatar {
      border-radius: 50%;  
      cursor: pointer;
    }
    
    .avatar[:hover] {
      box-shadow: 0 0 5px #fff;
    }
    
    #user-account-nav {
      
    }
  `;

  @property({ type: Boolean, attribute: "isLoggedIn" })
  private isLoggedIn: boolean;

  @property({ type: Boolean, attribute: "oAuthPopupState" })
  private oAuthPopupState: boolean;

  constructor() {
    super();
    this.isLoggedIn = false;
    this.oAuthPopupState = false;
    translatableStringsStore.getTranslations()
    console.log('construct')
    uiStore.isLoggedIn().then((value) => this.isLoggedIn = value);
  }

  private get menuRoutes(): RouteInfo[] {
    return views
      .filter((route) => route.title)
      .filter(isAuthorizedViewRoute) as RouteInfo[];
  }

  render() {
    return html`
      <vaadin-app-layout class="h-full">

        <header slot="navbar" class="w-full flex items-grid items-center  px-m">
          <vaadin-drawer-toggle></vaadin-drawer-toggle>
          <h1 class="text-l m-m">Rearm24.city</h1>
          <vaadin-avatar
              @click="${this.oAuthPopupClickHandler}"
              referrerpolicy="no-referrer"
              .img="${accountStore.user.avatar}"
              .name="${accountStore.user.username}"
          >
        </header>

        <div slot="drawer">
          <div class="flex flex-col h-full mx-m my-l spacing-b-m">
            <vaadin-tabs
                orientation="vertical"
                theme="minimal"
                .selected=${this.getSelectedViewRoute()}>
              ${this.menuRoutes.map(
                  (viewRoute) => html`
                    <vaadin-tab>
                      <a href="${router.urlForPath(viewRoute.path)}"
                         tabindex="-1">${viewRoute.title}</a>
                    </vaadin-tab>
                  `
              )}
            </vaadin-tabs>
          </div>
        </div>

        <div id="login-popup" ?hidden="${!this.oAuthPopupState}">
          <div class="unauthenticated container" ?hidden="${this.isLoggedIn}">
            <div>
              With GitHub: <a href="http://localhost:8080/oauth2/authorization/github">click here</a>
            </div>
            <div>
              With Google: <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
            </div>
            <div>
              With Facebook: <a href="http://localhost:8080/oauth2/authorization/facebook">click here</a>
            </div>
          </div>
          <div class="authenticated container " ?hidden=${!this.isLoggedIn}>
            <h5>${accountStore.user.username.toString()}</h5>
            <ul id="user-account-nav" role="menu">
              <li>Личный кабинет</li>
              <li>Ввести промокод</li>
              <li>Пополнить баланс</li>
              <li>Справочный центр</li>
              <li>
                <a router-ignore href="/logout"
                   @click="${this.onLogoutHandler}"
                   class="ms-auto"
                   ?hidden=${uiStore.offline}
                >Выйти</a>
              </li>
            </ul>
          </div>
        </div>

        <div class="h-full">
          <slot><!-- views go here --></slot>
        </div>

      </vaadin-app-layout>
    `;
  }

  oAuthPopupClickHandler(e: Event) {
    e.preventDefault();

    console.log("click")
    this.oAuthPopupState = !this.oAuthPopupState;
  }

  async connectedCallback() {
    super.connectedCallback()

    console.log('connected')
    console.log(accountStore)

    /*
    accountStore.initFromServer().then(value => {
      console.log(value)
      if (accountStore.user != null) {
        this.username = accountStore.user.username.toString();
      }
    })
     */
  }

  /*
  private getMenuRoutes(): RouteInfo[] {
      return views.filter((route) => route.title) as RouteInfo[];
  }
   */

  private getSelectedViewRoute(): number {
    return this.menuRoutes.findIndex((viewRoute) => viewRoute.path == appStore.location);
  }

  private async onLogoutHandler(e: Event) {
    e.preventDefault();

    let oldValue = this.isLoggedIn;
    console.log('clicked for logout');

    await logout();

    this.isLoggedIn = await uiStore.isLoggedIn();

    //await this.requestUpdate('isLoggedIn', oldValue);
    //await this.updateComplete;

    return undefined;
  }
}
