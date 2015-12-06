package org.novula.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigurationMap extends ConfigurationElement
{
	private final Map<String, ConfigurationElement> internalMap = new HashMap<>();

	public static ConfigurationMap newInstance()
	{
		return new ConfigurationMap();
	}

	private ConfigurationMap() {}

	public int getInt(final String key)
	{
		return internalMap.get(key).asValue().getInt();
	}

	public String getString(final String key)
	{
		return internalMap.get(key).asValue().getString();
	}

	public float getFloat(final String key)
	{
		return internalMap.get(key).asValue().getFloat();
	}

	public boolean getBoolean(final String key)
	{
		return internalMap.get(key).asValue().getBoolean();
	}

	public ConfigurationList getList(final String key)
	{
		return internalMap.get(key).asList();
	}

	public ConfigurationMap getMap(final String key)
	{
		return internalMap.get(key).asMap();
	}

	public Object getValue(final String key)
	{
		return internalMap.get(key);
	}

	ConfigurationMap put(final String key, final ConfigurationElement value)
	{
		internalMap.put(key, value);
		return this;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		final ConfigurationMap map = (ConfigurationMap) o;
		return Objects.equals(internalMap, map.internalMap);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(internalMap);
	}

	@Override
	public String toString()
	{
		return "ConfigurationMap" + internalMap;
	}
}
