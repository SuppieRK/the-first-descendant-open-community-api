package io.github.suppie.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum ModuleTier {
  NORMAL("Normal", 1),
  RARE("Rare", 2),
  ULTIMATE("Ultimate", 3),
  TRANSCENDENT("Transcendent", 5);

  private final String text;
  private final int costMultiplier;

  ModuleTier(String text, int costMultiplier) {
    this.text = text;
    this.costMultiplier = costMultiplier;
  }

  @JsonCreator
  public static ModuleTier fromString(String text) {
    for (ModuleTier tier : ModuleTier.values()) {
      if (tier.name().equalsIgnoreCase(text) || tier.text.equalsIgnoreCase(text)) {
        return tier;
      }
    }

    return null;
    //    throw new IllegalStateException("Module tier '%s' not found");
  }

  public int getCostMultiplier() {
    return costMultiplier;
  }

  @Override
  @JsonValue
  public String toString() {
    return text;
  }
}
