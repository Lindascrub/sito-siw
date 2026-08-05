package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link RecensioneService}.
 */
@Generated
public class RecensioneService__BeanDefinitions {
  /**
   * Get the bean definition for 'recensioneService'.
   */
  public static BeanDefinition getRecensioneServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RecensioneService.class);
    InstanceSupplier<RecensioneService> instanceSupplier = InstanceSupplier.using(RecensioneService::new);
    instanceSupplier = instanceSupplier.andThen(RecensioneService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
