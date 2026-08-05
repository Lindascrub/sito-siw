package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link RecensioneController}.
 */
@Generated
public class RecensioneController__BeanDefinitions {
  /**
   * Get the bean definition for 'recensioneController'.
   */
  public static BeanDefinition getRecensioneControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RecensioneController.class);
    InstanceSupplier<RecensioneController> instanceSupplier = InstanceSupplier.using(RecensioneController::new);
    instanceSupplier = instanceSupplier.andThen(RecensioneController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
