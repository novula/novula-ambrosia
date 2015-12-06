package org.novula.configuration;

import java.util.Objects;

public class ConfigurationValue extends ConfigurationElement
{
	private final Object value;

	public static ConfigurationValue of(final Object value)
	{
		return new ConfigurationValue(value);
	}

	public static ConfigurationValue empty()
	{
		return new ConfigurationValue(null);
	}

	private ConfigurationValue(final Object value)
	{
		this.value = value;
	}

	public String getString()
	{
		return (String) value;
	}

	public int getInt()
	{
		return (int) value;
	}

	public float getFloat()
	{
		return (float) value;
	}

	public boolean getBoolean()
	{
		return (boolean) value;
	}

	public Object getValue()
	{
		return value;
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
		final ConfigurationValue that = (ConfigurationValue) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(value);
	}

	@Override
	public String toString()
	{
		return "ConfigurationValue{" + value + '}';
	}
}
