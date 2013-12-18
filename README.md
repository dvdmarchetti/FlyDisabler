FlyDisabler
===========
Simple and lightweight bukkit plugin to prevent flying on specified worlds.
___
Installation
============
You only have to download and put this plugin into plugins folder of your server.
Then start and stop server. The plugin will generate a sample configuration file.
Now edit your config adding the name of the worlds where you want to disable fly.
####You're done!
Now just start the server!
___
Configuration
=============
This is the default configuration:

```
worldsDisableFly:
- world1
- world2
```

It simply prevents users from flying in world1 and world2.
___
Permissions
===========
Note: You can use **/fd** instead of **/flydisabler** as an alias.

| Command | Permission | Description |
| --------------:|:--------------:| ------ |
| /flydisabler reload| flydisabler.admin.reload | Reload the config |
| -| flydisabler.exempt | Allows a player to fly in world where flying is disabled |
