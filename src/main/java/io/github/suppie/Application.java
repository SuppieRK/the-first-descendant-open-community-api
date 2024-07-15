package io.github.suppie;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "The First Descendant Open Community API", version = "0.0.1-SNAPSHOT"))
public class Application {
  public static void main(String[] args) {
    Micronaut.build(args).eagerInitSingletons(true).banner(false).start();
  }
}
