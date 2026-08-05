package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link RecensioneService}.
 */
@Generated
public class RecensioneService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static RecensioneService apply(RegisteredBean registeredBean, RecensioneService instance) {
    AutowiredFieldValueResolver.forRequiredField("recensioneRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
