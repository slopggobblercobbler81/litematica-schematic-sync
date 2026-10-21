# Litematica Schematic Sync

A Fabric mod for Minecraft 1.21.4 that provides real-time cloud (GitHub Gist) and LAN (mDNS) schematic synchronization between players.

## Features

- **Cloud Synchronization**: Sync schematics securely using GitHub Gists.
- **LAN Discovery**: Automatically discover and share schematics on your local network using mDNS.
- **Seamless Integration**: Fully integrates with Litematica and MaLiLib.

## End-to-End Sync Verification

To verify schematic synchronization:
1. Ensure both clients have `litematica-schematic-sync` installed along with `litematica` and `malilib`.
2. Configure your GitHub token in the mod config screen or via command (`/schematic-sync gist <token>`).
3. Save or load a schematic in Litematica; changes will automatically be broadcasted or fetched from the configured Gist or local LAN peers.
