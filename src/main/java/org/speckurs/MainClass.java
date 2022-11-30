package org.speckurs;

import org.speckurs.parser.FileParser;
import org.speckurs.presenter.Presenter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClass {
    private static Logger log = Logger.getLogger(MainClass.class.getName());

    public static void main(String[] args) {
        if (args.length != 1) {
            log.info("only 1 arg should be passed to the programm - fileName in resources location");
            return;
        }

        Map<String, Map<String, List<String>>> programStructure;
        try {
            programStructure = FileParser.parse(args[0]);
        } catch (IOException ioException) {
            log.info("Can't open file. Please check if it exists or path is correct.\n" + ioException.getMessage());
            return;
        } catch (Exception e) {
            log.info("Error occured while parsing algorithm. Please check it.\n" + e.getMessage());
            return;
        }

        Map<String, Map<String, List<String>>> programCommands = programStructure.entrySet().stream()
                        .filter(e -> !e.getKey().contains("algo:"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        String algo = programStructure.entrySet().stream()
                        .filter(e -> e.getKey().contains("algo:"))
                        .findFirst()
                        .get()
                        .getKey()
                        .substring(5);
        StringBuilder algorithm = new StringBuilder();
        algorithm.append(algo);
        algorithm.insert(0, '_');
        algorithm.append('_');

        String state = "0";
        int pivot = 1;
        while (!"halt".equals(state)) {
            Presenter.present(algorithm, pivot, state);

            Map<String, List<String>> commandsForState = programCommands.get(state);
            List<String> commands = commandsForState.get(String.valueOf(algorithm.charAt(pivot)));

            if (!"*".equals(commands.get(0))) {
                algorithm.setCharAt(pivot, commands.get(0).charAt(0));
            }

            switch (commands.get(1)) {
                case "r":
                    pivot++;
                    break;
                case "l":
                    pivot--;
                    break;
                default:
                    break;
            }

            state = commands.get(2);
        }
        Presenter.present(algorithm, pivot, state);
    }
}
