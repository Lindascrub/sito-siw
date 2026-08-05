package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Prodotto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import java.util.function.LongSupplier;
import org.springframework.aot.generate.Generated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.DeclaredQuery;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.support.PageableExecutionUtils;

/**
 * AOT generated JPA repository implementation for {@link ProdottoRepository}.
 */
@Generated
public class ProdottoRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ProdottoRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByCategoriaId(java.lang.Long)}.
   */
  public List<Prodotto> findByCategoriaId(Long categoriaId) {
    String queryString = "SELECT p FROM Prodotto p WHERE p.categoria.id = :categoriaId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("categoriaId", categoriaId);

    return (List<Prodotto>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByCategoriaNome(java.lang.String,org.springframework.data.domain.Pageable)}.
   */
  public Page<Prodotto> findByCategoriaNome(String categoriaNome, Pageable pageable) {
    String queryString = "SELECT p FROM Prodotto p LEFT JOIN p.categoria c WHERE c.nome = :categoriaNome";
    String countQueryString = "SELECT COUNT(p) FROM Prodotto p LEFT JOIN p.categoria c WHERE c.nome = :categoriaNome";
    Pageable pageable_1 = pageable != null ? pageable : Pageable.unpaged();
    if (pageable_1.getSort().isSorted()) {
      DeclaredQuery declaredQuery = DeclaredQuery.jpqlQuery(queryString);
      queryString = rewriteQuery(declaredQuery, pageable_1.getSort(), Prodotto.class);
    }
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("categoriaNome", categoriaNome);
    if (pageable_1.isPaged()) {
      query.setFirstResult(Long.valueOf(pageable_1.getOffset()).intValue());
      query.setMaxResults(pageable_1.getPageSize());
    }
    LongSupplier countAll = () -> {
      Query countQuery = this.entityManager.createQuery(countQueryString);
      countQuery.setParameter("categoriaNome", categoriaNome);
      return getCount(countQuery);
    };

    return PageableExecutionUtils.getPage((List<Prodotto>) query.getResultList(), pageable_1, countAll);
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByNomeContainingIgnoreCase(java.lang.String)}.
   */
  public List<Prodotto> findByNomeContainingIgnoreCase(String nome) {
    String queryString = "SELECT p FROM Prodotto p WHERE UPPER(p.nome) LIKE UPPER(:nome) ESCAPE '\\'";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("nome", "%%%s%%".formatted(nome != null ? nome.toUpperCase() : nome));

    return (List<Prodotto>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByNomeContainingIgnoreCase(java.lang.String,org.springframework.data.domain.Pageable)}.
   */
  public Page<Prodotto> findByNomeContainingIgnoreCase(String nome, Pageable pageable) {
    String queryString = "SELECT p FROM Prodotto p WHERE UPPER(p.nome) LIKE UPPER(:nome) ESCAPE '\\'";
    String countQueryString = "SELECT COUNT(p) FROM Prodotto p WHERE UPPER(p.nome) LIKE UPPER(:nome) ESCAPE '\\'";
    Pageable pageable_1 = pageable != null ? pageable : Pageable.unpaged();
    if (pageable_1.getSort().isSorted()) {
      DeclaredQuery declaredQuery = DeclaredQuery.jpqlQuery(queryString);
      queryString = rewriteQuery(declaredQuery, pageable_1.getSort(), Prodotto.class);
    }
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("nome", "%%%s%%".formatted(nome != null ? nome.toUpperCase() : nome));
    if (pageable_1.isPaged()) {
      query.setFirstResult(Long.valueOf(pageable_1.getOffset()).intValue());
      query.setMaxResults(pageable_1.getPageSize());
    }
    LongSupplier countAll = () -> {
      Query countQuery = this.entityManager.createQuery(countQueryString);
      countQuery.setParameter("nome", "%%%s%%".formatted(nome != null ? nome.toUpperCase() : nome));
      return getCount(countQuery);
    };

    return PageableExecutionUtils.getPage((List<Prodotto>) query.getResultList(), pageable_1, countAll);
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByPrezzoBetween(java.lang.Double,java.lang.Double)}.
   */
  public List<Prodotto> findByPrezzoBetween(Double min, Double max) {
    String queryString = "SELECT p FROM Prodotto p WHERE p.prezzo BETWEEN :min AND :max";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("min", min);
    query.setParameter("max", max);

    return (List<Prodotto>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByPrezzoBetween(java.lang.Double,java.lang.Double,org.springframework.data.domain.Pageable)}.
   */
  public Page<Prodotto> findByPrezzoBetween(Double min, Double max, Pageable pageable) {
    String queryString = "SELECT p FROM Prodotto p WHERE p.prezzo BETWEEN :min AND :max";
    String countQueryString = "SELECT COUNT(p) FROM Prodotto p WHERE p.prezzo BETWEEN :min AND :max";
    Pageable pageable_1 = pageable != null ? pageable : Pageable.unpaged();
    if (pageable_1.getSort().isSorted()) {
      DeclaredQuery declaredQuery = DeclaredQuery.jpqlQuery(queryString);
      queryString = rewriteQuery(declaredQuery, pageable_1.getSort(), Prodotto.class);
    }
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("min", min);
    query.setParameter("max", max);
    if (pageable_1.isPaged()) {
      query.setFirstResult(Long.valueOf(pageable_1.getOffset()).intValue());
      query.setMaxResults(pageable_1.getPageSize());
    }
    LongSupplier countAll = () -> {
      Query countQuery = this.entityManager.createQuery(countQueryString);
      countQuery.setParameter("min", min);
      countQuery.setParameter("max", max);
      return getCount(countQuery);
    };

    return PageableExecutionUtils.getPage((List<Prodotto>) query.getResultList(), pageable_1, countAll);
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByPrezzoGreaterThanEqual(java.lang.Double,org.springframework.data.domain.Pageable)}.
   */
  public Page<Prodotto> findByPrezzoGreaterThanEqual(Double min, Pageable pageable) {
    String queryString = "SELECT p FROM Prodotto p WHERE p.prezzo >= :min";
    String countQueryString = "SELECT COUNT(p) FROM Prodotto p WHERE p.prezzo >= :min";
    Pageable pageable_1 = pageable != null ? pageable : Pageable.unpaged();
    if (pageable_1.getSort().isSorted()) {
      DeclaredQuery declaredQuery = DeclaredQuery.jpqlQuery(queryString);
      queryString = rewriteQuery(declaredQuery, pageable_1.getSort(), Prodotto.class);
    }
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("min", min);
    if (pageable_1.isPaged()) {
      query.setFirstResult(Long.valueOf(pageable_1.getOffset()).intValue());
      query.setMaxResults(pageable_1.getPageSize());
    }
    LongSupplier countAll = () -> {
      Query countQuery = this.entityManager.createQuery(countQueryString);
      countQuery.setParameter("min", min);
      return getCount(countQuery);
    };

    return PageableExecutionUtils.getPage((List<Prodotto>) query.getResultList(), pageable_1, countAll);
  }

  /**
   * AOT generated implementation of {@link ProdottoRepository#findByPrezzoLessThanEqual(java.lang.Double,org.springframework.data.domain.Pageable)}.
   */
  public Page<Prodotto> findByPrezzoLessThanEqual(Double max, Pageable pageable) {
    String queryString = "SELECT p FROM Prodotto p WHERE p.prezzo <= :max";
    String countQueryString = "SELECT COUNT(p) FROM Prodotto p WHERE p.prezzo <= :max";
    Pageable pageable_1 = pageable != null ? pageable : Pageable.unpaged();
    if (pageable_1.getSort().isSorted()) {
      DeclaredQuery declaredQuery = DeclaredQuery.jpqlQuery(queryString);
      queryString = rewriteQuery(declaredQuery, pageable_1.getSort(), Prodotto.class);
    }
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("max", max);
    if (pageable_1.isPaged()) {
      query.setFirstResult(Long.valueOf(pageable_1.getOffset()).intValue());
      query.setMaxResults(pageable_1.getPageSize());
    }
    LongSupplier countAll = () -> {
      Query countQuery = this.entityManager.createQuery(countQueryString);
      countQuery.setParameter("max", max);
      return getCount(countQuery);
    };

    return PageableExecutionUtils.getPage((List<Prodotto>) query.getResultList(), pageable_1, countAll);
  }
}
