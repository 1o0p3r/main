package seedu.whatnow.logic.commands;

import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.NoPrevCommandException;

public class UndoCommand extends Command{

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo previous task in WhatNow "
			+ "Parameters: No parameters"
			+ "Example: " + COMMAND_WORD;

	public static final String MESSAGE_SUCCESS = "Undo Successfully";
	public static final String MESSAGE_FAIL = "Undo failure due to unexisting previous commands";

	public UndoCommand() {

	}
	@Override
	public CommandResult execute() {
		assert model != null;
		if(model.getUndoStack().isEmpty()) {
			return new CommandResult(MESSAGE_FAIL);
		}
		else {
			UndoAndRedo reqCommand = (UndoAndRedo) model.getUndoStack().pop();
			return reqCommand.undo();
			//return new CommandResult(String.format(MESSAGE_SUCCESS));
		}
	}
}
