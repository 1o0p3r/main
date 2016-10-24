package seedu.whatnow.testutil;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }
    
    public TaskBuilder withStatus(String status) throws IllegalValueException {
        this.task.setStatus(status);
        return this;
    }
    
    public TaskBuilder withTaskType(String taskType) throws IllegalValueException {
        this.task.setTaskType(taskType);
        return this;
    }
    
    public TaskBuilder withStartDate(String date) throws IllegalValueException {
        this.task.setDate(date);
        return this;
    }
    
    public TaskBuilder withEndDate(String date) throws IllegalValueException {
        this.task.setDate(date);
        return this;
    }
    
    
    public TestTask build() {
        return this.task;
    }

}
