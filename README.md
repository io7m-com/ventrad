ventrad
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.ventrad/com.io7m.ventrad.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.ventrad%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/com.io7m.ventrad/com.io7m.ventrad?server=https%3A%2F%2Fs01.oss.sonatype.org&style=flat-square)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/io7m/ventrad/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/ventrad.svg?style=flat-square)](https://codecov.io/gh/io7m-com/ventrad)
![Java Version](https://img.shields.io/badge/17-java?label=java&color=e65cc3)

![com.io7m.ventrad](./src/site/resources/ventrad.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/ventrad/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/ventrad/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/ventrad/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/ventrad/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/ventrad/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/ventrad/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/ventrad/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/ventrad/actions?query=workflow%3Amain.windows.temurin.lts)|

## ventrad

The `ventrad` package provides a JSON protocol for advertising HTTP endpoint
protocol versions.

### Features

* Written in pure Java 17.
* [OSGi](https://www.osgi.org/) ready.
* [JPMS](https://en.wikipedia.org/wiki/Java_Platform_Module_System) ready.
* ISC license.
* High-coverage automated test suite.

### Description

`ventrad` is a trivial protocol for announcing a set of protocols
exposed by a set of HTTP endpoints.

A client connecting to a root endpoint reads a JSON document delivered with
the content type `application/ventrad+json`. The document details which
protocols are supported by the given server, and instructs the client on
where to go in order to use those protocols.

As an example:

```
$ curl https://api.example.com/
{
  "%Schema" : "urn:com.io7m.ventrad:1",
  "Protocols" : [ {
    "Id" : "urn:com.io7m.cardant:inventory",
    "VersionMajor" : 1,
    "VersionMinor" : 0,
    "Endpoint" : "/inventory/1/0/",
    "Description" : "Cardant Inventory service v1.0"
  }, {
    "Id" : "urn:com.io7m.cardant:inventory",
    "VersionMajor" : 1,
    "VersionMinor" : 1,
    "Endpoint" : "/inventory/1/1/",
    "Description" : "Cardant Inventory service v1.1"
  }, {
    "Id" : "urn:com.io7m.cardant:inventory",
    "VersionMajor" : 2,
    "VersionMinor" : 0,
    "Endpoint" : "/inventory/2/0/",
    "Description" : "Cardant Inventory service v2.0"
  } ]
}
```

The JSON document `MUST` include a `%Schema` property with a value of
`urn:com.io7m.ventrad:1`.

The `Protocols` property is a non-null array of `Protocol` objects. A
`Protocol` object contains a unique identifier for the protocol, a non-negative
major and minor protocol version, a `URI` indicating the endpoint that uses
the protocol, and a humanly-readable description of the protocol and the
version. All fields are required and must be non-null.

### Schema

The served document `MUST` conform to the given [schema](Ventrad.schema.json):

```
{
  "$schema" : "https://json-schema.org/draft/2020-12/schema",
  "$defs" : {
    "VProtocol" : {
      "type" : "object",
      "properties" : {
        "Description" : {
          "type" : "string",
          "description" : "A humanly-readable description of the protocol."
        },
        "Endpoint" : {
          "type" : "string",
          "format" : "uri",
          "description" : "The endpoint to be used by clients for this protocol."
        },
        "Id" : {
          "type" : "string",
          "description" : "The protocol identifier."
        },
        "VersionMajor" : {
          "type" : "integer",
          "description" : "The protocol major version."
        },
        "VersionMinor" : {
          "type" : "integer",
          "description" : "The protocol minor version."
        }
      },
      "required" : [ "Description", "Endpoint", "Id", "VersionMajor", "VersionMinor" ],
      "description" : "A single version of a single protocol."
    }
  },
  "type" : "object",
  "properties" : {
    "%Schema" : {
      "type" : "string"
    },
    "Protocols" : {
      "description" : "The protocols.",
      "type" : "array",
      "items" : {
        "$ref" : "#/$defs/VProtocol"
      }
    }
  },
  "required" : [ "%Schema", "Protocols" ],
  "description" : "A set of supported protocols in priority order."
}
```

