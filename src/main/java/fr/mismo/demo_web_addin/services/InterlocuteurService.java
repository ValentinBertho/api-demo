package fr.mismo.demo_web_addin.services;

import fr.mismo.demo_web_addin.projection.ContexteProjection;
import fr.mismo.demo_web_addin.projection.InterlocuteurProjection;
import fr.mismo.demo_web_addin.repository.InterlocuteurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterlocuteurService {

    private final InterlocuteurRepository repository;

    public InterlocuteurProjection rechercher(String email){

        return repository.rechercher(email)
                .stream()
                .findFirst()
                .orElse(null);

    }

    public ContexteProjection rechercherContexte(String email){

        return repository.rechercherContexte(email)
                .stream()
                .findFirst()
                .orElse(null);

    }

}