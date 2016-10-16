package seedu.whatnow.logic.commands;

import java.util.Stack;

import javafx.collections.transformation.FilteredList;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;

public class UndoCommand extends Command{


	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverts to the state of WhatNow"
			+ "Parameters: None\n"
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Undone Successfully!";
	public static final String MESSAGE_DUPLICATE_TASK = "Previous Command does not exist";
	private Stack<FilteredList<Task>> tempTasks;

	public UndoCommand() {
		tempTasks = new Stack<FilteredList<Task>>();
	}
	@Override
	public CommandResult execute() {
		// TODO Auto-generated method stub
		assert model != null;
		try{
			model.undoCommand();
			return new CommandResult(String.format(MESSAGE_SUCCESS, tempTasks));
		}catch (UniqueTaskList.NoPrevCommandFoundException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
	}	

}
