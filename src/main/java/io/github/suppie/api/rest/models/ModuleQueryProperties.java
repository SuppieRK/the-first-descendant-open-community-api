package io.github.suppie.api.rest.models;

import io.github.suppie.constants.ModuleClass;
import io.github.suppie.constants.ModuleSocket;
import io.github.suppie.constants.ModuleTier;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Objects;

@Serdeable
public class ModuleQueryProperties {
  private String nameContains;
  private String descriptionContains;
  private String typeContains;
  private ModuleTier moduleTier;
  private ModuleSocket moduleSocket;
  private ModuleClass moduleClass;
  private String effectContains;
  private int page = 1;
  private int size = 20;

  public String getNameContains() {
    return nameContains;
  }

  public void setNameContains(String nameContains) {
    this.nameContains = nameContains;
  }

  public String getDescriptionContains() {
    return descriptionContains;
  }

  public void setDescriptionContains(String descriptionContains) {
    this.descriptionContains = descriptionContains;
  }

  public String getTypeContains() {
    return typeContains;
  }

  public void setTypeContains(String typeContains) {
    this.typeContains = typeContains;
  }

  public ModuleTier getModuleTier() {
    return moduleTier;
  }

  public void setModuleTier(ModuleTier moduleTier) {
    this.moduleTier = moduleTier;
  }

  public ModuleSocket getModuleSocket() {
    return moduleSocket;
  }

  public void setModuleSocket(ModuleSocket moduleSocket) {
    this.moduleSocket = moduleSocket;
  }

  public ModuleClass getModuleClass() {
    return moduleClass;
  }

  public void setModuleClass(ModuleClass moduleClass) {
    this.moduleClass = moduleClass;
  }

  public String getEffectContains() {
    return effectContains;
  }

  public void setEffectContains(String effectContains) {
    this.effectContains = effectContains;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ModuleQueryProperties that = (ModuleQueryProperties) o;
    return page == that.page
        && size == that.size
        && Objects.equals(nameContains, that.nameContains)
        && Objects.equals(descriptionContains, that.descriptionContains)
        && Objects.equals(typeContains, that.typeContains)
        && moduleTier == that.moduleTier
        && moduleSocket == that.moduleSocket
        && moduleClass == that.moduleClass
        && Objects.equals(effectContains, that.effectContains);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(nameContains);
    result = 31 * result + Objects.hashCode(descriptionContains);
    result = 31 * result + Objects.hashCode(typeContains);
    result = 31 * result + Objects.hashCode(moduleTier);
    result = 31 * result + Objects.hashCode(moduleSocket);
    result = 31 * result + Objects.hashCode(moduleClass);
    result = 31 * result + Objects.hashCode(effectContains);
    result = 31 * result + page;
    result = 31 * result + size;
    return result;
  }
}
