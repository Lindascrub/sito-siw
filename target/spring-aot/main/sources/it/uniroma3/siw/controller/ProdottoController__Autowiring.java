package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ProdottoController}.
 */
@Generated
public class ProdottoController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ProdottoController apply(RegisteredBean registeredBean,
      ProdottoController instance) {
    AutowiredFieldValueResolver.forRequiredField("prodottoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("categoriaRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("recensioneService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
