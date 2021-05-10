package engineer.untitled.switter.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.connect.Endpoint;
import engineer.untitled.switter.entity.TranslatableString;
import engineer.untitled.switter.service.repository.TranslatableStringRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class TranslatableStringEndpoint {

  @Autowired
  private TranslatableStringRepository translatableStringRepository;

  public Iterable<TranslatableString> getTranslations () {

    return this.translatableStringRepository.findAll();
  }
}
