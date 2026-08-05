package it.uniroma3.siw.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CarrelloController}.
 */
@Generated
public class CarrelloController__BeanDefinitions {
  /**
   * Get the bean definition for 'carrelloController'.
   */
  public static BeanDefinition getCarrelloControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CarrelloController.class);
    InstanceSupplier<CarrelloController> instanceSupplier = InstanceSupplier.using(CarrelloController::new);
    instanceSupplier = instanceSupplier.andThen(CarrelloController__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
