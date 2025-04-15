/*
 * Copyright © 2024 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */


package com.io7m.ventrad.core;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigInteger;
import java.net.URI;
import java.util.Comparator;
import java.util.Objects;

/**
 * A single version of a single protocol.
 *
 * @param identifier   The protocol identifier
 * @param versionMajor The protocol major version
 * @param versionMinor The protocol minor version
 * @param endpoint     The endpoint to be used by clients for this protocol
 * @param description  A humanly-readable description of the protocol
 */

@JsonClassDescription("A single version of a single protocol.")
public record VProtocol(
  @JsonPropertyDescription("The protocol identifier.")
  @JsonProperty(value = "Id", required = true)
  String identifier,
  @JsonPropertyDescription("The protocol major version.")
  @JsonProperty(value = "VersionMajor", required = true)
  BigInteger versionMajor,
  @JsonPropertyDescription("The protocol minor version.")
  @JsonProperty(value = "VersionMinor", required = true)
  BigInteger versionMinor,
  @JsonPropertyDescription("The endpoint to be used by clients for this protocol.")
  @JsonProperty(value = "Endpoint", required = true)
  URI endpoint,
  @JsonPropertyDescription("A humanly-readable description of the protocol.")
  @JsonProperty(value = "Description", required = true)
  String description)
  implements Comparable<VProtocol>
{
  /**
   * A single version of a single protocol.
   *
   * @param identifier   The protocol identifier
   * @param versionMajor The protocol major version
   * @param versionMinor The protocol minor version
   * @param endpoint     The endpoint to be used by clients for this protocol
   * @param description  A humanly-readable description of the protocol
   */

  public VProtocol
  {
    Objects.requireNonNull(identifier, "identifier");
    Objects.requireNonNull(endpoint, "endpoint");
    Objects.requireNonNull(versionMajor, "versionMajor");
    Objects.requireNonNull(versionMinor, "versionMinor");
    Objects.requireNonNull(description, "description");
  }

  @Override
  public int compareTo(
    final VProtocol other)
  {
    return Comparator.comparing(VProtocol::identifier)
      .thenComparing(VProtocol::versionMajor)
      .thenComparing(VProtocol::versionMinor)
      .compare(this, other);
  }
}
