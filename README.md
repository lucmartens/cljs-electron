# cljs-electron

Research project to investigate:

* Clojurescript build tooling
* Clojurescript REPL
* Fast feedback loop in NodeJS
* Build and code sign Electron on CI
* Publish auto-updating installers on CI

## Overview

An Electron application consists of several components: A NodeJS process (Agent),
a Chromium process (Client) and a coordinating layer (Electron). These
components can be bundled into platform specific installers, allowing any
compile-to-javascript language to be used to build cross-platform desktop
applications using web technologies.

### Agent

The Agent is a fully functional NodeJS process running in Electron's context. In
addition to all NodeJS APIs, the Agent can use Electron's APIs for interacting
with the user's OS (notifications, dialogs, etc). The Agent can create
BrowserWindows, in which the Client process is loaded. Communication between
the Agent and Client is achieved using an event-like Inter Process Communication
channel.

### Client

The Client is a Chromium instance running in an Electron BrowserWindow. A single
Agent can own zero-to-many Clients. A Client always belongs to one Agent. By
default the Client has full access to the Agent and it's modules through an
Electron proxy layer. It is recommended to disable this integration and to
communicate through electron IPC channels.

## Development

### Setup

Ensure you have `lein`, `make` and `yarn` installed. Run `make init` to
install the application's dependencies.

### Local development

Start a Clojure REPL. From the REPL, call `(fig-start)` to start a `figwheel`
server which will create development artifacts for the Agent and Client
components. These artifacts contain a websocket interface between the component
and the REPL.

Once figwheel has finished compiling, run `make start` to start the electron
application using the development artifacts. To connect to the Agent or Client
Clojurescript REPLs, eval `(agent-repl)` or (`client-repl`) from your Clojure
REPL.

to exit the Clojurescript REPL and return to your Clojure REPL, eval
`:cljs/quit`. To stop the figwheel server, eval `(fig-stop)` from your Clojure
REPL.

## Continuous Integration

### Testing

The project contains a `.travis.yml` configuration file which will attempt
to build production artifacts on pushed commits. Currently, no unit-testing
framework is configured for the project. The assumption is that `clojure.test`
should work as expected when you correctly mock the electron api.

### Deployment

The project contains a `.travis.yml` configuration file to create and deploy
installers for MacOs and Windows on tagged commits. The installers are published
to github releases, using the `package.json` `version` field as the release
title. The application will automatically update once a newer github release
is available. In order for the installers to be properly signed and deployed to
github, some setup in Travis is required:

#### All artifacts

* Set a strong password as private `KEYCHAIN_PASS` env variable

#### MacOs artifacts

* Package `Developer ID application` and `Developer ID installer` certificates
  and keys into a p12 archive
* Set base64 encoded p12 archive as private `CERTIFICATE` env variable
* Set p12 archive password as private `CERTIFICATE_PASS` env variable

#### Windows artifacts

* Package code signing certificate and key into a p12 archive
* Set base64 encoded p12 archive as private `CSC_LINK` env variable
* Set p12 archive password as private `CSC_KEY_PASSWORD` env variable

#### Deploy artifacts

* Create a github personal access token
* Set token as private `GH_TOKEN` env variable
