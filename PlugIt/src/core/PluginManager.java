/**
 * Core classes and interfaces
 */
package core;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import xmlutils.XMLUtils;

/**
 * @author DGalassi
 * All plug-ins made available through PlugIt are managed using this component.
 */
public class PluginManager {

	private String sPluginsDir = null;

	/**
	 * Class constructor
	 * @param sPluginsDir Absolute path where plugins are stored
	 */
	public PluginManager(String sPluginsDir) {
		this.sPluginsDir = sPluginsDir;
	}

	/**
	 * Validates the configuration file has at least 1 plugin to load
	 * @return A W3C DOM document of the configuration file. 
	 */
	private Document verifyConfig() {
		File		fConfig = new File(sPluginsDir + "/config/config.xml");
		Document	dConfig = null;

		if (fConfig.exists()) {
			dConfig = XMLUtils.File2Document(fConfig);
			if (dConfig.getElementsByTagName("Plugin").getLength() == 0)
				dConfig = null;
		}
		else
			System.out.println("Configuration File Doesn't Exist");

		return dConfig;
	}

	/**
	 * Validates that each plugin class implements core.Plugin interface
	 * @param c plugin to validate
	 * @return true if the plugin checks out
	 */
	private boolean implementsInt (Class [] c) {
		boolean passed = false;

		for (int i=0; i < c.length; i++)
			if (c[i].getName().matches("core.Plugin"))
				passed = true;

		return passed;
	}
	
	/**
	 * Plug-in loader class
	 * @return Number of plug-ins loaded.
	 */
	public int load() {
		Document		dConfig = verifyConfig();
		String			sPlugin = null;
		String			sPackage = null;
		NodeList		nlPlugins = null;
		NamedNodeMap	nPlugin = null;
		int				iPlugins = 0;
		File			fJARPlugin = null;
		URL				uJAR = null;
		URL[]			urls = null;
		ClassLoader		cl = null;
		Class			cls = null;
		Object			oP = null;

		//purging all previously loaded plugins
		if (PlugIt.vPlugins.size() != 0)
			unload();

		//configuration file not empty? then load the plugins!
		if (dConfig == null) 
			System.out.println("Empty configuration file!");
		else {
			nlPlugins = dConfig.getElementsByTagName("Plugin");
			iPlugins = nlPlugins.getLength();
			System.out.println("No of plugins to load: " + iPlugins);

			//picking up every plugin registered in the XML file
			for (int i = 0; i < iPlugins; i++) {
				nPlugin = nlPlugins.item(i).getAttributes();
				sPlugin = nPlugin.getNamedItem("name").getNodeValue();
				sPackage = nPlugin.getNamedItem("package").getNodeValue();

				try {
					//dynamically creating a new instance of the plugin
					fJARPlugin = new File(sPluginsDir + sPlugin + ".jar");
					uJAR = fJARPlugin.toURI().toURL();
					urls = new URL[]{uJAR};
					cl = new URLClassLoader(urls);
					cls = cl.loadClass(sPackage + "." + sPlugin);
					oP = cls.newInstance();

					//adding the plugin if it checks out
					if (implementsInt(oP.getClass().getInterfaces())) {
						//creating the instance if not defined before
						if (PlugIt.vPlugins == null)
							PlugIt.vPlugins = new Vector <Object> ();
						PlugIt.vPlugins.add(oP);
						System.out.println("Plugin loaded: " + 
								oP.getClass().getName());
					}
					else 
						System.out.println("Plugin interface missing");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		oP = null;
		dConfig = null;
		sPlugin = null;
		sPackage = null;
		nlPlugins = null;
		nPlugin = null;
		fJARPlugin = null;
		uJAR = null;
		urls = null;
		cl = null;
		cls = null;
		return PlugIt.vPlugins.size();
	}

	/**
	 * Plug-ins cleanup
	 * @return Number of plug-ins yet to be unloaded.
	 */
	public int unload() {
		System.out.println("Unloading plugins.");
		if (PlugIt.vPlugins.size() > 0) {
			PlugIt.vPlugins.removeAllElements();
		}
		System.out.println("Plugins unloaded.");
		return PlugIt.vPlugins.size();
	}

	/**
	 * Wrapper when plug-in reloads are required
	 * @return Number of plug-ins reloaded.
	 */
	public int reload() {
		System.out.println("Reloading plugins.");
		if (unload() == 0)
			load();
		System.out.println("Plugins reloaded.");
		return PlugIt.vPlugins.size();
	}

	/**
	 * Prints the names of the plug-ins loaded
	 */
	public void listPlugins() {
		for (int i=0; i<PlugIt.vPlugins.size(); i++)
			System.out.println(PlugIt.vPlugins.get(0).getClass().getName());
	}

}
