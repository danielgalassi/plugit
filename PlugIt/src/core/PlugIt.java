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
	public //static 
	void run() {
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
	public //static 
	Vector <String> getResults(int i) {
		Vector <String> vResults = null;
		
		if (i < vPlugins.size()) {
			vResults = new Vector <String> ();
			vResults.add(vPlugins.get(i).getName());
			vResults.add(vPlugins.get(i).getDescription());
			vResults.add(vPlugins.get(i).passed()+ "");
		}

		return vResults;
	}

	/**
	 * 
	 * @return
	 */
	public //static 
	int size() {
		return vPlugins.size();
	}
	
	/**
	 * 
	 * @param sPluginsDir
	 */
	public //static 
	void test(String sPluginsDir) {
		PluginManager pm = new PluginManager(sPluginsDir);
		pm.load();
		run();
		for (int i=0; i<size(); i++)
			getResults(0);
		pm.unload();
		pm = null;
	}

	/**
	 * 
	 * @param args
	 */
	//public static void main(String[] args) {
	//	test("C:\\job\\workspace\\PlugIt\\plugins\\");
	//}

}
