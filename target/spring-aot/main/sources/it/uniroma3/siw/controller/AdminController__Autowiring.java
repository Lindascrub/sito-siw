package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link AdminController}.
 */
@Generated
public class AdminController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static AdminController apply(RegisteredBean registeredBean, AdminController instance) {
    AutowiredFieldValueResolver.forRequiredField("prodottoRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("categoriaRepository").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("tagliaRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
