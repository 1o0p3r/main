package seedu.whatnow.logic.commands;

import java.util.Stack;

import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.Tracker;

public class UndoCommand extends Command{


    public static final String COMMAND_WORD = "Undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reverts to the state of WhatNow"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undone Successfully!";
    public static final String MESSAGE_DUPLICATE_TASK = "Previous Command does not exist";

    private Stack<Tracker> stackOfTracker;
	@Override
	public CommandResult execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
