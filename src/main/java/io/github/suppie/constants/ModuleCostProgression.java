package io.github.suppie.constants;

import java.util.Map;

public final class ModuleCostProgression {
  private static final Map<Integer, Integer> KUIPER_PROGRESSION =
      Map.ofEntries(
          Map.entry(0, 0),
          Map.entry(1, 300),
          Map.entry(2, 600),
          Map.entry(3, 1100),
          Map.entry(4, 2000),
          Map.entry(5, 3400),
          Map.entry(6, 5700),
          Map.entry(7, 9500),
          Map.entry(8, 15500),
          Map.entry(9, 25000),
          Map.entry(10, 40000));

  public static int getKuiperCost(ModuleTier tier, int moduleLevel) {
    if (KUIPER_PROGRESSION.containsKey(moduleLevel)) {
      return KUIPER_PROGRESSION.get(moduleLevel) * tier.getCostMultiplier();
    } else {
      throw new IllegalArgumentException("Invalid module level: " + moduleLevel);
    }
  }

  public static int getGoldCost(ModuleTier tier, int moduleLevel) {
    return getKuiperCost(tier, moduleLevel) * 10;
  }
}
