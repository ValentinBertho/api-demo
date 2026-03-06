package fr.mismo.demo_web_addin.repository;

import fr.mismo.demo_web_addin.projection.ContexteProjection;
import fr.mismo.demo_web_addin.projection.InterlocuteurProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.naming.Context;
import java.util.List;

public interface InterlocuteurRepository extends Repository<Object, Long> {

    @Query(value = "EXEC SP_ATHENEO_RECHERCHER_INTERLOCUTEUR @EMAIL=:email", nativeQuery = true)
    List<InterlocuteurProjection> rechercher(@Param("email") String email);

    @Query(value = "EXEC SP_ATHENEO_RECHERCHER_CONTEXTE @EMAIL=:email", nativeQuery = true)
    List<ContexteProjection> rechercherContexte(@Param("email") String email);

}