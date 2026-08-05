package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link RecensioneController}.
 */
@Generated
public class RecensioneController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static RecensioneController apply(RegisteredBean registeredBean,
      RecensioneController instance) {
    AutowiredFieldValueResolver.forRequiredField("recensioneService").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("authenticationHelper").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
