package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link CarrelloController}.
 */
@Generated
public class CarrelloController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static CarrelloController apply(RegisteredBean registeredBean,
      CarrelloController instance) {
    AutowiredFieldValueResolver.forRequiredField("carrelloService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("ordineService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("authenticationHelper").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
