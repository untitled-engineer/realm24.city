package engineer.untitled.realm.service.repository;

import engineer.untitled.realm.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
