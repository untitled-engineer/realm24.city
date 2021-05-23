import "./main-layout.ts";
import "./views/login/login-view";
import "./views/common/redirect-page";
import {autorun} from "mobx";
import {Commands, Context, Route, Router} from "@vaadin/router";
import {isLoggedIn, isUserInRole, logout} from "Frontend/auth";
import {uiStore} from "./stores/app-store";

const authGuard = async (context: Context, commands: Commands) => {
  console.log(uiStore)
  if (!isLoggedIn()) {
    // Save requested path
    sessionStorage.setItem("login-redirect-path", context.pathname);
    return commands.redirect("/login");
  }
  return undefined;
};

export type ViewRoute = Route & {
  title?: string;
  children?: ViewRoute[];
  rolesAllowed?: string[];
};

export const views: ViewRoute[] = [
  {
    path: "todo",
    component: "todo-view",
    title: "Todo",
    rolesAllowed: [],
    action: async function (context: Context, commands: Commands) {
      //await authGuard(context, commands);
      await import("./views/todo/todo-view");
    },
  },
  /*
  {
    path: "accounts",
    component: "list-view",
    title: "Accounts",
    rolesAllowed: ['ADMIN'],
    action: async function (context: Context, commands: Commands) {
      await authGuard(context, commands);
      const route = context.route as ViewRoute;
      if (!isAuthorizedViewRoute(route)) {
        console.log(commands)
        return commands.redirect("/");
      }
      await import("./views/account-grid/list-view");
      return undefined;
    },
  },

   */
];

export const routes: ViewRoute[] = [
  {
    path: "",
    component: "main-layout",
    children: views,
    // action: authGuard,
    rolesAllowed: [],
    action: (_: Context, __: Commands) => {
      return undefined;
    },
  },
  // { path: "oauth2/authorization/github", component: "redirect-page"},
  {
    path: '/login/oauth2/code/(.*)',
    action: (ctx, commands) => {
      console.log(ctx, commands);
      window.location.pathname = ctx.pathname;
      return undefined;
    }
  },
  {
    path: '/oauth2/authorization/(.*)',
    action: (ctx, commands) => {
      console.log(ctx, commands);
      window.location.pathname = ctx.pathname;
      return undefined;
    }
  },
  { path: "login", component: "login-view" },
  /*
  {
    path: "logout",
    action: async (_: Context, commands: Commands) => {
      await logout();
      return commands.redirect("/");
    },
  },
   */
];

export function isAuthorizedViewRoute(route: ViewRoute) {
  if (route.rolesAllowed?.length) {
    return route.rolesAllowed.find((role) => isUserInRole(role));
  }

  return true;
}

// Catch logins and logouts, redirect appropriately
autorun(() => {
  /*
  if (isLoggedIn()) {
      Router.go(sessionStorage.getItem("login-redirect-path") || "/");
  } else {
      if (location.pathname !== "/login") {
          sessionStorage.setItem("login-redirect-path", location.pathname);
      }
      Router.go("/login");
  }
  */
});
