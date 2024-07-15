package io.github.suppie.api.rest.models;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Objects;

@Serdeable
public class ModulesResponse {
  private int page;
  private int size;
  private int total;
  private List<Module> modules;

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

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<Module> getModules() {
    return modules;
  }

  public void setModules(List<Module> modules) {
    this.modules = modules;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ModulesResponse that = (ModulesResponse) o;
    return page == that.page
        && size == that.size
        && total == that.total
        && Objects.equals(modules, that.modules);
  }

  @Override
  public int hashCode() {
    int result = page;
    result = 31 * result + size;
    result = 31 * result + total;
    result = 31 * result + Objects.hashCode(modules);
    return result;
  }
}
