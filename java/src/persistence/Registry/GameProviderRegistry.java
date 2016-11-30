package persistence.Registry;

import java.io.File;

import persistence.provider.IProvider;

public class GameProviderRegistry {

	/**
	 * Registers a plugin to be used as the User Provider
	 * 
	 * @param name the name of the plugin
	 * @param className the className of the plugin
	 * @param fileExtensions the fileExtensions
	 */
	void registerGameProvider(String name, String className,
			String[] fileExtensions)
	{	
		
	}

	/**
	 * Creates the User Provider, depending on the registered plugin
	 * 
	 * @param fileExtension the fileExtension
	 * @return the UserProvider to keeptrack of user account persistence
	 */
	IProvider createGameProvider(String fileExtension)
	{
		return null;
		// use reflection API to instantiate the appropriate plug-in class
		
	}

	/**
	 * Unregister the User Provider
	 * 
	 * @param name the name of the plugin to unregister
	 */
	void unregisterGameProvider(String name)
	{
		
	}

	/**
	 * Loads a configuration from XML file
	 * 
	 * @param file the XML file to load
	 */
	void load(File file)
	{
		// load configuration from XML file
		
	}

	/**
	 * Saves a configuration to XML file
	 * 
	 * @param file the XML file to save the configuration
	 */
	void save(File file)
	{
		// save configuration to XML file
		
	}

}
