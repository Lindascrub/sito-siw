package it.uniroma3.siw.security;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link AuthenticationHelper}.
 */
@Generated
public class AuthenticationHelper__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static AuthenticationHelper apply(RegisteredBean registeredBean,
      AuthenticationHelper instance) {
    AutowiredFieldValueResolver.forRequiredField("utenteService").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
