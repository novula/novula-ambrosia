package org.novula.configuration;

public abstract class ConfigurationElement
{
	public ConfigurationMap asMap()
	{
		return (ConfigurationMap) this;
	}

	public ConfigurationList asList()
	{
		return (ConfigurationList) this;
	}

	public ConfigurationValue asValue()
	{
		return (ConfigurationValue) this;
	}

	public boolean isMap()
	{
		return this instanceof ConfigurationMap;
	}

	public boolean isList()
	{
		return this instanceof ConfigurationList;
	}

	public boolean isValue()
	{
		return this instanceof ConfigurationValue;
	}
}
