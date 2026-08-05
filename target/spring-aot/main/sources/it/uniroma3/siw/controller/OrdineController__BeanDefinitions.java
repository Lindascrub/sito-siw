package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrdineController}.
 */
@Generated
public class OrdineController__BeanDefinitions {
  /**
   * Get the bean definition for 'ordineController'.
   */
  public static BeanDefinition getOrdineControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrdineController.class);
    InstanceSupplier<OrdineController> instanceSupplier = InstanceSupplier.using(OrdineController::new);
    instanceSupplier = instanceSupplier.andThen(OrdineController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
