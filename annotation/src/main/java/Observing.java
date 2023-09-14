import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <a href="http://www.eclipse.org/aspectj/">AspectJ</a> aspect that advises
 * classes annoted with {@link IObservable}.
 *
 * @author glasnier
 */
@Aspect
public class Observing {

    // class implements IObservable and is not @NotObservable
    @Pointcut("within(IObservable+ && !@NotObservable *)")
    void observableType() {
    }

    // method is not @NotObservable and is not static
    // excludes constructors and initialization
    @Pointcut("withincode(!@NotObservable !static * * (..))")
    void observableMethod() {
    }

    // constructor is not @NotObservable
    @Pointcut("withincode(!@NotObservable new (..))")
    void observableConstructor() {
    }

    // setting a class field that is not @NotObservable and is not static
    @Pointcut("set(!@NotObservable !static * IObservable+.*)")
    void observableSetter() {
    }

    // setting a class field (of type List) that is not @NotObservable and is not static
    @Pointcut("set(!@NotObservable !static java.util.List+ IObservable+.*)")
    void observableListSetter() {
    }

    // setting a class field (of type Map) that is not @NotObservable and is not static
    @Pointcut("set(!@NotObservable !static java.util.Map+ IObservable+.*)")
    void observableMapSetter() {
    }

    /**
     *
     * @param bean
     * @param value
     */
    @Pointcut("observableType() && observableMethod() && observableSetter() && this(bean) && args(value)")
    void setter(Object bean, Object value) {
    }

    /**
     *
     * @param bean
     * @param list
     */
    @Pointcut("observableType() && (observableMethod() || observableConstructor()) && observableListSetter() && this(bean) && args(list)")
    void setterList(IObservable bean, List list) {
    }

    /**
     *
     * @param bean
     * @param map
     */
    @Pointcut("observableType() && observableMapSetter() && this(bean) && args(map)")
    void setterMap(IObservable bean, Map map) {
    }

    /**
     *
     * @param thisJoinPoint
     * @param bean
     * @param value
     * @throws Throwable
     */
    @Around("setter(bean, value) && !withincode(!static * IObservable+.get*())")
    public void aroundSetter(ProceedingJoinPoint thisJoinPoint, Object bean, Object value) throws Throwable {
        String propertyName = thisJoinPoint.getSignature().getName();
        /*if (PropertyUtils.isReadable(bean, propertyName)) {
            // property is readable -> firePropertyChange
            Class declaringType = thisJoinPoint.getSignature().getDeclaringType();
            Object oldValue = this.getValueForProperty(bean, declaringType, propertyName);
            thisJoinPoint.proceed(new Object[] { bean, value });
            ((IObservable)bean).firePropertyChange(propertyName, oldValue, value);
        } else {
            // property is not readable -> no need to firePropertyChange
            thisJoinPoint.proceed(new Object[] { bean, value });
        }*/
    }

    /**
     *
     * @param thisJoinPoint
     * @param bean
     * @param list
     * @throws Throwable
     */
    @Around("setterList(bean, list)")
    public void aroundSetterList(ProceedingJoinPoint thisJoinPoint, IObservable bean, List list) throws Throwable {
        thisJoinPoint.proceed(new Object[] { bean, list });
    }

    /**
     *
     * @param thisJoinPoint
     * @param bean
     * @param map
     * @throws Throwable
     */
    @Around("setterMap(bean, map)")
    public void aroundSetterMap(ProceedingJoinPoint thisJoinPoint, IObservable bean, Map map) throws Throwable {
        thisJoinPoint.proceed(new Object[] { bean, map });
    }

    /**
     *
     * @param bean
     * @param propertyName
     * @return
     */
    private Object getValueForProperty(Object bean, Class<?> declaringType, String propertyName) {
        Object value;
        try {
            Field field = declaringType.getDeclaredField(propertyName);
            AccessibleObject.setAccessible(new AccessibleObject[] { field }, true);
            value = field.get(bean);
        } catch (Throwable t) {
            System.err.println("Could not retrieve value for property " + propertyName + ": " + t.getMessage());
            value = new Object();
        }
        return value;
    }

}
