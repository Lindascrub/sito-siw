package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link GlobalController}.
 */
@Generated
public class GlobalController__BeanDefinitions {
  /**
   * Get the bean definition for 'globalController'.
   */
  public static BeanDefinition getGlobalControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(GlobalController.class);
    InstanceSupplier<GlobalController> instanceSupplier = InstanceSupplier.using(GlobalController::new);
    instanceSupplier = instanceSupplier.andThen(GlobalController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
