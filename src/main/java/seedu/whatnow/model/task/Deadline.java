package seedu.whatnow.model.task;

import seedu.whatnow.commons.util.CollectionUtil;
import seedu.whatnow.model.tag.UniqueTagList;

public class Deadline extends BasicTask{

	private Name name;
	private TaskDate taskDate;
	private UniqueTagList tags;

	public Deadline(Name name, TaskDate taskDate, UniqueTagList tags) {
		super(name);
		// TODO Auto-generated constructor stub
		assert !CollectionUtil.isAnyNull(name, tags);
		this.name = name;
		this.taskDate = taskDate;
		this.tags = new UniqueTagList(tags);
	}

	@Override
	public TaskDate getTask() {
		// TODO Auto-generated method stub
		return taskDate;
	}

	@Override
	public UniqueTagList getTags() {
		// TODO Auto-generated method stub
		return tags;
	}
	public void setTags(UniqueTagList replacement) {
		tags.setTags(replacement);
	}
	public void setTaskDate(TaskDate newDate) {
		taskDate.set(newDate);
	}
}
