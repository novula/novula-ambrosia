package org.novula.configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

class JsonConfigurationReader extends ConfigurationReader
{
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConfigurationReader.class);

	private static final JsonFactory JSON_FACTORY = new JsonFactory();

	private final String fileName;

	public JsonConfigurationReader(final String fileName)
	{
		this.fileName = fileName;
	}

	@Override
	public ConfigurationElement read()
	{
		LOGGER.info("About to read the configuration from '{}'.", fileName);
		try (final JsonParser jsonParser = JSON_FACTORY.createParser(new File(fileName)))
		{
			final ConfigurationElement element = parseJson(jsonParser);
			LOGGER.info("Finished reading the configuration from '{}'.", fileName);
			return element;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Cannot parse configuration: " + fileName, e);
		}
	}

	private ConfigurationElement parseJson(final JsonParser jsonParser) throws IOException
	{
		switch (jsonParser.nextToken())
		{
			case START_OBJECT:
				return parseMap(jsonParser);
			case START_ARRAY:
				return parseList(jsonParser);
			default:
				throw new RuntimeException("Unknown type of root element: " + jsonParser.getCurrentToken());
		}
	}

	private ConfigurationMap parseMap(final JsonParser jsonParser) throws IOException
	{
		final ConfigurationMap map = ConfigurationMap.newInstance();
		String name = null;
		while (jsonParser.nextToken() != JsonToken.END_OBJECT)
		{
			switch (jsonParser.getCurrentToken())
			{
				case START_OBJECT:
					map.put(name, parseMap(jsonParser));
					break;
				case START_ARRAY:
					map.put(name, parseList(jsonParser));
					break;
				case FIELD_NAME:
					name = jsonParser.getCurrentName();
					break;
				case VALUE_STRING:
					map.put(name, ConfigurationValue.of(jsonParser.getValueAsString()));
					break;
				case VALUE_NUMBER_INT:
					map.put(name, ConfigurationValue.of(jsonParser.getIntValue()));
					break;
				case VALUE_NUMBER_FLOAT:
					map.put(name, ConfigurationValue.of(jsonParser.getFloatValue()));
					break;
				case VALUE_TRUE:
				case VALUE_FALSE:
					map.put(name, ConfigurationValue.of(jsonParser.getBooleanValue()));
					break;
				case VALUE_NULL:
					map.put(name, ConfigurationValue.of(null));
					break;
			}
		}
		return map;
	}

	private ConfigurationList parseList(final JsonParser jsonParser) throws IOException
	{
		final ConfigurationList list = ConfigurationList.newInstance();
		while (jsonParser.nextToken() != JsonToken.END_ARRAY)
		{
			switch (jsonParser.getCurrentToken())
			{
				case START_OBJECT:
					list.append(parseMap(jsonParser));
					break;
				case START_ARRAY:
					list.append(parseList(jsonParser));
					break;
				case VALUE_STRING:
					list.append(ConfigurationValue.of(jsonParser.getValueAsString()));
					break;
				case VALUE_NUMBER_INT:
					list.append(ConfigurationValue.of(jsonParser.getIntValue()));
					break;
				case VALUE_NUMBER_FLOAT:
					list.append(ConfigurationValue.of(jsonParser.getFloatValue()));
					break;
				case VALUE_TRUE:
				case VALUE_FALSE:
					list.append(ConfigurationValue.of(jsonParser.getValueAsBoolean()));
					break;
				case VALUE_NULL:
					list.append(ConfigurationValue.of(null));
					break;
			}
		}
		return list;
	}
}
