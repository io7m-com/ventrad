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

package com.io7m.ventrad.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.io7m.ventrad.core.VProtocol;
import com.io7m.ventrad.core.VProtocols;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;

public final class VProtocolsTest
{
  private static final JsonMapper MAPPER =
    JsonMapper.builder()
      .enable(SerializationFeature.INDENT_OUTPUT)
      .build();

  @Test
  public void testRoundTrip()
    throws Exception
  {
    final var v =
      new VProtocols(
        List.of(
          new VProtocol(
            "urn:com.io7m.cardant:inventory",
            BigInteger.ONE,
            BigInteger.ZERO,
            URI.create("/inventory/1/0/"),
            "Cardant Inventory service v1.0"
          ),
          new VProtocol(
            "urn:com.io7m.cardant:inventory",
            BigInteger.ONE,
            BigInteger.ONE,
            URI.create("/inventory/1/1/"),
            "Cardant Inventory service v1.1"
          ),
          new VProtocol(
            "urn:com.io7m.cardant:inventory",
            BigInteger.TWO,
            BigInteger.ZERO,
            URI.create("/inventory/2/0/"),
            "Cardant Inventory service v2.0"
          )
        )
      );

    final var t =
      MAPPER.writeValueAsString(v);

    System.out.println(t);

    final var rr =
      MAPPER.readerFor(VProtocols.class);

    final var r =
      rr.readValue(t);
  }
}
