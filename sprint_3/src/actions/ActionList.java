package actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Helper class used to represent and store a list of Actions.
 */

public class ActionList implements Iterable<Action> {

    private ArrayList<Action> actions = new ArrayList<Action>();

    /**
     * Constructs an empty list.
     */
    public ActionList() {}

    /**
     * Appends the contents of another Actions list to this one.
     * @param actions the Actions to append
     */
    public void add(ActionList actions) {
        for(Action action : actions) {
            add(action);
        }
    }

    /**
     * Appends the contents of any List&lt;Action&gt; to this one.
     * This overload allows the use of an unmodifiableList to prevent privacy leaks.
     * @param actions the List&lt;Action&gt; to append
     */
    public void add(List<Action> actions) {
        for (Action action : actions) {
            add(action);
        }
    }

    /**
     * Appends a single Action to this collection if it is non-null. If it is null, then it is ignored.
     * @param action the Action to append
     * @return true unconditionally
     */
    public boolean add(Action action) {
        if (action != null) {
            actions.add(action);
        }
        return true;
    }

    /**
     * Returns an Iterator for the underlying collection.
     * Implementing this method means that Actions implements the Iterable interface, which allows
     * you to use it in a foreach, e.g. <code>for (Action a: actions) {
     * @return an iterator
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Action> iterator() {
        return Collections.unmodifiableList(actions).iterator();
    }

    /**
     * Return the <code>i</code>'th Action in the collection.
     * @param i index of the Action to retrieve
     * @return the <code>i</code>'th Action in the collection
     * @throws IndexOutOfBoundsException when <code>i</code> &gt;= <code>this.size()</code>
     */
    public Action get(int i) {
        return actions.get(i);
    }

    /**
     * Count the number of Actions in the collection.
     * @return the number of Actions in the collection.
     */
    public int size() {
        return actions.size();
    }
}