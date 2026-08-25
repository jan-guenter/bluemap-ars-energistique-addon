/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arseng.profile;

import java.util.List;

/** Exact All the Mons 1.2.0 profile `arseng-2.1.1-beta-mc1.21.1`. */
public final class ArsEng211BetaProfile {

    public static final String PROFILE_ID = "arseng-2.1.1-beta-mc1.21.1";
    public static final List<String> REQUIRED_AE2_MODELS = List.of(
            "arseng:part/source_acceptor",
            "arseng:part/source_p2p_tunnel",
            "arseng:part/spell_p2p_tunnel",
            "arseng:block/drive/cells/1k_source_cell",
            "arseng:block/drive/cells/4k_source_cell",
            "arseng:block/drive/cells/16k_source_cell",
            "arseng:block/drive/cells/64k_source_cell",
            "arseng:block/drive/cells/256k_source_cell"
    );
    public static final List<ArtifactPin> ARTIFACTS = List.of(
            new ArtifactPin(
                    "arseng",
                    "arseng",
                    "2.1.1-beta",
                    "arseng-2.1.1-beta.jar",
                    123_229L,
                    "9e2b78101f08bf325589cb98ffba9d91171fdd76ba587d54bdef9d5de18080bc"
            ),
            new ArtifactPin(
                    "appliedEnergistics2",
                    "ae2",
                    "19.2.17",
                    "appliedenergistics2-19.2.17.jar",
                    8_230_896L,
                    "460d779a0609b81409907d9956de8f6f70a1b0912257e3e5c3c7e75ac9630e95"
            ),
            new ArtifactPin(
                    "arsNouveau",
                    "ars_nouveau",
                    "5.13.0",
                    "ars_nouveau-1.21.1-5.13.0.jar",
                    20_096_005L,
                    "90796df69bfb39b1a9c79edbfa01c2425e5b86aea47dc55ebdcbf30e88f47592"
            )
    );

    private ArsEng211BetaProfile() {
    }
}
