package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OrdineService}.
 */
@Generated
public class OrdineService__BeanDefinitions {
  /**
   * Get the bean definition for 'ordineService'.
   */
  public static BeanDefinition getOrdineServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OrdineService.class);
    InstanceSupplier<OrdineService> instanceSupplier = InstanceSupplier.using(OrdineService::new);
    instanceSupplier = instanceSupplier.andThen(OrdineService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
