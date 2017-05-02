package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class which represents implementation of ShellCommand
 * interface.
 *
 * @author Boris
 */
public abstract class AbstractCommand implements ShellCommand {

    private String commandName;
    private String commandDescription;

    /**
     * Constructor method which creates new command with given
     * name and description.
     *
     * @param name given name
     * @param description given decscription
     */
    public AbstractCommand(String name, String description) {
        this.commandName = name;
        this.commandDescription = description;
    }

    @Override
    public String getCommandName() {
        return this.commandName;
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add(commandName);
        description.add(commandDescription);
        return Collections.unmodifiableList(description);
    }
}
