package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Recensione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link RecensioneRepository}.
 */
@Generated
public class RecensioneRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public RecensioneRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link RecensioneRepository#findByProdottoIdOrderByDataCreazioneDesc(java.lang.Long)}.
   */
  public List<Recensione> findByProdottoIdOrderByDataCreazioneDesc(Long prodottoId) {
    String queryString = "SELECT r FROM Recensione r WHERE r.prodotto.id = :prodottoId ORDER BY r.dataCreazione desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("prodottoId", prodottoId);

    return (List<Recensione>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link RecensioneRepository#findByUtenteId(java.lang.Long)}.
   */
  public List<Recensione> findByUtenteId(Long utenteId) {
    String queryString = "SELECT r FROM Recensione r WHERE r.utente.id = :utenteId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("utenteId", utenteId);

    return (List<Recensione>) query.getResultList();
  }
}
