package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellSymbol;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;

/**
 * Command which is used in {@link MyShell} to change {@link ShellSymbol}.<br>
 *
 * By default {@link MyShell} contains:
 * <ul>
 * <li>PROMPT symbol - <code>'>'</code></li>
 * <li>MORELINES symbol - <code>'\'</code></li>
 * <li>MULTILINE symbol - <code>'|'</code></li>
 * </ul>
 *
 * @author Boris
 */
public class SymbolShellCommand extends AbstractCommand {

    /**
     * Creates new instance of symbol command.
     */
    public SymbolShellCommand() {
        super("SYMBOL", "");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        String[] arguments = Utility.customSplit(args);

        if (arguments[0] == null) {
            throw new IllegalArgumentException(
                "Symbol command expects at least one argument: symbol name." +
                    "And if second argument is given it will be set as a new symbol for " +
                    "given symbol name."
            );
        }

        String symbolName = arguments[0];

        if (arguments[1] == null) {
            // print symbol
            char symbol = ShellSymbol.getSymbol(symbolName);
            e.writeln("Symbol for " + symbolName + " is \'" + symbol + "\'");
        } else {
            char newSymbol = arguments[1].charAt(0);
            char oldSymbol = ShellSymbol.getSymbol(symbolName);
            ShellSymbol.setSymbol(symbolName, newSymbol);
            e.writeln(
                "Symbol for " + symbolName + " changed from \'"
                    + oldSymbol + "\' to \'" + newSymbol + "\'"
            );
        }

        return ShellStatus.CONTINUE;
    }
}
