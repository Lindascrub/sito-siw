package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link CategoriaController}.
 */
@Generated
public class CategoriaController__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static CategoriaController apply(RegisteredBean registeredBean,
      CategoriaController instance) {
    AutowiredFieldValueResolver.forRequiredField("categoriaRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
