package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link CredenzialiService}.
 */
@Generated
public class CredenzialiService__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static CredenzialiService apply(RegisteredBean registeredBean,
      CredenzialiService instance) {
    AutowiredFieldValueResolver.forRequiredField("credenzialiRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
