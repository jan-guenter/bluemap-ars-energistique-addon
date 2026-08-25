#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Complete stable Ars Énergistique block and AE2-host fixtures."""

from __future__ import annotations

from dataclasses import dataclass


NAMESPACE = "arseng_gallery"
ENVELOPE = (173, 99, 173, 199, 103, 195)
TELEPORT = (186, 110, 184, 0, 45)


@dataclass(frozen=True)
class Placement:
    case_id: str
    label: str
    x: int
    y: int
    z: int
    block_state: str
    expected: str
    block_entity_snbt: str | None = None


def cable_bus(case_id: str, part_id: str, side: str, x: int, z: int) -> Placement:
    return Placement(
        case_id,
        f"{part_id.removeprefix('arseng:').replace('_', ' ')} on {side}",
        x,
        100,
        z,
        "ae2:cable_bus",
        "ae2-extension-part-visible",
        "{hasRedstone:2,cable:{id:\"ae2:fluix_glass_cable\"},"
        f"{side}:{{id:\"{part_id}\"}}}}",
    )


JARS = tuple(
    Placement(
        f"me-source-jar-{facing}",
        f"empty ME Source Jar facing {facing}",
        176 + index * 4,
        100,
        176,
        f"arseng:me_source_jar[facing={facing},waterlogged=false]",
        "stock-shell-visible",
    )
    for index, facing in enumerate(("north", "east", "south", "west"))
)

PART_IDS = (
    "arseng:cable_source_acceptor",
    "arseng:source_p2p_tunnel",
    "arseng:spell_p2p_tunnel",
)
SIDES = ("down", "up", "north", "south", "west", "east")
PARTS = tuple(
    cable_bus(
        f"{part_id.removeprefix('arseng:').replace('_', '-')}-{side}",
        part_id,
        side,
        176 + side_index * 4,
        180 + part_index * 4,
    )
    for part_index, part_id in enumerate(PART_IDS)
    for side_index, side in enumerate(SIDES)
)

DRIVE_NBT = (
    "{inv:{"
    'item0:{id:"arseng:source_storage_cell_1k",count:1},'
    'item1:{id:"arseng:source_storage_cell_4k",count:1},'
    'item2:{id:"arseng:source_storage_cell_16k",count:1},'
    'item3:{id:"arseng:source_storage_cell_64k",count:1},'
    'item4:{id:"arseng:source_storage_cell_256k",count:1},'
    "item5:{},item6:{},item7:{},item8:{},item9:{}}}"
)

PLACEMENTS = (
    JARS
    + (
        Placement(
            "source-acceptor-control",
            "ME Source Converter stock-model control",
            192,
            100,
            176,
            "arseng:source_acceptor",
            "stock-model-visible",
        ),
    )
    + PARTS
    + (
        Placement(
            "source-cell-drive",
            "AE2 Drive with all five Source Storage Cells",
            176,
            100,
            192,
            "ae2:drive[facing=south,spin=0]",
            "ae2-extension-cells-visible",
            DRIVE_NBT,
        ),
        Placement(
            "stock-control",
            "stone stock rendering control",
            180,
            100,
            192,
            "minecraft:stone",
            "stock-visible",
        ),
    )
)
