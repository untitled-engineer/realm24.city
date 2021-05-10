import { MiddlewareContext } from "@vaadin/flow-frontend";
import { MiddlewareNext } from "@vaadin/flow-frontend";
import { ConnectClient } from "@vaadin/flow-frontend";
//import { uiStore } from "./stores/app-store";

import {logout} from "Frontend/auth";

const client = new ConnectClient({
  prefix: "connect",
  middlewares: [
    async (context: MiddlewareContext, next: MiddlewareNext) => {
      const response = await next(context);
      // Log out if the session has expired
      if (response.status === 401) {
        await logout();
      }
      return response;
    },
  ],
});

export default client;
