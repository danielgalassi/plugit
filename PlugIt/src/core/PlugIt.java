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

	protected static Vector <Plugin> vPlugins = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PluginManager pm = new PluginManager("C:\\job\\workspace\\PlugIt\\plugins\\");
		pm.load();
		if (vPlugins.size() > 0) {
			
		}
		pm.unload();
		pm = null;
	}

}
