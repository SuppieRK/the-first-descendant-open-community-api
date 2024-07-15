package io.github.suppie.services;

import static com.googlecode.cqengine.query.QueryFactory.*;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.suffix.SuffixTreeIndex;
import com.googlecode.cqengine.index.unique.UniqueIndex;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.resultset.ResultSet;
import com.googlecode.cqengine.resultset.common.NoSuchObjectException;
import com.googlecode.cqengine.resultset.common.NonUniqueObjectException;
import io.github.suppie.configuration.ModuleData;
import io.github.suppie.api.rest.models.Module;
import io.github.suppie.api.rest.models.ModuleQueryProperties;
import io.github.suppie.api.rest.models.ModulesResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Singleton;
import java.util.Comparator;
import java.util.List;

/** Allows for fast search within modules in game. */
@Singleton
public class ModuleService {
  private final IndexedCollection<ModuleData> modules;

  public ModuleService(List<ModuleData> moduleData) {
    this.modules = new ConcurrentIndexedCollection<>();

    this.modules.addIndex(UniqueIndex.onAttribute(ModuleData.ID_ATTRIBUTE));
    this.modules.addIndex(SuffixTreeIndex.onAttribute(ModuleData.NAME_ATTRIBUTE));
    this.modules.addIndex(SuffixTreeIndex.onAttribute(ModuleData.DESCRIPTION_ATTRIBUTE));
    this.modules.addIndex(SuffixTreeIndex.onAttribute(ModuleData.TYPE_ATTRIBUTE));
    this.modules.addIndex(HashIndex.onAttribute(ModuleData.TIER_ATTRIBUTE));
    this.modules.addIndex(HashIndex.onAttribute(ModuleData.SOCKET_ATTRIBUTE));
    this.modules.addIndex(HashIndex.onAttribute(ModuleData.CLASS_ATTRIBUTE));
    this.modules.addIndex(SuffixTreeIndex.onAttribute(ModuleData.EFFECT_ATTRIBUTE));

    this.modules.addAll(moduleData);
  }

  /**
   * Find module by ID.
   *
   * @param id to search
   * @return module information
   */
  public Module findById(int id) {
    try (ResultSet<ModuleData> resultSet = modules.retrieve(equal(ModuleData.ID_ATTRIBUTE, id))) {
      return new Module(resultSet.uniqueResult());
    } catch (NoSuchObjectException e) {
      throw new HttpStatusException(HttpStatus.NOT_FOUND, "No module found with ID " + id);
    } catch (NonUniqueObjectException e) {
      throw new HttpStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "There are multiple modules with ID " + id + " found");
    }
  }

  /**
   * Find modules based on multiple criteria.
   *
   * @param properties to use for searching
   * @return a list of applicable modules
   */
  public ModulesResponse find(ModuleQueryProperties properties) {
    Query<ModuleData> query = null;

    if (properties.getNameContains() != null && !properties.getNameContains().isBlank()) {
      if (query == null) {
        query = contains(ModuleData.NAME_ATTRIBUTE, properties.getNameContains().toLowerCase());
      } else {
        query =
            and(
                query,
                contains(ModuleData.NAME_ATTRIBUTE, properties.getNameContains().toLowerCase()));
      }
    }

    if (properties.getDescriptionContains() != null
        && !properties.getDescriptionContains().isBlank()) {
      if (query == null) {
        query =
            contains(
                ModuleData.DESCRIPTION_ATTRIBUTE,
                properties.getDescriptionContains().toLowerCase());
      } else {
        query =
            and(
                query,
                contains(
                    ModuleData.DESCRIPTION_ATTRIBUTE,
                    properties.getDescriptionContains().toLowerCase()));
      }
    }

    if (properties.getTypeContains() != null && !properties.getTypeContains().isBlank()) {
      if (query == null) {
        query = contains(ModuleData.TYPE_ATTRIBUTE, properties.getTypeContains().toLowerCase());
      } else {
        query =
            and(
                query,
                contains(ModuleData.TYPE_ATTRIBUTE, properties.getTypeContains().toLowerCase()));
      }
    }

    if (properties.getModuleTier() != null) {
      if (query == null) {
        query = equal(ModuleData.TIER_ATTRIBUTE, properties.getModuleTier());
      } else {
        query = and(query, equal(ModuleData.TIER_ATTRIBUTE, properties.getModuleTier()));
      }
    }

    if (properties.getModuleSocket() != null) {
      if (query == null) {
        query = equal(ModuleData.SOCKET_ATTRIBUTE, properties.getModuleSocket());
      } else {
        query = and(query, equal(ModuleData.SOCKET_ATTRIBUTE, properties.getModuleSocket()));
      }
    }

    if (properties.getModuleClass() != null) {
      if (query == null) {
        query = equal(ModuleData.CLASS_ATTRIBUTE, properties.getModuleClass());
      } else {
        query = and(query, equal(ModuleData.CLASS_ATTRIBUTE, properties.getModuleClass()));
      }
    }

    if (properties.getEffectContains() != null && !properties.getEffectContains().isBlank()) {
      if (query == null) {
        query = contains(ModuleData.EFFECT_ATTRIBUTE, properties.getEffectContains().toLowerCase());
      } else {
        query =
            and(
                query,
                contains(
                    ModuleData.EFFECT_ATTRIBUTE, properties.getEffectContains().toLowerCase()));
      }
    }

    if (query == null) {
      List<Module> modulesR =
          modules.stream()
              .sorted(Comparator.comparing(ModuleData::getName))
              .skip((long) properties.getSize() * (properties.getPage() - 1))
              .limit(properties.getSize())
              .map(Module::new)
              .toList();

      ModulesResponse response = new ModulesResponse();
      response.setPage(properties.getPage());
      response.setSize(properties.getSize());
      response.setTotal(modules.size());
      response.setModules(modulesR);
      return response;
    } else {
      try (ResultSet<ModuleData> resultSet = modules.retrieve(query)) {
        List<Module> modulesR =
            resultSet.stream()
                .sorted(Comparator.comparing(ModuleData::getName))
                .skip((long) properties.getSize() * (properties.getPage() - 1))
                .limit(properties.getSize())
                .map(Module::new)
                .toList();

        ModulesResponse response = new ModulesResponse();
        response.setPage(properties.getPage());
        response.setSize(properties.getSize());
        response.setTotal(resultSet.size());
        response.setModules(modulesR);
        return response;
      }
    }
  }
}
