package io.github.suppie.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum ModuleClass {
  GENERAL_ROUNDS("General Rounds"),
  SPECIAL_ROUNDS("Special Rounds"),
  IMPACT_ROUNDS("Impact Rounds"),
  HIGH_POWER_ROUNDS("High-Power Rounds"),
  DESCENDANT("Descendant");

  private final String text;

  ModuleClass(String text) {
    this.text = text;
  }

  @JsonCreator
  public static ModuleClass fromString(String text) {
    for (ModuleClass moduleClass : ModuleClass.values()) {
      if (moduleClass.name().equalsIgnoreCase(text) || moduleClass.text.equalsIgnoreCase(text)) {
        return moduleClass;
      }
    }

    return null;
    //    throw new IllegalStateException("Module class '%s' not found");
  }

  @Override
  @JsonValue
  public String toString() {
    return text;
  }
}
