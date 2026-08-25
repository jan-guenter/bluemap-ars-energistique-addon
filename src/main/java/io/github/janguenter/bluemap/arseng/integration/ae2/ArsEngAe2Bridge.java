/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arseng.integration.ae2;

import io.github.janguenter.bluemap.ae2.api.Ae2ExtensionRegistry;
import io.github.janguenter.bluemap.ae2.api.CableBusPartDefinition;
import io.github.janguenter.bluemap.ae2.api.CableBusPartKind;
import io.github.janguenter.bluemap.ae2.api.ExtensionDefinition;
import io.github.janguenter.bluemap.ae2.api.ExtensionRoute;
import io.github.janguenter.bluemap.ae2.api.ExtensionRouteState;
import io.github.janguenter.bluemap.ae2.api.NativeDriveCellDefinition;

import java.util.List;

/** Ars Énergistique's immutable registration against the BlueMap AE2 data API. */
public final class ArsEngAe2Bridge {

    static final String ROUTE_ID = "arseng-2.1.1-beta";
    private static final ExtensionDefinition DEFINITION = definition();
    private static ExtensionRoute route;

    private ArsEngAe2Bridge() {
    }

    public static synchronized boolean register() {
        if (route != null) {
            return true;
        }
        try {
            route = Ae2ExtensionRegistry.register(DEFINITION);
            return true;
        } catch (IllegalArgumentException | IllegalStateException | LinkageError exception) {
            return false;
        }
    }

    public static synchronized boolean activate() {
        if (route == null) {
            return false;
        }
        route.activate();
        return route.state() == ExtensionRouteState.ACTIVE;
    }

    public static synchronized boolean deactivate() {
        if (route == null) {
            return false;
        }
        route.deactivate();
        return route.state() == ExtensionRouteState.INACTIVE;
    }

    static ExtensionDefinition definition() {
        return new ExtensionDefinition(
                ROUTE_ID,
                "arseng",
                List.of(
                        new CableBusPartDefinition(
                                "arseng:cable_source_acceptor",
                                CableBusPartKind.STATIC,
                                2,
                                2D,
                                14D,
                                List.of("arseng:part/source_acceptor")
                        ),
                        p2p("arseng:source_p2p_tunnel", "arseng:part/source_p2p_tunnel"),
                        p2p("arseng:spell_p2p_tunnel", "arseng:part/spell_p2p_tunnel")
                ),
                List.of(
                        cell("1k"),
                        cell("4k"),
                        cell("16k"),
                        cell("64k"),
                        cell("256k")
                )
        );
    }

    private static CableBusPartDefinition p2p(String itemId, String frontModel) {
        return new CableBusPartDefinition(
                itemId,
                CableBusPartKind.P2P,
                1,
                2D,
                14D,
                List.of(
                        "ae2:part/p2p/p2p_tunnel_status_off",
                        "ae2:part/p2p/p2p_tunnel_frequency",
                        frontModel
                )
        );
    }

    private static NativeDriveCellDefinition cell(String tier) {
        return new NativeDriveCellDefinition(
                "arseng:source_storage_cell_" + tier,
                "arseng:block/drive/cells/" + tier + "_source_cell"
        );
    }
}
