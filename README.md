# BlueMap Ars Énergistique Add-on

A Java 21 BlueMap 5.23 feature-backport add-on for the exact
`arseng-2.1.1-beta-mc1.21.1` profile in All the Mons `1.2.0` / Minecraft
`1.21.1`.

Status: owner-accepted `0.1.0-alpha.2` release candidate. The unchanged three
cable parts and five Source Storage Cell models target only BlueMap
feature-backport commit `7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` and API
commit `285c9a60eff3ac2b0cab308ce1058d1565be0971`. Direct Ars
Énergistique blocks retain their correct stock resource models.

## Build

Clone with `--recurse-submodules`, or initialize an existing checkout with:

```bash
git submodule update --init --recursive -- \
  tooling/bluemap-addon-toolkit modules/bluemap-addon-adapter-api
```

The settings preflight accepts only the committed toolkit and Adapter API
gitlinks. It rejects an uninitialized, changed, dirty, incorrectly pinned, or
source-tree-mismatched checkout.

```bash
gradle --no-daemon \
  -PbluemapSourcePath=../bluemap-backport \
  -Pae2AddonJar=../bluemap-ae2-addon/build/libs/bluemap-ae2-addon-0.1.0-alpha.3.jar \
  clean check build
```

`check` is the Java, checkstyle, and archive gate. `prototypeCheck` also
requires every exact candidate JAR property, verifies the exact AE2 alpha.3
API JAR, and validates the 25-cell gallery. The production and sources JARs
contain the four exact Adapter API sources, never the standalone module JAR.
The AE2 JAR remains a compile/test input and is never bundled. See
`provenance/upstreams.json` for immutable input identities and the
[execution guide](docs/EXECUTION.md) for the review and release loop.

## Install

Install this add-on together with the compatible BlueMap AE2 add-on, then
restart the BlueMap JVM. Removal plus one restart restores stock behavior; the
add-on creates no custom world state.

Set `-Dbluemap.arseng.disabled=true` to leave the exact profile inactive.

## Scope boundary

Live Source fill, activity overlays, power/channel state, P2P frequency,
particles, and animation stay stock or deterministic-neutral. The add-on owns
no `ae2:*` renderer; it contributes immutable data through the AE2 add-on's
single host renderer.

No Ars Énergistique binary, source, class, asset, captured mesh, or gallery is
bundled in the add-on.
