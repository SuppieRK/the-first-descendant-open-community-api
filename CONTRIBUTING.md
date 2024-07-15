# Contributing

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change.

Please note we have a code of conduct, please follow it in all your interactions with the project.

## Pull Request Process

1. Ensure any installation or build dependencies are removed before the end of the layer when doing a
   build.
2. Update the README.md with details of changes to the interface, this includes new environment
   variables, exposed ports, useful file locations and container parameters.
3. Increase the version numbers in any example files and the README.md to the new version that this
   Pull Request would represent. The versioning scheme we use is [SemVer](http://semver.org/) where first
   three numbers represent Spring version in use and the last fourth number represents library code version.
5. You may merge the Pull Request in once you have the sign-off of at least one other developer.

## Reference Documentation

For further reference, please consider the following sections:

### Build tools

The command typically used to build the project is:

```shell
./gradlew clean spotlessApply build
```

* [Official Gradle documentation](https://docs.gradle.org)

## Micronaut 4.5.0 Documentation

- [User Guide](https://docs.micronaut.io/4.5.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.5.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.5.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature reactor documentation

- [Micronaut Reactor documentation](https://micronaut-projects.github.io/micronaut-reactor/snapshot/guide/index.html)

## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)

## Feature swagger-ui documentation

- [Micronaut Swagger UI documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://swagger.io/tools/swagger-ui/](https://swagger.io/tools/swagger-ui/)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Building and launching Docker image locally

- To launch Docker image from Docker Hub, use:
```shell
docker run -d --name the-first-descendant-open-community-api -p 8080:8080 suppie/the-first-descendant-open-community-api:latest
```

- To build Docker image locally, use:
```shell
./gradlew clean dockerBuildNative
```

- Visit [localhost page](http://localhost:8080/swagger-ui/index.html) to observe the API
