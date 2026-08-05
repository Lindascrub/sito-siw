package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link OrdineRepository}.
 */
@Generated
public class OrdineRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public OrdineRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link OrdineRepository#findByUtenteOrderByDataCreazioneDesc(it.uniroma3.siw.model.Utente)}.
   */
  public List<Ordine> findByUtenteOrderByDataCreazioneDesc(Utente utente) {
    String queryString = "SELECT o FROM Ordine o WHERE o.utente = :utente ORDER BY o.dataCreazione desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("utente", utente);

    return (List<Ordine>) query.getResultList();
  }
}
