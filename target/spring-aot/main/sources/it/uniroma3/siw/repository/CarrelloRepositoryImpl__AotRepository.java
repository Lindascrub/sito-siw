package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link CarrelloRepository}.
 */
@Generated
public class CarrelloRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public CarrelloRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link CarrelloRepository#findByUtente(it.uniroma3.siw.model.Utente)}.
   */
  public Optional<Carrello> findByUtente(Utente utente) {
    String queryString = "SELECT c FROM Carrello c WHERE c.utente = :utente";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("utente", utente);

    return Optional.ofNullable((Carrello) convertOne(query.getSingleResultOrNull(), false, Carrello.class));
  }
}
