BungeeJSON
==========

This is a really quick hack-y fork of minecrafter/BungeeJSON (commit: a6a655a9d9aaa61d563b537e549633d3092e0f7f).

The original version was built against a newer version of the Bungeecord API. I'm running a Forge based modified server running Minecraft 1.6.4 and I'm stuck with build #701 of Bungeecord, that's before the needed API changes.

After some investigation I realized that I could just strip away a lot of the functionality that used the new API:s, I only needed /bungeecord/server_list anyway.

This build removes:
Chat, message and commands.
Kick, connect and player control.
All auth (I do not need it)
Configuration (was easier to remove)

I have changed the pom to use 1.6.4-SNAPSHOT of bungeecord-api and bungeecord-config.

It builds with "mvn clean package" and works :)
