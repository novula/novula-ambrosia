package org.novula.configuration;

public abstract class ConfigurationReader
{
	public static ConfigurationReader newJsonReader(final String fileName)
	{
		return new JsonConfigurationReader(fileName);
	}

	protected ConfigurationReader() {}

	public abstract ConfigurationElement read();
}
