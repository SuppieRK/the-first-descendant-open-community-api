package io.github.suppie.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public enum ModuleSocket {
  CERULEAN("Cerulean"),
  ALMANDINE("Almandine"),
  MALACHITE("Malachite"),
  XANTIC("Xantic"),
  RUTILE("Rutile");

  private final String text;

  ModuleSocket(String text) {
    this.text = text;
  }

  @JsonCreator
  public static ModuleSocket fromString(String text) {
    for (ModuleSocket socket : ModuleSocket.values()) {
      if (socket.name().equalsIgnoreCase(text) || socket.text.equalsIgnoreCase(text)) {
        return socket;
      }
    }

    return null;
    //    throw new IllegalStateException("Module socket '%s' not found");
  }

  @Override
  @JsonValue
  public String toString() {
    return text;
  }
}
