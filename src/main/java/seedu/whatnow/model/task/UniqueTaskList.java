package seedu.whatnow.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.whatnow.commons.exceptions.DuplicateDataException;
import seedu.whatnow.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTaskException extends DuplicateDataException {
        protected DuplicateTaskException() {
            super("Operation would result in duplicate tasks");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class TaskNotFoundException extends Exception {}
    
    /**
     * Signals that there is no more previous commands that were entered
     */
    public static class NoPrevCommandFoundException extends Exception{}
    
    private static ObservableList<Task> internalList = FXCollections.observableArrayList();
    
    private static Stack<ObservableList<Task>> reqStack = new Stack<>();
    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        reqStack.add(internalList);
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        assert toRemove != null;
        reqStack.add(internalList);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }
    
    /**
     * Updates the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean update(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException {
        reqStack.add( internalList);
    	assert old != null;
        final boolean taskFoundAndUpdated = internalList.contains(old);
        if (!taskFoundAndUpdated) {
            throw new TaskNotFoundException();
        }
        internalList.set(internalList.indexOf(old), toUpdate);
        
        return taskFoundAndUpdated;
    }
    
    /**
     * Reverts back to the state before the previous command in WhatNow
     * @throws NoPrevCommandFoundException 
     * 
     */
    public boolean undo() throws NoPrevCommandFoundException{
    	
    	if(reqStack.isEmpty()) {
    		throw new NoPrevCommandFoundException();
    	}
    	else {
    		internalList = reqStack.pop();
    		return true;
    	}
    }
    public static ObservableList<Task> getInternalList() {
        return internalList;
    }
    
    /*
    private void ObservableList<Task> void setInternalList(ObservableList<Task> newInternalList) {
    	this.internalList = newInternalList;
    }*/
    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
