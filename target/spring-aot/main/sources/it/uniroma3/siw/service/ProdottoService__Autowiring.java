package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ProdottoService}.
 */
@Generated
public class ProdottoService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ProdottoService apply(RegisteredBean registeredBean, ProdottoService instance) {
    AutowiredFieldValueResolver.forRequiredField("prodottoRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
