/**
 * Core classes and interfaces
 */
package core;

import java.util.Vector;

/**
 * @author DGalassi
 * Yes, this is a limited functionality plugin-based execution engine.
 */
public class PlugIt {

	protected static Vector <Plugin> vPlugins = new Vector <Plugin> ();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test("C:\\job\\workspace\\PlugIt\\plugins\\");
	}

	public static void test(String sPluginsDir) {
		PluginManager pm = new PluginManager(sPluginsDir);
		pm.load();
		if (vPlugins.size() > 0) {

		}
		pm.unload();
		pm = null;
	}

}
