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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

/**
 * A set of supported protocols in priority order.
 *
 * @param protocols The list of supported protocols
 */

@JsonClassDescription("A set of supported protocols in priority order.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record VProtocols(
  @JsonPropertyDescription("The protocols.")
  @JsonProperty(value = "Protocols", required = true)
  List<VProtocol> protocols)
{
  /**
   * @return The ventrad schema identifier
   */

  @JsonProperty(value = "%Schema", index = -1000)
  public String schema()
  {
    return "urn:com.io7m.ventrad:1";
  }

  /**
   * A set of supported protocols in priority order.
   *
   * @param protocols The list of supported protocols
   */

  public VProtocols
  {
    protocols = List.copyOf(protocols);
  }
}
