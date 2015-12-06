package org.novula.configuration;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class JsonConfigurationReaderTest
{
	@Test
	public void readOneEntryConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/one-entry-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isTrue();
		assertThat(rootElement.isList()).isFalse();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asMap().getInt("key")).isEqualTo(123);
	}

	@Test
	public void readSimpleMapConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/simple-map-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isTrue();
		assertThat(rootElement.isList()).isFalse();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asMap().getInt("key")).isEqualTo(123);
		assertThat(rootElement.asMap().getString("star")).isEqualTo("Sun");
		assertThat(rootElement.asMap().getFloat("approx. one third")).isEqualTo(0.333f);
		assertThat(rootElement.asMap().getInt("negative")).isEqualTo(-347);
		assertThat(rootElement.asMap().getBoolean("probable")).isTrue();
		assertThat(rootElement.asMap().getValue("nothing")).isEqualTo(ConfigurationValue.empty());
	}

	@Test
	public void readListValueConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/list-value-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isTrue();
		assertThat(rootElement.isList()).isFalse();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asMap().getList("key"))
				.containsOnly(
						ConfigurationValue.of(12),
						ConfigurationValue.of(34),
						ConfigurationValue.of(56),
						ConfigurationValue.of(78));
	}

	@Test
	public void readSimpleListConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/simple-list-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isFalse();
		assertThat(rootElement.isList()).isTrue();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asList())
				.containsOnly(
						ConfigurationValue.of(12),
						ConfigurationValue.of(34),
						ConfigurationValue.of(56),
						ConfigurationValue.of(78));
	}

	@Test
	public void readListWithVariousValuesConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/list-with-various-values-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isFalse();
		assertThat(rootElement.isList()).isTrue();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asList())
				.containsOnly(
						ConfigurationValue.of(12),
						ConfigurationValue.of("a string"),
						ConfigurationValue.of(null),
						ConfigurationMap.newInstance()
								.put("first", ConfigurationValue.of(4))
								.put("last", ConfigurationValue.of(false)),
						ConfigurationValue.of(true),
						ConfigurationValue.of(0.625f));
	}

	@Test
	public void readComplexListConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/complex-list-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isFalse();
		assertThat(rootElement.isList()).isTrue();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asList())
				.containsOnly(
						ConfigurationValue.of(12),
						ConfigurationList.newInstance()
								.append(ConfigurationValue.of(45.15365f))
								.append(ConfigurationMap.newInstance()
										.put("one", ConfigurationValue.of(true)))
								.append(ConfigurationValue.of(null))
								.append(ConfigurationList.newInstance()
										.append(ConfigurationValue.of(1))
										.append(ConfigurationValue.of(2))
										.append(ConfigurationValue.of(3))),
						ConfigurationValue.of("string"),
						ConfigurationValue.of(null),
						ConfigurationMap.newInstance()
								.put("first", ConfigurationValue.of(4))
								.put("last", ConfigurationValue.of(false))
								.put("list", ConfigurationList.newInstance()
										.append(ConfigurationValue.of("a"))
										.append(ConfigurationValue.of("b"))
										.append(ConfigurationValue.of(true))
										.append(ConfigurationValue.of("c"))
										.append(ConfigurationMap.newInstance()
												.put("un", ConfigurationValue.of("one")))
										.append(ConfigurationList.newInstance()
												.append(ConfigurationValue.of(true))
												.append(ConfigurationValue.of(false))
												.append(ConfigurationValue.of(false)))
										.append(ConfigurationValue.of("d"))),
						ConfigurationValue.of(true),
						ConfigurationList.newInstance()
								.append(ConfigurationList.newInstance()
										.append(ConfigurationList.newInstance()
												.append(ConfigurationValue.of(42)))),
						ConfigurationValue.of(0.625f));
	}

	@Test
	public void readComplexMapConfig()
	{
		//given
		final JsonConfigurationReader reader = readerFor("json-reader/complex-map-config.json");

		// when
		final ConfigurationElement rootElement = reader.read();

		// then
		assertThat(rootElement.isMap()).isTrue();
		assertThat(rootElement.isList()).isFalse();
		assertThat(rootElement.isValue()).isFalse();

		assertThat(rootElement.asMap().getInt("number")).isEqualTo(12);
		assertThat(rootElement.asMap().getList("list"))
				.containsOnly(
						ConfigurationValue.of(45.15365f),
						ConfigurationMap.newInstance()
								.put("one", ConfigurationValue.of(true)),
						ConfigurationValue.of(null),
						ConfigurationList.newInstance()
								.append(ConfigurationValue.of(1))
								.append(ConfigurationValue.of(2))
								.append(ConfigurationValue.of(3)));
		assertThat(rootElement.asMap().getString("string")).isEqualTo("string");
		assertThat(rootElement.asMap().getValue("null")).isEqualTo(ConfigurationValue.empty());
		assertThat(rootElement.asMap().getMap("map")).isEqualTo(
				ConfigurationMap.newInstance()
						.put("first", ConfigurationValue.of(4))
						.put("last", ConfigurationValue.of(false))
						.put("list", ConfigurationList.newInstance()
								.append(ConfigurationValue.of("a"))
								.append(ConfigurationValue.of("b"))
								.append(ConfigurationValue.of(true))
								.append(ConfigurationValue.of("c"))
								.append(ConfigurationMap.newInstance()
										.put("un", ConfigurationValue.of("one")))
								.append(ConfigurationList.newInstance()
										.append(ConfigurationValue.of(true))
										.append(ConfigurationValue.of(false))
										.append(ConfigurationValue.of(false)))
								.append(ConfigurationValue.of("d"))));
		assertThat(rootElement.asMap().getBoolean("bool")).isTrue();
		assertThat(rootElement.asMap().getList("nested list")).containsOnly(
				ConfigurationList.newInstance()
						.append(ConfigurationList.newInstance()
								.append(ConfigurationValue.of(42))));
		assertThat(rootElement.asMap().getFloat("float")).isEqualTo(0.625f);
	}

	private JsonConfigurationReader readerFor(final String fileName)
	{
		return new JsonConfigurationReader("src/test/resources/" + fileName);
	}
}
