package io.github.suppie.api.rest;

import io.github.suppie.api.rest.models.Module;
import io.github.suppie.api.rest.models.ModuleQueryProperties;
import io.github.suppie.api.rest.models.ModulesResponse;
import io.github.suppie.services.ModuleService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/tfd")
public class RestController {
  private final ModuleService moduleService;

  public RestController(ModuleService moduleService) {
    this.moduleService = moduleService;
  }

  @Get(uri = "/modules/{id}", produces = "application/json")
  public Module moduleById(int id) {
    return moduleService.findById(id);
  }

  @Get(uri = "/modules{?properties*}", produces = "application/json")
  public ModulesResponse modules(ModuleQueryProperties properties) {
    return moduleService.find(properties);
  }
}
