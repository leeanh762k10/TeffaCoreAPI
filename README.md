# TeffaCoreAPI

**Language:** English | [Tiếng Việt](README.vi.md)

---

## English

TeffaCoreAPI is a core plugin/API for Minecraft Paper servers in the Teffa plugin ecosystem.

This plugin provides shared services for other Teffa plugins, including player profiles, permission access, diagnostics, and TeffaClient bridge/session support.

> Status: Beta. Features and API structure may change in future versions.

## Features

* Player profile system
* JSON profile storage
* Permission service using LuckPerms
* Diagnostic service
* TeffaClient bridge/session system
* Join/leave profile handling
* `/teffa` command base
* API foundation for other Teffa plugins

## Requirements

* Paper 26.1.2
* Java 25
* Maven
* LuckPerms

## Installation

1. Download `LuckPerms`
2. Download `TeffaCoreAPI`
3. Put all `.jar` files into the `plugins/` folder
4. Restart the server

## Build

```bash
mvn clean package
```

The built `.jar` file will be located in the `target/` folder.

## Commands

| Command                        | Permission                   | Description                         |
| ------------------------------ | ---------------------------- | ----------------------------------- |
| `/teffa clientstatus <player>` | `teffa.command.clientstatus` | Check a player's TeffaClient status |

## Project structure

```txt
TeffaCoreAPI/
├─ src/
│  └─ main/
│     ├─ java/
│     └─ resources/
│        └─ plugin.yml
├─ pom.xml
├─ .gitignore
└─ README.md
```

## Notes

TeffaCoreAPI is still in development and may change in future versions.

TeffaCoreAPI is part of the Teffa plugin ecosystem, developed for Minecraft Paper servers.

This project is created and maintained by Nguyễn Lê Anh.

Some parts of the code were developed with AI assistance.

The idea, feature design, testing, and implementation decisions belong to the author.
