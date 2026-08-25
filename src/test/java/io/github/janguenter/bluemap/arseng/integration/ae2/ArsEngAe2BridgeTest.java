/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arseng.integration.ae2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janguenter.bluemap.ae2.api.CableBusPartKind;
import io.github.janguenter.bluemap.ae2.api.ExtensionDefinition;

import org.junit.jupiter.api.Test;

class ArsEngAe2BridgeTest {

    @Test
    void exactDefinitionContainsThreePartsAndFiveNativeDriveCells() {
        ExtensionDefinition definition = ArsEngAe2Bridge.definition();

        assertEquals("arseng-2.1.1-beta", definition.routeId());
        assertEquals("arseng", definition.ownerNamespace());
        assertEquals(3, definition.cableBusParts().size());
        assertEquals(5, definition.nativeDriveCells().size());
        assertEquals(CableBusPartKind.STATIC, definition.cableBusParts().get(0).kind());
        assertEquals(CableBusPartKind.P2P, definition.cableBusParts().get(1).kind());
        assertEquals(CableBusPartKind.P2P, definition.cableBusParts().get(2).kind());
    }
}
