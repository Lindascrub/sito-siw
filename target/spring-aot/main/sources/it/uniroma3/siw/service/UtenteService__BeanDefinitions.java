package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link UtenteService}.
 */
@Generated
public class UtenteService__BeanDefinitions {
  /**
   * Get the bean definition for 'utenteService'.
   */
  public static BeanDefinition getUtenteServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(UtenteService.class);
    InstanceSupplier<UtenteService> instanceSupplier = InstanceSupplier.using(UtenteService::new);
    instanceSupplier = instanceSupplier.andThen(UtenteService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
