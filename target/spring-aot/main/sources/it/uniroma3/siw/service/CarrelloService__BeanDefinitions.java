package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CarrelloService}.
 */
@Generated
public class CarrelloService__BeanDefinitions {
  /**
   * Get the bean definition for 'carrelloService'.
   */
  public static BeanDefinition getCarrelloServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CarrelloService.class);
    InstanceSupplier<CarrelloService> instanceSupplier = InstanceSupplier.using(CarrelloService::new);
    instanceSupplier = instanceSupplier.andThen(CarrelloService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
