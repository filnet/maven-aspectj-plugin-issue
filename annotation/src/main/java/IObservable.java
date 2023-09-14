import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * An {@code IObservable} bean. An observable bean :
 * <ul>
 * <li>register/unregsiters {@link PropertyChangeListener},</li>
 * <li>fires {@link PropertyChangeEvent} to all its registered listeners when a
 * bean property has changed.</li>
 * </ul>
 *
 * @author glasnier
 */
public interface IObservable {

    /**
     * Registers a listener to receive property change events for any property
     * of this object.
     *
     * @param valueChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener valueChangeListener);

    /**
     *
     * Registers a listener to receive property change events for the specified
     * property of this object.
     *
     * @param propertyName
     * @param valueChangeListener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener valueChangeListener);

    /**
     * Unregisters a listener to receive property change events for any property
     * of this object.
     *
     * @param valueChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener valueChangeListener);

    /**
     * Unregisters a listener to receive property change events for the
     * specified property.
     *
     * @param propertyName
     * @param valueChangeListener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener valueChangeListener);

    /**
     * An array of all of the {@code PropertyChangeListeners} added so far.
     *
     * @return all of the {@code PropertyChangeListeners} added so far.
     * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners
     */
    public PropertyChangeListener[] getPropertyChangeListeners();

    /**
     * Called whenever the value of a bound property is set.
     * <p>
     * If oldValue is not equal to newValue, invoke the {@code propertyChange}
     * method on all of the {@code PropertyChangeListeners} added so far, on the
     * event dispatching thread.
     *
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see java.beans.PropertyChangeSupport#firePropertyChange(String, Object,
     *      Object)
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue);

    /**
     * Fire an existing PropertyChangeEvent
     * <p>
     * If the event's oldValue property is not equal to newValue, invoke the
     * {@code propertyChange} method on all of the {@code
     * PropertyChangeListeners} added so far, on the event dispatching thread.
     *
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see java.beans.PropertyChangeSupport#firePropertyChange(PropertyChangeEvent
     *      e)
     */
    public void firePropertyChange(PropertyChangeEvent e);
}
