package io.github.suppie.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Collection;
import org.junit.jupiter.api.Test;

@MicronautTest
class ModuleDataTest {
  @Inject ApplicationContext context;

  @Test
  void verifyThatConfigurationLoadsFromYaml() {
    Collection<ModuleData> beans = context.getBeansOfType(ModuleData.class);

    assertNotNull(beans);
    assertFalse(beans.isEmpty());
  }
}
