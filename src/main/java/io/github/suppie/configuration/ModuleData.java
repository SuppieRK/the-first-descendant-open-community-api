package io.github.suppie.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import io.github.suppie.constants.ModuleClass;
import io.github.suppie.constants.ModuleSocket;
import io.github.suppie.constants.ModuleTier;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.core.order.Ordered;
import io.micronaut.serde.annotation.Serdeable;
import java.util.*;
import java.util.stream.Collectors;

/** Internal short representation of the module from the game. */
@Serdeable
@EachProperty(value = "modules", list = true)
public class ModuleData {
  // CQEngine attributes for lookups
  public static final Attribute<ModuleData, Integer> ID_ATTRIBUTE =
      new SimpleAttribute<>("moduleId") {
        @Override
        public Integer getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getId();
        }
      };

  public static final Attribute<ModuleData, String> NAME_ATTRIBUTE =
      new SimpleAttribute<>("moduleName") {
        @Override
        public String getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getName().toLowerCase();
        }
      };

  public static final Attribute<ModuleData, String> DESCRIPTION_ATTRIBUTE =
      new SimpleAttribute<>("moduleDescription") {
        @Override
        public String getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getDescriptionPattern().toLowerCase();
        }
      };

  public static final Attribute<ModuleData, String> TYPE_ATTRIBUTE =
      new SimpleAttribute<>("moduleType") {
        @Override
        public String getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getType().toLowerCase();
        }
      };

  public static final Attribute<ModuleData, ModuleTier> TIER_ATTRIBUTE =
      new SimpleAttribute<>("moduleTier") {
        @Override
        public ModuleTier getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getTier();
        }
      };

  public static final Attribute<ModuleData, ModuleSocket> SOCKET_ATTRIBUTE =
      new SimpleAttribute<>("moduleSocket") {
        @Override
        public ModuleSocket getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getSocketType();
        }
      };

  public static final Attribute<ModuleData, ModuleClass> CLASS_ATTRIBUTE =
      new SimpleAttribute<>("moduleClass") {
        @Override
        public ModuleClass getValue(ModuleData m, QueryOptions queryOptions) {
          return m.getModuleClass();
        }
      };

  public static final Attribute<ModuleData, String> EFFECT_ATTRIBUTE =
      new MultiValueAttribute<>("moduleEffect") {
        @Override
        public Iterable<String> getValues(ModuleData m, QueryOptions queryOptions) {
          return m.getEnhancements().iterator().next().getEffects().stream()
              .map(EnhancementEffect::getName)
              .map(String::toLowerCase)
              .collect(Collectors.toSet());
        }
      };

  // Class properties
  private int id;
  private String name;
  private String descriptionPattern;
  private ModuleClass moduleClass;
  private ModuleTier tier;
  private String type;
  private ModuleSocket socketType;
  private int initialCapacityCost;
  private int capacityCostLevelStep;

  private final SortedSet<Enhancement> enhancements;

  public ModuleData() {
    this.enhancements = new TreeSet<>(Comparator.comparingInt(Enhancement::getOrder));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescriptionPattern() {
    return descriptionPattern;
  }

  public void setDescriptionPattern(String descriptionPattern) {
    this.descriptionPattern = descriptionPattern;
  }

  public ModuleClass getModuleClass() {
    return moduleClass;
  }

  public void setModuleClass(ModuleClass moduleClass) {
    this.moduleClass = moduleClass;
  }

  public ModuleTier getTier() {
    return tier;
  }

  public void setTier(ModuleTier tier) {
    this.tier = tier;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ModuleSocket getSocketType() {
    return socketType;
  }

  public void setSocketType(ModuleSocket socketType) {
    this.socketType = socketType;
  }

  public int getInitialCapacityCost() {
    return initialCapacityCost;
  }

  public void setInitialCapacityCost(int initialCapacityCost) {
    this.initialCapacityCost = initialCapacityCost;
  }

  public int getCapacityCostLevelStep() {
    return capacityCostLevelStep;
  }

  public void setCapacityCostLevelStep(int capacityCostLevelStep) {
    this.capacityCostLevelStep = capacityCostLevelStep;
  }

  public SortedSet<Enhancement> getEnhancements() {
    return enhancements;
  }

  public void setEnhancements(List<Enhancement> enhancements) {
    this.enhancements.clear();
    this.enhancements.addAll(enhancements);
  }

  @Serdeable
  @EachProperty(value = "enhancements", list = true)
  public static class Enhancement implements Ordered {
    private int level;
    private String description;
    private List<EnhancementEffect> effects;

    public Enhancement() {
      this.effects = new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public int getOrder() {
      return getLevel();
    }

    public int getLevel() {
      return level;
    }

    public void setLevel(int level) {
      this.level = level;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public List<EnhancementEffect> getEffects() {
      return effects;
    }

    public void setEffects(List<EnhancementEffect> effects) {
      this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Enhancement that = (Enhancement) o;
      return level == that.level && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
      int result = level;
      result = 31 * result + Objects.hashCode(description);
      return result;
    }
  }

  @Serdeable
  @EachProperty(value = "effects", list = true)
  public static class EnhancementEffect {
    private String name;
    private double value;
    private boolean percentage;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public double getValue() {
      return value;
    }

    public void setValue(double value) {
      this.value = value;
    }

    public boolean isPercentage() {
      return percentage;
    }

    public void setPercentage(boolean percentage) {
      this.percentage = percentage;
    }
  }
}
