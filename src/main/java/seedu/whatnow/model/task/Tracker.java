package seedu.whatnow.model.task;

public class Tracker {
	private String command;
	private Task task;
	public Tracker(String command, Task task) {
		this.command = command;
		this.task = task;
	}
	public String getCommandString() {
		return this.command;
	}
	public Task getTask() {
		return this.task;
	}
}
