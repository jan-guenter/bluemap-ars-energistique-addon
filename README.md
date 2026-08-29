# BlueMap Ars Énergistique Add-on

A Java 21 BlueMap add-on for the exact `arseng-2.1.1-beta-mc1.21.1` profile in All the Mons
`1.2.0` / Minecraft `1.21.1`.

Status: `0.1.0-alpha.1` is the owner-accepted first release candidate. The exact
artifact gate registers three cable parts and five Source Storage Cell models
through the data-only public API in the BlueMap AE2 add-on. Direct Ars
Énergistique blocks retain their correct stock resource models.

## Build

Clone with `--recurse-submodules`, or initialize an existing checkout with
`git submodule update --init --recursive -- tooling/bluemap-addon-toolkit`.
The settings preflight accepts only the committed toolkit gitlink at commit
`6cd34a8368cc4ee8628fbe830a90ec5b14960629` and rejects an uninitialized,
changed, or dirty toolkit checkout.

```bash
gradle --no-daemon \
  -PbluemapSourcePath=../bluemap-backport \
  -Pae2AddonJar=../bluemap-ae2-addon/build/libs/bluemap-ae2-addon-0.1.0-alpha.3.jar \
  clean check build
```

`check` is the quick Java/checkstyle/archive gate. `prototypeCheck` additionally
requires every exact candidate JAR property, verifies the exact AE2 alpha.3 API
JAR, and validates the 25-cell gallery. The AE2 JAR is a compile/test input and
is never bundled. See `provenance/upstreams.json` for immutable artifact
identities and the [execution guide](docs/EXECUTION.md) for the
prototype-to-release loop.

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
