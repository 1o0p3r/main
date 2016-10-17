package seedu.whatnow.model.task;

import java.util.Objects;

import seedu.whatnow.commons.util.CollectionUtil;
import seedu.whatnow.model.tag.UniqueTagList;

/**
 * Represents a Task in WhatNow.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    
    private TaskDate taskDate;
    
    private UniqueTagList tags;
    
    private String status;

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD
    public Task(Name name, TaskDate taskDate, UniqueTagList tags) {
=======
    public Task(Name name, UniqueTagList tags, String status) {
>>>>>>> 8bc3a7ac4fe132323e728c8ac0560d00ad792005
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.taskDate = taskDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.status = status;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
<<<<<<< HEAD
        this(source.getName(), source.getTask(), source.getTags());
    }
    
    @Override
    public TaskDate getTask() {
    	return taskDate;
=======
        this(source.getName(), source.getTags(), source.getStatus());
>>>>>>> 8bc3a7ac4fe132323e728c8ac0560d00ad792005
    }
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public String getStatus() {
        return status;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    public void setName(Name name) {
        this.name = name;
    }
<<<<<<< HEAD
    public void setDate(TaskDate taskDate) {
    	this.taskDate = taskDate;
=======
    
    public void setStatus(String status) {
        this.status = status;
>>>>>>> 8bc3a7ac4fe132323e728c8ac0560d00ad792005
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
