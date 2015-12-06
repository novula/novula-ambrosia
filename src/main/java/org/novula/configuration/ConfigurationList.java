package org.novula.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ConfigurationList extends ConfigurationElement implements Iterable<ConfigurationElement>
{
	private final List<ConfigurationElement> internalList = new ArrayList<>();

	public static ConfigurationList newInstance()
	{
		return new ConfigurationList();
	}

	private ConfigurationList() {}

	@Override
	public Iterator<ConfigurationElement> iterator()
	{
		return internalList.iterator();
	}

	public ConfigurationList append(final ConfigurationElement element)
	{
		internalList.add(element);
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
		final ConfigurationList list = (ConfigurationList) o;
		return Objects.equals(internalList, list.internalList);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(internalList);
	}

	@Override
	public String toString()
	{
		return "ConfigurationList" + internalList;
	}
}
