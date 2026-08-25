#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Lint the generated Ars Énergistique gallery without starting Minecraft."""

from __future__ import annotations

import json
from pathlib import Path
import re
import sys

sys.dont_write_bytecode = True
import cases
import generate


ROOT = Path(__file__).resolve().parent


def main() -> int:
    for relative, payload in generate.generated_files().items():
        path = ROOT / relative
        if not path.is_file() or path.read_bytes() != payload:
            raise ValueError(f"generated file differs: {relative}")

    json.loads((ROOT / "datapack/pack.mcmeta").read_text(encoding="utf-8"))
    load_tag = json.loads(
        (ROOT / "datapack/data/minecraft/tags/function/load.json").read_text(
            encoding="utf-8"
        )
    )
    if load_tag != {"values": [f"{cases.NAMESPACE}:load"]}:
        raise ValueError("load tag differs from the exact namespace")
    if len(cases.PLACEMENTS) != 25:
        raise ValueError("gallery must contain exactly 25 review cells")

    case_ids = {placement.case_id for placement in cases.PLACEMENTS}
    coordinates = {
        (placement.x, placement.y, placement.z) for placement in cases.PLACEMENTS
    }
    if len(case_ids) != 25 or len(coordinates) != 25:
        raise ValueError("gallery case IDs and coordinates must be unique")

    minimum_x, minimum_y, minimum_z, maximum_x, maximum_y, maximum_z = (
        cases.ENVELOPE
    )
    for placement in cases.PLACEMENTS:
        if not (
            minimum_x <= placement.x <= maximum_x
            and minimum_y <= placement.y <= maximum_y
            and minimum_z <= placement.z <= maximum_z
        ):
            raise ValueError(f"placement escaped envelope: {placement.case_id}")

    states = [placement.block_state for placement in cases.PLACEMENTS]
    if sum(state.startswith("arseng:me_source_jar[") for state in states) != 4:
        raise ValueError("gallery must cover four ME Source Jar facings")
    if states.count("ae2:cable_bus") != 18:
        raise ValueError("gallery must cover three parts on all six faces")
    if sum(state.startswith("ae2:drive[") for state in states) != 1:
        raise ValueError("gallery must contain one Source Cell drive")
    if states.count("minecraft:stone") != 1:
        raise ValueError("gallery must contain one stock control")

    function_root = ROOT / f"datapack/data/{cases.NAMESPACE}/function"
    functions = "\n".join(
        path.read_text(encoding="utf-8")
        for path in sorted(function_root.glob("*.mcfunction"))
    )
    if len(re.findall(r"^setblock ", functions, re.MULTILINE)) != 25:
        raise ValueError("gallery must place exactly 25 blocks")
    if len(re.findall(r"^setblock .* ae2:cable_bus\{", functions, re.MULTILINE)) != 18:
        raise ValueError("gallery must atomically place all 18 cable-bus fixtures")
    if len(re.findall(r"^data merge block ", functions, re.MULTILINE)) != 1:
        raise ValueError("gallery must inject exactly one non-cable block entity")
    lowered = functions.lower()
    for forbidden in ("summon ", "op ", "deop ", "stop "):
        if forbidden in lowered:
            raise ValueError(f"forbidden gallery command: {forbidden}")

    print("gallery lint passed: 25 bounded Ars Énergistique review cells")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError) as error:
        print(f"gallery lint failed: {error}", file=sys.stderr)
        raise SystemExit(1)
