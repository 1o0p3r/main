package seedu.whatnow.logic.parser;

import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatnow.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.commands.*;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    private static final Pattern TASK_DATA_MODIFIED_ARGS_FORMAT =
    		Pattern.compile("(?<name>[^/]+)" +"\\s" 
    				+".*?\\bon|by\\b.*?" +"\\s" +
    				"(?<dateArguments>[^/]+)" +
    				"(?<tagArguments>(?: t/[^/]+)*)");		//This pattern is for e.g. add task on/by 12/10/2016
    														//add task on/by today
    private static final Pattern TASK_DATA_MODIFIED2_ARGS_FORMAT =
    		Pattern.compile("(?<name>[^/]+)" +"\\s" 
    				+".*?\\bon|by\\b.*?" +"\\s" +
    				"(?<dateArguments>[^/]+)" + "(?<dateArguments>[^/]+)" +
    				"(?<tagArguments>(?: t/[^/]+)*)");		//This pattern is for e.g. add task on/by 12 january
    private static final Pattern TASK_DATA_MODIFIED3_ARGS_FORMAT =
    		Pattern.compile("(?<name>[^/]+)" +"\\s" 
    				+".*?\\bon|by\\b.*?" +"\\s" +
    				"(?<dateArguments>[^/]+)" + "(?<dateArguments>[^/]+)" + "(?<dateArguments>[^/]+)" +
    				"(?<tagArguments>(?: t/[^/]+)*)");		//This pattern is for e.g. add task on/by 12 january 2016
    private static final int TASK_TYPE = 0;
    private static final int INDEX = 1;
    private static final int ARG_TYPE = 2;
    private static final int ARG = 3;
    

    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case UpdateCommand.COMMAND_WORD:
            return prepareUpdate(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_MODIFIED_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher2 = TASK_DATA_MODIFIED2_ARGS_FORMAT.matcher(args.trim());
        final Matcher matcher3 = TASK_DATA_MODIFIED3_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches() && !matcher2.matches() && !matcher3.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),
                    getDateFromArgs(matcher.group("dateArguments")),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private static set<String> getDateFromArgs(String dateArguments) throws IllegalValueException {
    	//no date
    	if(dateArguments.isEmpty()) {
    		
    		return Collections.emptySet();
    	}
    }
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCommand(index.get());
    }
    
    /**
     * Parses arguments in the context of the update task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUpdate(String args) {
        String[] ArgComponents= args.trim().split(" ");
        String type = ArgComponents[TASK_TYPE];
        int index = Integer.parseInt((ArgComponents[INDEX]));
        String argType = ArgComponents[ARG_TYPE];
        String arg = "";
        for (int i = ARG; i < ArgComponents.length; i++) {
            arg += ArgComponents[i] + " ";
        }
        // Validate arg string format
        if (!isValidUpdateCommandFormat(type, index, argType)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }
        
        try {
            return new UpdateCommand(
                type,
                index,
                argType,
                arg);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    private boolean isValidUpdateCommandFormat(String type, int index, String argType) {
        if (!(type.compareToIgnoreCase("todo") == 0 || type.compareToIgnoreCase("schedule") == 0)) {
            return false;
        }
        if (index < 0) {
            return false;
        }
        if (!(argType.compareToIgnoreCase("description") == 0 || argType.compareToIgnoreCase("tag") == 0 
                || argType.compareToIgnoreCase("date") == 0 || argType.compareToIgnoreCase("time") == 0)) {
            return false;
        }
        return true;
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

}