package io.github.suppie.api.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.suppie.configuration.ModuleData;
import io.github.suppie.constants.ModuleClass;
import io.github.suppie.constants.ModuleCostProgression;
import io.github.suppie.constants.ModuleSocket;
import io.github.suppie.constants.ModuleTier;
import io.micronaut.core.order.Ordered;
import io.micronaut.serde.annotation.Serdeable;
import java.util.*;

@Serdeable
public class Module {
  private final int id;
  private final String name;
  private final ModuleClass moduleClass;
  private final ModuleTier tier;
  private final String type;
  private final ModuleSocket socketType;
  private final int levels;
  private final SortedSet<Enhancement> enhancements;

  public Module(ModuleData data) {
    this.id = data.getId();
    this.name = data.getName();
    this.moduleClass = data.getModuleClass();
    this.tier = data.getTier();
    this.type = data.getType();
    this.socketType = data.getSocketType();
    this.levels = data.getEnhancements().size() - 1; // Excluding base level

    this.enhancements = new TreeSet<>(Comparator.comparingInt(Enhancement::getOrder));

    int runningKuiperCost = 0;
    int runningGoldCost = 0;

    for (ModuleData.Enhancement enhancement : data.getEnhancements()) {
      int kuiperCost = ModuleCostProgression.getKuiperCost(data.getTier(), enhancement.getLevel());
      int goldCost = ModuleCostProgression.getGoldCost(data.getTier(), enhancement.getLevel());

      runningKuiperCost += kuiperCost;
      runningGoldCost += goldCost;

      String description = data.getDescriptionPattern();
      for (ModuleData.EnhancementEffect effect : enhancement.getEffects()) {
        description =
            description.replaceAll(
                "\\{" + effect.getName() + "\\}",
                ("Duration".equalsIgnoreCase(effect.getName())
                        ? ""
                        : (effect.getValue() > 0 ? "+" : ""))
                    + effect.getValue()
                    + (effect.isPercentage() ? "%" : ""));
      }

      this.enhancements.add(
          new Enhancement(
              enhancement.getLevel(),
              description,
              data.getInitialCapacityCost()
                  + enhancement.getLevel() * data.getCapacityCostLevelStep(),
              kuiperCost,
              goldCost,
              runningKuiperCost,
              runningGoldCost));
    }
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ModuleClass getModuleClass() {
    return moduleClass;
  }

  public ModuleTier getTier() {
    return tier;
  }

  public String getType() {
    return type;
  }

  public ModuleSocket getSocketType() {
    return socketType;
  }

  public int getLevels() {
    return levels;
  }

  public SortedSet<Enhancement> getEnhancements() {
    return enhancements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Module module = (Module) o;
    return id == module.id
        && levels == module.levels
        && Objects.equals(name, module.name)
        && moduleClass == module.moduleClass
        && tier == module.tier
        && Objects.equals(type, module.type)
        && socketType == module.socketType
        && enhancements.equals(module.enhancements);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(moduleClass);
    result = 31 * result + Objects.hashCode(tier);
    result = 31 * result + Objects.hashCode(type);
    result = 31 * result + Objects.hashCode(socketType);
    result = 31 * result + levels;
    result = 31 * result + enhancements.hashCode();
    return result;
  }

  @Serdeable
  public static class Enhancement implements Ordered {
    private final int level;
    private final String description;
    private final int capacityCost;
    private final int kuiperCost;
    private final int goldCost;
    private final int runningKuiperCost;
    private final int runningGoldCost;

    public Enhancement(
        int level,
        String description,
        int capacityCost,
        int kuiperCost,
        int goldCost,
        int runningKuiperCost,
        int runningGoldCost) {
      this.level = level;
      this.description = description;
      this.capacityCost = capacityCost;
      this.kuiperCost = kuiperCost;
      this.goldCost = goldCost;
      this.runningKuiperCost = runningKuiperCost;
      this.runningGoldCost = runningGoldCost;
    }

    @Override
    @JsonIgnore
    public int getOrder() {
      return getLevel();
    }

    public int getLevel() {
      return level;
    }

    public String getDescription() {
      return description;
    }

    public int getCapacityCost() {
      return capacityCost;
    }

    public int getKuiperCost() {
      return kuiperCost;
    }

    public int getGoldCost() {
      return goldCost;
    }

    public int getRunningKuiperCost() {
      return runningKuiperCost;
    }

    public int getRunningGoldCost() {
      return runningGoldCost;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Enhancement that = (Enhancement) o;
      return level == that.level
          && capacityCost == that.capacityCost
          && kuiperCost == that.kuiperCost
          && goldCost == that.goldCost
          && runningKuiperCost == that.runningKuiperCost
          && runningGoldCost == that.runningGoldCost
          && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
      int result = level;
      result = 31 * result + Objects.hashCode(description);
      result = 31 * result + capacityCost;
      result = 31 * result + kuiperCost;
      result = 31 * result + goldCost;
      result = 31 * result + runningKuiperCost;
      result = 31 * result + runningGoldCost;
      return result;
    }
  }
}
