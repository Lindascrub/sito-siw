package org.springframework.boot.security.oauth2.client.autoconfigure.servlet;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

/**
 * Bean definitions for {@link OAuth2ClientWebSecurityAutoConfiguration}.
 */
@Generated
public class OAuth2ClientWebSecurityAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'oAuth2ClientWebSecurityAutoConfiguration'.
   */
  public static BeanDefinition getOAuthClientWebSecurityAutoConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2ClientWebSecurityAutoConfiguration.class);
    beanDefinition.setInstanceSupplier(OAuth2ClientWebSecurityAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'authorizedClientRepository'.
   */
  private static BeanInstanceSupplier<OAuth2AuthorizedClientRepository> getAuthorizedClientRepositoryInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<OAuth2AuthorizedClientRepository>forFactoryMethod(OAuth2ClientWebSecurityAutoConfiguration.class, "authorizedClientRepository", OAuth2AuthorizedClientService.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.security.oauth2.client.autoconfigure.servlet.OAuth2ClientWebSecurityAutoConfiguration", OAuth2ClientWebSecurityAutoConfiguration.class).authorizedClientRepository(args.get(0)));
  }

  /**
   * Get the bean definition for 'authorizedClientRepository'.
   */
  public static BeanDefinition getAuthorizedClientRepositoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizedClientRepository.class);
    beanDefinition.setFactoryBeanName("org.springframework.boot.security.oauth2.client.autoconfigure.servlet.OAuth2ClientWebSecurityAutoConfiguration");
    beanDefinition.setInstanceSupplier(getAuthorizedClientRepositoryInstanceSupplier());
    return beanDefinition;
  }
}
