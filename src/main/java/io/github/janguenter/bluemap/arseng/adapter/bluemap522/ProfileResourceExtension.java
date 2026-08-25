/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arseng.adapter.bluemap522;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePackExtension;
import de.bluecolored.bluemap.core.util.Key;
import io.github.janguenter.bluemap.arseng.activation.AddonRuntime;
import io.github.janguenter.bluemap.arseng.integration.ae2.Ae2BridgeLifecycle;
import io.github.janguenter.bluemap.arseng.profile.ExactArtifactDetector;
import io.github.janguenter.bluemap.arseng.profile.ArsEng211BetaProfile;

import java.nio.file.Path;

/** Exact-artifact admission hook for the data-only AE2 extension route. */
final class ProfileResourceExtension implements ResourcePackExtension {

    private final ResourcePack resourcePack;
    private final AddonRuntime runtime;

    ProfileResourceExtension(ResourcePack resourcePack, AddonRuntime runtime) {
        this.resourcePack = resourcePack;
        this.runtime = runtime;
    }

    @Override
    public void loadResources(Iterable<Path> roots) {
        if (Boolean.getBoolean("bluemap.arseng.disabled")) {
            Ae2BridgeLifecycle.deactivate();
            runtime.inactive("operator-disabled");
            return;
        }
        if (!ExactArtifactDetector.matchesAll(roots, ArsEng211BetaProfile.ARTIFACTS)) {
            Ae2BridgeLifecycle.deactivate();
            runtime.inactive("exact-artifact-missing-or-duplicate");
            return;
        }
        if (!requiredModelsPresent()) {
            Ae2BridgeLifecycle.deactivate();
            runtime.inactive("exact-resource-model-missing");
            return;
        }
        if (!Ae2BridgeLifecycle.activate()) {
            runtime.inactive("ae2-addon-api-unavailable");
            return;
        }
        runtime.activate();
    }

    private boolean requiredModelsPresent() {
        if (resourcePack.getModels() == null) {
            return false;
        }
        for (String model : ArsEng211BetaProfile.REQUIRED_AE2_MODELS) {
            if (resourcePack.getModels().get(Key.parse(model)) == null) {
                return false;
            }
        }
        return true;
    }
}
