package fr.mismo.demo_web_addin.repository;
import fr.mismo.demo_web_addin.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.mismo.demo_web_addin.model.Action;


import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {}