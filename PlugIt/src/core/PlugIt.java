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
	 * 
	 */
	public static void run() {
		for (int i=0; i < vPlugins.size(); i++) {
			if (!vPlugins.get(i).getStatus().matches("Ready"))
				vPlugins.get(i).reset();
			System.out.println(vPlugins.get(i).getStatus());
			vPlugins.get(i).run();
			System.out.println(vPlugins.get(i).passed());
			System.out.println(vPlugins.get(i).getStatus());
		}
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public static String getHTMLResults(int i) {
		String sHTMLres = "";
		if (i < vPlugins.size())
		sHTMLres = "<td>" + vPlugins.get(i).getName() + "</td>" +
					"<td>" + vPlugins.get(i).getDescription() + "</td>" +
					"<td>" + vPlugins.get(i).passed() + "</td>";
		return sHTMLres;
	}

	/**
	 * 
	 * @return
	 */
	public static int size() {
		return vPlugins.size();
	}
	
	/**
	 * 
	 * @param sPluginsDir
	 */
	public static void test(String sPluginsDir) {
		PluginManager pm = new PluginManager(sPluginsDir);
		pm.load();
		run();
		for (int i=0; i<size(); i++)
			getHTMLResults(0);
		pm.unload();
		pm = null;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		test("C:\\job\\workspace\\PlugIt\\plugins\\");
	}

}
