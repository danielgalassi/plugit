/**
 * Core classes and interfaces
 */
package core;

import java.lang.reflect.Method;
import java.util.Vector;

/**
 * @author DGalassi
 * Yes, this is a limited functionality plug-in based execution engine.
 */
public class PlugIt {

	protected static Vector <Object> vPlugins = new Vector <Object> ();
	protected PluginManager pm = null;

	/**
	 * Executes sequencially each plugin
	 */
	public void run() {
		String sStat = null;
		Method m1 = null;
		Method m2 = null;
		Method m3 = null;
		Method m4 = null;
		Object o = null;

		for (int i=0; i < vPlugins.size(); i++) {
			try {
				o = vPlugins.get(i);
				m1 = o.getClass().getMethod("getStatus");
				m2 = o.getClass().getMethod("reset");
				m3 = o.getClass().getMethod("run");
				m4 = o.getClass().getMethod("passed");
				sStat = m1.invoke(vPlugins.get(i)).toString();
				if (!sStat.matches("Ready"))
					m2.invoke(o);
				m3.invoke(o);
				//prints true if the test passed
				System.out.println(m4.invoke(o));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		o = null;
		m1 = null;
		m2 = null;
		m3 = null;
		m4 = null;
		sStat = null;
	}

	/**
	 * Get the name of the plug-in, its description, results obtained and flag
	 * @param i plug-in index
	 * @return four literals wrapped using Vector
	 */
	public Vector <String> getResults(int i) {
		Vector <String> vResults = null;
		Method m1 = null;
		Method m2 = null;
		Method m3 = null;
		Method m4 = null;
		Object o = null;

		if (i < vPlugins.size()) {
			o = vPlugins.get(i);
			try {
				m1 = o.getClass().getMethod("getName");
				m2 = o.getClass().getMethod("getDescription");
				m3 = o.getClass().getMethod("passed");
				m4 = o.getClass().getMethod("getResNotes");

				vResults = new Vector <String> ();
				vResults.add(m1.invoke(o).toString());
				vResults.add(m2.invoke(o).toString());
				vResults.add(m3.invoke(o) + "");
				vResults.add(m4.invoke(o).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return vResults;
	}

	/**
	 * Exposes the number of available plug-ins
	 * @return
	 */
	public int size() {
		return vPlugins.size();
	}

	/**
	 * Unit test method
	 * @param sPluginsDir absolute path where plug-ins are stored 
	 */
	public void test(String sPluginsDir) {
		pm = new PluginManager(sPluginsDir);
		pm.load();
	}

	/**
	 * Unloads all plug-ins
	 */
	public void cleanup() {
		pm.unload();
		pm = null;
	}

	/**
	 * Constructor
	 * @param sPluginsDir Absolute path 
	 */
	public PlugIt(String sPluginsDir) {
		pm = new PluginManager(sPluginsDir);
		pm.load();
	}
}
