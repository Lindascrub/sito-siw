package org.springframework.boot.security.oauth2.client.autoconfigure;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

/**
 * Bean definitions for {@link OAuth2ClientConfigurations}.
 */
@Generated
public class OAuth2ClientConfigurations__BeanDefinitions {
  /**
   * Bean definitions for {@link OAuth2ClientConfigurations.ClientRegistrationRepositoryConfiguration}.
   */
  @Generated
  public static class ClientRegistrationRepositoryConfiguration {
    /**
     * Get the bean definition for 'clientRegistrationRepositoryConfiguration'.
     */
    public static BeanDefinition getClientRegistrationRepositoryConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2ClientConfigurations.ClientRegistrationRepositoryConfiguration.class);
      beanDefinition.setInstanceSupplier(OAuth2ClientConfigurations.ClientRegistrationRepositoryConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'clientRegistrationRepository'.
     */
    private static BeanInstanceSupplier<InMemoryClientRegistrationRepository> getClientRegistrationRepositoryInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<InMemoryClientRegistrationRepository>forFactoryMethod(OAuth2ClientConfigurations.ClientRegistrationRepositoryConfiguration.class, "clientRegistrationRepository", OAuth2ClientProperties.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientConfigurations$ClientRegistrationRepositoryConfiguration", OAuth2ClientConfigurations.ClientRegistrationRepositoryConfiguration.class).clientRegistrationRepository(args.get(0)));
    }

    /**
     * Get the bean definition for 'clientRegistrationRepository'.
     */
    public static BeanDefinition getClientRegistrationRepositoryBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(InMemoryClientRegistrationRepository.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientConfigurations$ClientRegistrationRepositoryConfiguration");
      beanDefinition.setInstanceSupplier(getClientRegistrationRepositoryInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link OAuth2ClientConfigurations.OAuth2AuthorizedClientServiceConfiguration}.
   */
  @Generated
  public static class OAuth2AuthorizedClientServiceConfiguration {
    /**
     * Get the bean definition for 'oAuth2AuthorizedClientServiceConfiguration'.
     */
    public static BeanDefinition getOAuthAuthorizedClientServiceConfigurationBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2ClientConfigurations.OAuth2AuthorizedClientServiceConfiguration.class);
      beanDefinition.setInstanceSupplier(OAuth2ClientConfigurations.OAuth2AuthorizedClientServiceConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'authorizedClientService'.
     */
    private static BeanInstanceSupplier<OAuth2AuthorizedClientService> getAuthorizedClientServiceInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<OAuth2AuthorizedClientService>forFactoryMethod(OAuth2ClientConfigurations.OAuth2AuthorizedClientServiceConfiguration.class, "authorizedClientService", ClientRegistrationRepository.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientConfigurations$OAuth2AuthorizedClientServiceConfiguration", OAuth2ClientConfigurations.OAuth2AuthorizedClientServiceConfiguration.class).authorizedClientService(args.get(0)));
    }

    /**
     * Get the bean definition for 'authorizedClientService'.
     */
    public static BeanDefinition getAuthorizedClientServiceBeanDefinition() {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(OAuth2AuthorizedClientService.class);
      beanDefinition.setFactoryBeanName("org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientConfigurations$OAuth2AuthorizedClientServiceConfiguration");
      beanDefinition.setInstanceSupplier(getAuthorizedClientServiceInstanceSupplier());
      return beanDefinition;
    }
  }
}
