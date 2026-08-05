package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link GlobalController}.
 */
@Generated
public class GlobalController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static GlobalController apply(RegisteredBean registeredBean, GlobalController instance) {
    AutowiredFieldValueResolver.forRequiredField("authenticationHelper").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
