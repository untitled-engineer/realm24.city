package engineer.untitled.switter.service.repository;

import engineer.untitled.switter.entity.Status;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
