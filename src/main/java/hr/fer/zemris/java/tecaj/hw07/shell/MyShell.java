package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkDirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class which represents shell. Bash shell in Linux, CMD in Windows.
 *
 * @author Boris
 * @version 1.0
 */
public class MyShell {

    /**
     * Commands of the shell are stored in map.
     */
    private static Map<String, ShellCommand> commands;
    private static Environment environment = new EnvironmentImpl();

    static {
        commands = new HashMap<>();
        ShellCommand[] cc = {
            new CatShellCommand(),
            new CharsetsShellCommand(),
            new CopyShellCommand(),
            new ExitShellCommand(),
            new HelpShellCommand(),
            new HexDumpShellCommand(),
            new LsShellCommand(),
            new MkDirShellCommand(),
            new SymbolShellCommand(),
            new TreeShellCommand()
        };
        for (ShellCommand c : cc) {
            commands.put(c.getCommandName(), c);
        }
    }

    /**
     * Main method.
     *
     * @param args arguments of command line.
     */
    public static void main(String[] args) {

        environment.writeln("Welcome to MyShell! You may enter commands.");
        while (true) {
            StringBuilder sb = new StringBuilder();
            System.out.print(ShellSymbol.getSymbol("PROMPT") + " ");
            String line = environment.readLine();
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("EXIT")) break;
            sb.append(line.replaceFirst("\\s+\\\\", " "));

            while (line.trim().endsWith(" " + String.valueOf(ShellSymbol.getSymbol("MORELINES")))) {
                environment.write(ShellSymbol.getSymbol("MULTILINE") + " ");
                line = environment.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) break;
                sb.append(line.replaceFirst("\\s+\\\\", " "));
            }

            String[] split = Utility.customSplit(sb.toString().trim());

            String cmd = split[0].toUpperCase();
            String arg = split[1];

            ShellCommand shellCommand = MyShell.commands.get(cmd);
            if (shellCommand == null) {
                environment.writeln("Unknown command!");
                continue;
            }
            try {
                if (shellCommand.execute(environment, arg) == ShellStatus.EXIT) {
                    break;// izvrsi naredbu; ako vrati EXIT break;
                }
            } catch (IllegalArgumentException iae) {
                environment.writeln(iae.getMessage());
            }
        }

        environment.writeln("Thank you for using this shell. Goodbye!");
    }

    /**
     * An implementation of Environment interface.
     *
     * @author Formula1
     */
    public static class EnvironmentImpl implements Environment {

        private BufferedReader reader;
        private BufferedWriter writer;

        public EnvironmentImpl() {
            this.reader = new BufferedReader(new InputStreamReader(System.in,
                StandardCharsets.UTF_8));
            this.writer = new BufferedWriter(new OutputStreamWriter(System.out,
                StandardCharsets.UTF_8));
        }

        @Override
        public String readLine() {
            String r = null;
            try {
                r = reader.readLine();
            } catch (IOException e) {
                System.err.println("Error with input buffer.");
            }
            return r;
        }

        @Override
        public void write(String text) {
            try {
                writer.write(text);
                writer.flush();
            } catch (IOException e) {
                System.err.println("Error with output buffer.");
            }
        }

        @Override
        public void writeln(String text) {
            try {
                writer.write(text);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.err.println("Error with output buffer.");
            }
        }

        @Override
        public Iterable<? extends ShellCommand> commands() {
            return new Iterable<ShellCommand>() {

                @Override
                public Iterator<ShellCommand> iterator() {
                    return new Iterator<ShellCommand>() {

                        Iterator<ShellCommand> iterator = commands.values().iterator();

                        @Override
                        public boolean hasNext() {
                            return iterator.hasNext();
                        }

                        @Override
                        public ShellCommand next() {
                            return iterator.next();
                        }

                        @Override
                        public void remove() {
                            iterator.remove();
                        }
                    };
                }
            };
        }

        @Override
        public Character getMultilineSymbol() {
            return ShellSymbol.getSymbol("MULTILINE");
        }

        @Override
        public void setMultilineSymbol(Character symbol) {
            ShellSymbol.setSymbol("MULTILINE", symbol);
        }

        @Override
        public Character getPromptSymbol() {
            return ShellSymbol.getSymbol("PROMPT");
        }

        @Override
        public void setPromptSymbol(Character symbol) {
            ShellSymbol.setSymbol("PROMPT", symbol);
        }

        @Override
        public Character getMorelinesSymbol() {
            return ShellSymbol.getSymbol("MORELINES");
        }

        @Override
        public void setMorelinesSymbol(Character symbol) {
            ShellSymbol.setSymbol("MORELINES", symbol);
        }
    }
}
