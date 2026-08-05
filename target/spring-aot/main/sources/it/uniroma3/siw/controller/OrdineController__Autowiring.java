package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link OrdineController}.
 */
@Generated
public class OrdineController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static OrdineController apply(RegisteredBean registeredBean, OrdineController instance) {
    AutowiredFieldValueResolver.forRequiredField("ordineService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("authenticationHelper").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
