//@@author A0126240W
package guitests;

import org.junit.Test;
import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.testutil.TestUtil;
import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.logic.commands.UpdateCommand;
import seedu.whatnow.model.task.Name;

import static org.junit.Assert.assertTrue;
import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatnow.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;

import java.util.Arrays;

public class UpdateCommandTest extends WhatNowGuiTest {

    @Test
    public void update() throws IllegalValueException {
        //invalid command
        commandBox.runCommand("updates Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid update format
        commandBox.runCommand("update hello");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        commandBox.runCommand("update todo hello");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        commandBox.runCommand("update schedule 1 hello");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        
        TestTask[] currentList = td.getTypicalTasks();
        
        //update description   
        int targetIndex = 1;
        Arrays.sort(currentList);
        assertUpdateSuccess(targetIndex, currentList, "description");
        
        //update time   
        targetIndex = 3;
        Arrays.sort(currentList);
        assertUpdateSuccess(targetIndex, currentList, "time");
    }
    
    /**
     * Runs the update command to update the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to update the first task in the list, 1 should be given as the target index.
     * @param task The updated task
     * @param currentList A copy of the current list of tasks (before deletion).
     * @throws IllegalValueException 
     */
    private void assertUpdateSuccess(int targetIndexOneIndexed, TestTask[] currentList, String field) throws IllegalValueException {
        TestTask taskToUpdate = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask toBeUpdatedTo = new TestTask();
        toBeUpdatedTo.setName(taskToUpdate.getName());
        toBeUpdatedTo.setTaskDate(taskToUpdate.getTaskDate());
        toBeUpdatedTo.setStartDate(taskToUpdate.getStartDate());
        toBeUpdatedTo.setEndDate(taskToUpdate.getEndDate());
        toBeUpdatedTo.setTaskTime(taskToUpdate.getTaskTime());
        toBeUpdatedTo.setStartTime(taskToUpdate.getStartTime());
        toBeUpdatedTo.setEndTime(taskToUpdate.getEndTime());
        toBeUpdatedTo.setPeriod(taskToUpdate.getPeriod());
        toBeUpdatedTo.setEndPeriod(taskToUpdate.getEndPeriod());
        toBeUpdatedTo.setTags(taskToUpdate.getTags());
        toBeUpdatedTo.setStatus(taskToUpdate.getStatus());
        toBeUpdatedTo.setTaskType(taskToUpdate.getTaskType());
        
        if (field.equals("description")) {
            toBeUpdatedTo.setName(new Name("Buy milk"));
            commandBox.runCommand("update schedule " + targetIndexOneIndexed + " " + field + " " + toBeUpdatedTo.getName());
        } else if (field.equals("date")) {
            toBeUpdatedTo.setTaskDate(null);
            toBeUpdatedTo.setStartDate("23/02/2024");
            toBeUpdatedTo.setEndDate("25/02/2024");
            commandBox.runCommand("update schedule " + targetIndexOneIndexed + " " + field + " " + toBeUpdatedTo.getStartDate() + " to " + toBeUpdatedTo.getEndDate());
        } else if (field.equals("time")) {
            toBeUpdatedTo.setTaskTime("12:30pm");
            toBeUpdatedTo.setStartTime(null);
            toBeUpdatedTo.setEndTime(null);
            commandBox.runCommand("update schedule " + targetIndexOneIndexed + " " + field + " " + toBeUpdatedTo.getTaskTime());
        }

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toBeUpdatedTo));
    }
}
