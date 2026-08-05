package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CredenzialiService}.
 */
@Generated
public class CredenzialiService__BeanDefinitions {
  /**
   * Get the bean definition for 'credenzialiService'.
   */
  public static BeanDefinition getCredenzialiServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CredenzialiService.class);
    InstanceSupplier<CredenzialiService> instanceSupplier = InstanceSupplier.using(CredenzialiService::new);
    instanceSupplier = instanceSupplier.andThen(CredenzialiService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
