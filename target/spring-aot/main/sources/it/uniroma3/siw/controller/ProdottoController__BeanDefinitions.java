package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProdottoController}.
 */
@Generated
public class ProdottoController__BeanDefinitions {
  /**
   * Get the bean definition for 'prodottoController'.
   */
  public static BeanDefinition getProdottoControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProdottoController.class);
    InstanceSupplier<ProdottoController> instanceSupplier = InstanceSupplier.using(ProdottoController::new);
    instanceSupplier = instanceSupplier.andThen(ProdottoController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
