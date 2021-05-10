package engineer.untitled.switter.service.repository;

import engineer.untitled.switter.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
