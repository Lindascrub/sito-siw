package org.springframework.boot.security.oauth2.client.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link OAuth2ClientProperties}.
 */
@Generated
public class OAuth2ClientProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2ClientProperties'.
   */
  public static BeanDefinition getOAuthClientPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2ClientProperties.class);
    beanDefinition.setInstanceSupplier(OAuth2ClientProperties::new);
    return beanDefinition;
  }
}
