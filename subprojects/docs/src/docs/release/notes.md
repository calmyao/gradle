## New and noteworthy

Here are the new features introduced in this Gradle release.

### Support version ranges in parent elements

Gradle now suppoprts version range in parent elements of POM, which was introduced by [Maven 3.2.2](https://maven.apache.org/docs/3.2.2/release-notes.html):

> Parent elements can now use bounded ranges in the version specification. You can now consistently use ranges for all intra-project dependencies, of which parents are a special case but still considered a dependency of projects that inherit from them. The following is now permissible:

```
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.apache</groupId>
    <artifactId>apache</artifactId>
    <version>[3.0,4.0)</version>
  </parent>
  <groupId>org.apache.maven.its.mng2199</groupId>
  <artifactId>valid</artifactId>
  <version>1</version>
  <packaging>pom</packaging>
</project>
```


<!--
IMPORTANT: if this is a patch release, ensure that a prominent link is included in the foreword to all releases of the same minor stream.
Add-->

### Support for Maven optional dependencies

Gradle will now [take Maven optional dependencies into account](https://github.com/gradle/gradle/pull/3129) during dependency resolution. 
Before, optional dependencies were not included in the dependency graph.
As of Gradle 4.4, optional dependencies will participate in dependency resolution as soon as another dependency on the same module is found in the graph.
For example, if a transitive dependency on `foo:bar:1.1` is optional, but another path in the dependency graph brings `foo:bar:1.0` (not optional), then Gradle will resolve to `foo:bar:1.1`.
Previous releases would resolve to `foo:bar:1.0`. However, if no "hard" dependency is found on the optional module, then it will **not** be included, as previous Gradle versions did.

### Dependencies of published components can be adjusted

[Component metadata rules](userguide/component_metadata_rules.html) now supports adding and removing dependencies in case the published metadata is flawed or too strict. This can be used for example to adjust version ranges of dependencies.
TODO extend this section when further features (e.g. updating an existing dependency) are added.

### Parametrized tooling model builders.

The Tooling API now allows model builders to accept parameters from the tooling client. This is useful when there are multiple possible mappings from the Gradle project to the tooling model and the decision depends on some user-provided value.
Android Studio for instance will use this API to request just the dependencies for the variant that the user currently selected in the UI. This will greatly reduce synchronization times.

For more information see the [documentation](javadoc/org/gradle/tooling/provider/model/ParametrizedToolingModelBuilder.html) of the new API.

## Promoted features

Promoted features are features that were incubating in previous versions of Gradle but are now supported and subject to backwards compatibility.
See the User guide section on the “[Feature Lifecycle](userguide/feature_lifecycle.html)” for more information.

The following are the features that have been promoted in this Gradle release.

<!--
### Example promoted
-->

## Fixed issues

## Deprecations

Features that have become superseded or irrelevant due to the natural evolution of Gradle become *deprecated*, and scheduled to be removed
in the next major Gradle version (Gradle 5.0). See the User guide section on the “[Feature Lifecycle](userguide/feature_lifecycle.html)” for more information.

The following are the newly deprecated items in this Gradle release. If you have concerns about a deprecation, please raise it via the [Gradle Forums](https://discuss.gradle.org).

<!--
### Example deprecation
-->

## Potential breaking changes

### Maven optional dependencies may lead to different dependency resolution result

Supporting optional dependencies means that depending on the shape of your dependency graph, you may now have a different dependency resolution result after upgrading to Gradle 4.4.
Should you see any problem, [build scans](https://scans.gradle.com) can help you debug those.

## External contributions

We would like to thank the following community members for making contributions to this release of Gradle.

<!--
 - [Some person](https://github.com/some-person) - fixed some issue (gradle/gradle#1234)
-->

- [Lucas Smaira](https://github.com/lsmaira) - Support parametrized Tooling model builders (gradle/gradle#2729)
- [Kyle Moore](https://github.com/DPUkyle) - Updated Gosu plugin to fix API breakage (gradle/gradle#3115)
- [Jaci Brunning](https://github.com/JacisNonsense) - Support for overriding target platforms on Gcc toolchains (gradle/gradle#3124)

We love getting contributions from the Gradle community. For information on contributing, please see [gradle.org/contribute](https://gradle.org/contribute).

## Known issues

Known issues are problems that were discovered post release that are directly related to changes made in this release.
