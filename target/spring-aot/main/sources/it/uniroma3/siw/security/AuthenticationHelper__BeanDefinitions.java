package it.uniroma3.siw.security;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AuthenticationHelper}.
 */
@Generated
public class AuthenticationHelper__BeanDefinitions {
  /**
   * Get the bean definition for 'authenticationHelper'.
   */
  public static BeanDefinition getAuthenticationHelperBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AuthenticationHelper.class);
    InstanceSupplier<AuthenticationHelper> instanceSupplier = InstanceSupplier.using(AuthenticationHelper::new);
    instanceSupplier = instanceSupplier.andThen(AuthenticationHelper__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
