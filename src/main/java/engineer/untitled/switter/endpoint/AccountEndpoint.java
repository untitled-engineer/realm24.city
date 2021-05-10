package engineer.untitled.switter.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.connect.Endpoint;
import engineer.untitled.switter.entity.Company;
import engineer.untitled.switter.entity.Contact;
import engineer.untitled.switter.entity.Status;
import engineer.untitled.switter.service.repository.CompanyRepository;
import engineer.untitled.switter.service.repository.ContactRepository;
import engineer.untitled.switter.service.repository.StatusRepository;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class AccountEndpoint {
  private final ContactRepository contactRepository;
  private final CompanyRepository companyRepository;
  private final StatusRepository statusRepository;

  public AccountEndpoint(
      ContactRepository contactRepository,
      CompanyRepository companyRepository,
      StatusRepository statusRepository
  ) {
    this.contactRepository = contactRepository;
    this.companyRepository = companyRepository;
    this.statusRepository = statusRepository;
  }

  public AccountData
  getCrmData() {
    AccountData accountData = new AccountData();
    accountData.contacts = contactRepository.findAll();
    accountData.companies = companyRepository.findAll();
    accountData.statuses = statusRepository.findAll();

    return accountData;
  }

  // @Secured("ROLE_ADMIN")
  public Contact
  saveContact(Contact contact) {
    contact
        .setCompany(companyRepository.findById(contact.getCompany().getId())
            .orElseThrow(() ->
                new RuntimeException("Could not find Company with id" + contact.getCompany().getId())));
    contact
        .setStatus(statusRepository.findById(contact.getStatus().getId())
            .orElseThrow(() ->
                new RuntimeException("Could not find Status with id" + contact.getStatus().getId())));

    return contactRepository.save(contact);
  }

  // @Secured("ROLE_ADMIN")
  public void deleteContact(Long contactId) {
    contactRepository.deleteById(contactId);
  }

  public static class AccountData {
    public List<Contact> contacts;
    public List<Company> companies;
    public List<Status> statuses;
  }
}
