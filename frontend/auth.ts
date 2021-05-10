// Uses the Vaadin provided login an logout helper methods
import {login as loginImpl, LoginResult, logout as logoutImpl} from '@vaadin/flow-frontend';
import {UserInfoEndpoint} from 'Frontend/generated/UserInfoEndpoint';
import UserProfile from 'Frontend/generated/engineer/untitled/switter/UserProfile';
// import Role from 'Frontend/generated/engineer/untitled/switter/entity/Role';
import {uiStore} from "Frontend/stores/app-store";

import {clearCache} from "Frontend/stores/cacheable";

export interface Authentication {
  user: UserProfile;
  timestamp: number;
}

let authentication: Authentication | undefined = undefined;

export const AUTHENTICATION_KEY = 'authentication';
const THIRTY_DAYS_MS = 30 * 24 * 60 * 60 * 1000;
// Get authentication from local storage
const storedAuthenticationJson = localStorage.getItem(AUTHENTICATION_KEY);

if (storedAuthenticationJson !== null) {
  const storedAuthentication = JSON.parse(storedAuthenticationJson) as Authentication;
  // Check that the stored timestamp is not older than 30 days
  const hasRecentAuthenticationTimestamp =
    new Date().getTime() - storedAuthentication.timestamp < THIRTY_DAYS_MS;
  if (hasRecentAuthenticationTimestamp) {
    // Use loaded authentication
    authentication = storedAuthentication;
  } else {
    // Delete expired stored authentication
    setSessionExpired();
  }
}

/**
 * Forces the session to expire and removes user information stored in
 * `localStorage`.
 */
export function setSessionExpired() {
  authentication = undefined;

  // Delete the authentication from the local storage
  localStorage.removeItem(AUTHENTICATION_KEY);
}

/**
 * Login wrapper method that retrieves user information.
 *
 * Uses `localStorage` for offline support.
 */
export async function login(username: string, password: string): Promise<LoginResult> {
  const result = await loginImpl(username, password);
  if (!result.error) {
    // uiStore.setLoggedIn(true);
    // Get user info from endpoint
    const user = await UserInfoEndpoint.getUserInfo();

    authentication = {
      // @ts-ignore
      user,
      timestamp: new Date().getTime(),
    };

    // Save the authentication to local storage
    localStorage.setItem(AUTHENTICATION_KEY, JSON.stringify(authentication));

    // todo we want load whot
    //await accountStore.initFromServer();
  }

  return result;
}

/**
 * Login wrapper method that retrieves user information.
 *
 * Uses `localStorage` for offline support.
 */
export async function logout() {
  setSessionExpired();
  await logoutImpl();
  uiStore.setLoggedIn(false);
  clearCache();
  return await logoutImpl();
}

/**
 * Checks if the user is logged in.
 */
export async function isLoggedIn() {
  //if (!authentication || !authentication.user.username) {
  const user = await UserInfoEndpoint.getUserInfo();

  authentication = {
    // @ts-ignore
    user,
    timestamp: new Date().getTime(),
  };

  // Save the authentication to local storage
  localStorage.setItem(AUTHENTICATION_KEY, JSON.stringify(authentication));

  // todo we want load whot
  //await accountStore.initFromServer();
  //}

  const tmp = authentication.user.username.length > 0;

  //debugger
  return tmp

  //return !!authentication;
}

/**
 * Checks if the user has the role.
 */
export function isUserInRole(role: string) {
  if (!authentication) {
    return false;
  }

  let x: string = `ROLE_${role.trim().toUpperCase()}`

  return authentication.user.authorities.includes(x);
}