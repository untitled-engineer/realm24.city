package engineer.untitled.switter.service.repository;

import engineer.untitled.switter.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
