package seedu.whatnow.model;

import java.util.Set;
import java.util.Stack;

import javafx.collections.transformation.FilteredList;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;

import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.NoPrevCommandFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWhatNow newData);

    /** Returns the WhatNow */
    ReadOnlyWhatNow getWhatNow();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Update the given task */
    void updateTask(ReadOnlyTask old, Task toUpdate) throws UniqueTaskList.TaskNotFoundException;
    
    /** Undo Previous Command 
     * @throws NoPrevCommandFoundException */
    void undoCommand(Stack<FilteredList<Task>> stackOfFilteredTasks) throws NoPrevCommandFoundException;
}
