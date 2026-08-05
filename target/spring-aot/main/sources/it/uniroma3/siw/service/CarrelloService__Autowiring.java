package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link CarrelloService}.
 */
@Generated
public class CarrelloService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static CarrelloService apply(RegisteredBean registeredBean, CarrelloService instance) {
    AutowiredFieldValueResolver.forRequiredField("carrelloRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("prodottoService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
