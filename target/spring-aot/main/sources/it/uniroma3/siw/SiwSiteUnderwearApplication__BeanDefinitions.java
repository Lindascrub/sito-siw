package it.uniroma3.siw;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SiwSiteUnderwearApplication}.
 */
@Generated
public class SiwSiteUnderwearApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'siwSiteUnderwearApplication'.
   */
  public static BeanDefinition getSiwSiteUnderwearApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SiwSiteUnderwearApplication.class);
    beanDefinition.setInstanceSupplier(SiwSiteUnderwearApplication::new);
    return beanDefinition;
  }
}
