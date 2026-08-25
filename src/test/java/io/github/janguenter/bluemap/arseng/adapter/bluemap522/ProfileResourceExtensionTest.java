/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arseng.adapter.bluemap522;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.bluecolored.bluemap.core.resources.pack.PackVersion;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Element;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.util.Key;
import io.github.janguenter.bluemap.arseng.activation.AddonRuntime;
import io.github.janguenter.bluemap.arseng.integration.ae2.Ae2BridgeLifecycle;
import io.github.janguenter.bluemap.arseng.profile.ArsEng211BetaProfile;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

class ProfileResourceExtensionTest {

    @Test
    void exactArtifactsAndModelsActivateTheAe2Route()
            throws ReflectiveOperationException {
        assertTrue(Ae2BridgeLifecycle.register());
        AddonRuntime runtime = runtime();

        new ProfileResourceExtension(completePack(), runtime).loadResources(exactRoots());

        assertTrue(runtime.active());
        assertEquals("exact-profile", runtime.detail());
    }

    @Test
    void missingModelLeavesTheRouteInactive() throws ReflectiveOperationException {
        assertTrue(Ae2BridgeLifecycle.register());
        ResourcePack pack = completePack();
        pack.getModels().remove(Key.parse(ArsEng211BetaProfile.REQUIRED_AE2_MODELS.getFirst()));
        AddonRuntime runtime = runtime();

        new ProfileResourceExtension(pack, runtime).loadResources(exactRoots());

        assertFalse(runtime.active());
        assertEquals("exact-resource-model-missing", runtime.detail());
    }

    private static List<Path> exactRoots() {
        List<Path> roots = List.of(
                Path.of(System.getProperty("arsengJar", "")),
                Path.of(System.getProperty("appliedEnergisticsJar", "")),
                Path.of(System.getProperty("arsNouveauJar", ""))
        );
        Assumptions.assumeTrue(roots.stream().allMatch(Files::isRegularFile));
        return roots;
    }

    private static ResourcePack completePack() {
        ResourcePack pack = new ResourcePack(new PackVersion(34, 0));
        ArsEng211BetaProfile.REQUIRED_AE2_MODELS.forEach(model ->
                pack.getModels().put(Key.parse(model), new Model(new Element[0])));
        return pack;
    }

    private static AddonRuntime runtime() throws ReflectiveOperationException {
        Constructor<AddonRuntime> constructor = AddonRuntime.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}
