package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link OrdineService}.
 */
@Generated
public class OrdineService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static OrdineService apply(RegisteredBean registeredBean, OrdineService instance) {
    AutowiredFieldValueResolver.forRequiredField("ordineRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("prodottoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("carrelloService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
