package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link UtenteService}.
 */
@Generated
public class UtenteService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static UtenteService apply(RegisteredBean registeredBean, UtenteService instance) {
    AutowiredFieldValueResolver.forRequiredField("utenteRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("credenzialiService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
