import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author glasnier
 *
 */
public aspect Observability {

    protected interface IInternalObservable extends IObservable {
    }

    declare parents : (@Observable *) implements IInternalObservable;

    private transient PropertyChangeSupport IInternalObservable.pcs;

    /**
     *
     */
    private PropertyChangeSupport IInternalObservable.getPcs() {
        if (pcs == null) {
            synchronized (this) {
                if (pcs == null) {
                    pcs = new PropertyChangeSupport(this);
                }
            }
        }
        return pcs;
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is
     * registered for all properties and its {@code propertyChange} method will
     * run on the event dispatching thread.
     * <p>
     * If {@code listener} is null, no exception is thrown and no action is taken.
     *
     * @param listener
     *            the PropertyChangeListener to be added.
     * @see #removePropertyChangeListener
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener
     */
    public void IInternalObservable.addPropertyChangeListener(PropertyChangeListener listener) {
        getPcs().addPropertyChangeListener(listener);
    }

    /**
     * Remove a PropertyChangeListener from the listener list.
     * <p>
     * If {@code listener} is null, no exception is thrown and no action is taken.
     *
     * @param listener
     *            the PropertyChangeListener to be removed.
     * @see #addPropertyChangeListener
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener
     */
    public void IInternalObservable.removePropertyChangeListener(PropertyChangeListener listener) {
        getPcs().removePropertyChangeListener(listener);
    }

    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only when a call on
     * firePropertyChange names that specific property. The same listener object may be added more than once.
     * For each property, the listener will be invoked the number of times it was added for that property.
     * If <code>propertyName</code> or <code>listener</code> is null, no exception is thrown and no action is taken.
     *
     * @param propertyName
     *            The name of the property to listen on.
     * @param listener
     *            the PropertyChangeListener to be added
     * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
     */
    public void IInternalObservable.addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPcs().addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a PropertyChangeListener for a specific property. If <code>listener</code> was added more than once to the
     * same event source for the specified property, it will be notified one less time after being removed.
     * If <code>propertyName</code> is null, no exception is thrown and no action is taken. If <code>listener</code> is
     * null, or was never added for the specified property, no exception is thrown and no action is taken.
     *
     * @param propertyName
     *            The name of the property that was listened on.
     * @param listener
     *            The PropertyChangeListener to be removed
     * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
     */
    public synchronized void IInternalObservable.removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        getPcs().removePropertyChangeListener(propertyName, listener);
    }

    /**
     * An array of all of the {@code PropertyChangeListeners} added so far.
     *
     * @return all of the {@code PropertyChangeListeners} added so far.
     * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners
     */
    public PropertyChangeListener[] IInternalObservable.getPropertyChangeListeners() {
        return getPcs().getPropertyChangeListeners();
    }

    /**
     * Called whenever the value of a bound property is set.
     * <p>
     * If oldValue is not equal to newValue, invoke the {@code propertyChange} method on all of the
     * {@code PropertyChangeListeners} added so far, on the event dispatching thread.
     *
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see java.beans.PropertyChangeSupport#firePropertyChange(String, Object, Object)
     */
    public void IInternalObservable.firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (oldValue == null && newValue == null ||
                oldValue != null && newValue != null && oldValue.equals(newValue)) {
            return;
        }
        getPcs().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Fire an existing PropertyChangeEvent
     * <p>
     * If the event's oldValue property is not equal to newValue, invoke the {@code propertyChange} method on all of the
     * {@code PropertyChangeListeners} added so far, on the event dispatching thread.
     *
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     * @see java.beans.PropertyChangeSupport#firePropertyChange(PropertyChangeEvent e)
     */
    public void IInternalObservable.firePropertyChange(PropertyChangeEvent e) {
        getPcs().firePropertyChange(e);
    }

}
