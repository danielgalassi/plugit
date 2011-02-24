/**
 * Core classes and interfaces
 */
package core;

/**
 * @author DGalassi
 *
 */
public interface Plugin {

	public boolean isReady();
	public String getName();
	public String getDescription();
	public String getStatus();
	public boolean passed();
	public void reset();
	public void run();

}
