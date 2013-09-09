/**
 * Core classes and interfaces
 */
package core;

/**
 * @author danielgalassi@gmail.com
 *
 */
public interface Plugin {

	public boolean isReady();
	public String getName();
	public String getDescription();
	public String getResNotes();
	public String getStatus();
	public boolean passed();
	public void reset();
	public void run();

}
