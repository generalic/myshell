package testing;

import hr.fer.zemris.java.tecaj.hw07.shell.ShellSymbol;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MultiLineSupportTD {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            StringBuilder sb = new StringBuilder();

            System.out.print(ShellSymbol.getSymbol("PROMPT") + " ");
            List<String> arguments = new ArrayList<>();
            String line = reader.readLine();
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("EXIT")) break;
            arguments.add(line);
            sb.append(line.replaceFirst("\\s+\\\\", " "));

            while (line.trim().endsWith(" " + String.valueOf(ShellSymbol.getSymbol("MORELINES")))) {
                System.out.print(ShellSymbol.getSymbol("MULTILINE") + " ");
                line = reader.readLine();
                if (line == null) continue;
                line = line.trim();
                if (line.isEmpty()) continue;
                sb.append(line.replaceFirst("\\s+\\\\", " "));
                arguments.add(line);
            }

            //			arguments.stream()
            //			.forEach(t -> sb.append(t.replaceFirst("\\s+\\\\", " ")));

            System.out.println(sb.toString());

            System.out.println();
            System.out.println(Utility.customSplit(sb.toString())[0]);
            System.out.println(Utility.customSplit(sb.toString())[1]);
            System.out.println();

            arguments.stream().forEach(t -> System.out.println(t));
        }
    }
}
