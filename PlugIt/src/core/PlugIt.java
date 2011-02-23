/**
 * Core classes and interfaces
 */
package core;

/**
 * @author DGalassi
 * Yes, this is a limited functionality plugin-based execution engine.
 */
public class PlugIt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PluginManager pm = new PluginManager("C:\\job\\workspace\\PlugIt\\plugins\\");
		pm.load();
		pm.unload();
		pm = null;
	}

}
