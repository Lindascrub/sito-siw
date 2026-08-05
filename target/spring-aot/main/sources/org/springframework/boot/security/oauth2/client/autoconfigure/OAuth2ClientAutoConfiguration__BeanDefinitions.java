package org.springframework.boot.security.oauth2.client.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2ClientAutoConfiguration}.
 */
@Generated
public class OAuth2ClientAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2ClientAutoConfiguration'.
   */
  public static BeanDefinition getOAuthClientAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2ClientAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(OAuth2ClientAutoConfiguration::new);
    return beanDefinition;
  }
}
