# Ars Énergistique gallery

The deterministic 25-cell gallery covers every horizontal ME Source Jar
facing, the stock Source Acceptor, all three Ars Énergistique AE2 parts on all
six cable-bus faces, one drive containing all five Source Storage Cells, and a
stone control.

Live Source fill, AE2 power/channel state, and P2P frequency remain neutral.
Those runtime states are outside this add-on's stable static-map contract.

```bash
python gallery/generate.py
python gallery/generate.py --check
python gallery/lint.py
bash gallery/package.sh /tmp/arseng-gallery.zip
```

The gallery is bounded and generated from commands only. It contains no copied
mod assets or captured meshes.
