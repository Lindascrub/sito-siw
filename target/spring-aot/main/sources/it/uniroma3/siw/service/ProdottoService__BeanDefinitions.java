package it.uniroma3.siw.service;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProdottoService}.
 */
@Generated
public class ProdottoService__BeanDefinitions {
  /**
   * Get the bean definition for 'prodottoService'.
   */
  public static BeanDefinition getProdottoServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ProdottoService.class);
    InstanceSupplier<ProdottoService> instanceSupplier = InstanceSupplier.using(ProdottoService::new);
    instanceSupplier = instanceSupplier.andThen(ProdottoService__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
