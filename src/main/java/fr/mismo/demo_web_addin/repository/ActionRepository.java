package fr.mismo.demo_web_addin.repository;

import fr.mismo.demo_web_addin.projection.ActionProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionRepository extends Repository<Object, Long> {

    @Query(value = """
        EXEC SP_ATHENEO_LISTER_ACTIONS
        @STATUT=:statut,
        @PRIORITE=:priorite,
        @TYPE=:type,
        @ID_INTERLOCUTEUR=:idInterlocuteur,
        @LIMITE=:limite
        """, nativeQuery = true)
    List<ActionProjection> listerActions(
            @Param("statut") String statut,
            @Param("priorite") String priorite,
            @Param("type") String type,
            @Param("idInterlocuteur") Long idInterlocuteur,
            @Param("limite") int limite
    );

}