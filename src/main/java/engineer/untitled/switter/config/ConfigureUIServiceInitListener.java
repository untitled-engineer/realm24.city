package engineer.untitled.switter.config;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener
    implements VaadinServiceInitListener {

  @Override
  public void serviceInit(ServiceInitEvent serviceInitEvent) {
    System.out.println(serviceInitEvent.getSource().toString());

    serviceInitEvent.getSource().addUIInitListener(uiEvent -> {
      final UI ui = uiEvent.getUI();
      ui.addBeforeEnterListener(this::beforeEnter);
    });
  }

  private void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    System.out.println("hi");
    /*
    f(!SecurityUtils.isAccessGranted(event.getNavigationTarget())) { //
        if(SecurityUtils.isUserLoggedIn()) { //
            event.rerouteToError(NotFoundException.class); //
        } else {
            event.rerouteTo(LoginView.class); //
        }
    }
     */
  }
}
