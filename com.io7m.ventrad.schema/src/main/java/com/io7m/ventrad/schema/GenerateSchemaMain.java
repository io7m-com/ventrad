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


package com.io7m.ventrad.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.io7m.ventrad.core.VProtocols;

import static com.github.victools.jsonschema.module.jackson.JacksonOption.INCLUDE_ONLY_JSONPROPERTY_ANNOTATED_METHODS;
import static com.github.victools.jsonschema.module.jackson.JacksonOption.RESPECT_JSONPROPERTY_REQUIRED;

/**
 * Schema generation.
 */

public final class GenerateSchemaMain
{
  private GenerateSchemaMain()
  {

  }

  /**
   * The main entry point.
   *
   * @param args Command-line arguments
   *
   * @throws Exception On errors
   */

  public static void main(
    final String[] args)
    throws Exception
  {
    final var module =
      new JacksonModule(
        INCLUDE_ONLY_JSONPROPERTY_ANNOTATED_METHODS,
        RESPECT_JSONPROPERTY_REQUIRED
      );

    final var builder =
      new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12);

    builder.with(module);
    builder.with(Option.ADDITIONAL_FIXED_TYPES);
    builder.with(Option.DEFINITIONS_FOR_ALL_OBJECTS);
    builder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES);
    builder.with(Option.FIELDS_DERIVED_FROM_ARGUMENTFREE_METHODS);
    builder.with(Option.FLATTENED_ENUMS);
    builder.with(Option.FLATTENED_OPTIONALS);
    builder.with(Option.SCHEMA_VERSION_INDICATOR);
    builder.without(Option.NONPUBLIC_STATIC_FIELDS);

    builder.forFields()
      .withNullableCheck(ignored -> false);
    builder.forMethods()
      .withNullableCheck(ignored -> false);

    builder.forMethods()
      .withPropertyNameOverrideResolver(target -> {
        return target.getAnnotation(JsonProperty.class).value();
      });

    final var config =
      builder.build();

    final var generator =
      new SchemaGenerator(config);

    final var mapper =
      JsonMapper.builder()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build();

    System.out.println(
      mapper.writeValueAsString(generator.generateSchema(VProtocols.class))
    );
  }
}
