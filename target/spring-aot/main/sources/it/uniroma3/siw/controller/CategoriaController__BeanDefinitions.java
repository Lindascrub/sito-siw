package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CategoriaController}.
 */
@Generated
public class CategoriaController__BeanDefinitions {
  /**
   * Get the bean definition for 'categoriaController'.
   */
  public static BeanDefinition getCategoriaControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CategoriaController.class);
    InstanceSupplier<CategoriaController> instanceSupplier = InstanceSupplier.using(CategoriaController::new);
    instanceSupplier = instanceSupplier.andThen(CategoriaController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
