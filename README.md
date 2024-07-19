# Resize Players
Resize your or other players to your desired height! Allows reach to be affected as well.

## Commands
| Command                        | Description                                                                   |
|--------------------------------|-------------------------------------------------------------------------------|
| `/resizeplayers help`          | View the help menu.                                                           |
| `/resizeplayers reload`        | Reload the configuration file.                                                |
| `/resizeplayers info [player]` | View the height, block reach, and entity reach of yourself or another player. |
| `/resize <blocks> [player]`    | Resize yourself or another player to the specified amount of blocks.          |
| `/resize <blocks> all`         | Resize all players to the specified amount of blocks.                         |

## Permissions
| Permission                          | Description                                                                                      |
|-------------------------------------|--------------------------------------------------------------------------------------------------|
| `resizeplayers.scale.self`          | Allows the player to scale themselves with `/resize <blocks>`.                                    |
| `resizeplayers.scale.others`        | Allows the player to scale other players with `/resize <blocks> <player>`.                        |
| `resizeplayers.scale.all`           | Allows the player to scale all players with `/resize <blocks> all`.                               |
| `resizeplayers.scale.exempt`        | Exempts the player from being scaled.                                                            |
| `resizeplayers.scale.bypass`        | Allows the player to bypass the minimum and maximum scale limit.                                 |
| `resizeplayers.reload`              | Allows the player to reload the plugin with `/resizeplayers reload`.                             |
| `resizeplayers.info.self`           | Allows the player to view their own height, block reach, and entity reach with `/resizeplayers info`. |
| `resizeplayers.info.others`         | Allows the player to view another player's height, block reach, and entity reach with `/resizeplayers info <player>`. |
