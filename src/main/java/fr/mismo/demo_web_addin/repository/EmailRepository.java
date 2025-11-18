package fr.mismo.demo_web_addin.repository;

import fr.mismo.demo_web_addin.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {}

