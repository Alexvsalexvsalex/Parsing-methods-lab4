import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    private final Map<String, String> terminalRegexps = new HashMap<>();
    private final List<String> sortedTerminals = new ArrayList<>();
    private final Map<String, List<List<String>>> nonTerminals = new HashMap<>();
    private final Map<String, List<String>> synthesisCodes = new HashMap<>();
    private final Map<String, List<String>> parentCodes = new HashMap<>();
    private final Map<String, List<Set<String>>> first = new HashMap<>();
    private final List<String> fields = new ArrayList<>();

    public Grammar(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));
        if (lines.size() < 1) {
            throw new RuntimeException("First line should contains fields' description");
        }

        for (String s : lines.get(0).split(",")) {
            if (!s.isBlank()) {
                fields.add(s.trim());
            }
        }

        for (int i = 1; i < lines.size(); i++) {
            String s = lines.get(i);
            if (!s.isBlank()) {
                int eqInd = s.indexOf('=');
                if (eqInd == -1) {
                    throw new RuntimeException("Invalid sample_grammar");
                }
                String left = s.substring(0, eqInd).trim();
                String middle = s.substring(eqInd + 1).trim();
                if (left.isBlank()) {
                    throw new RuntimeException("Empty left part of '='");
                }
                if (left.chars().allMatch(Character::isUpperCase)) {
                    if (terminalRegexps.containsKey(left)) {
                        throw new RemoteException("Double terminal's definition");
                    }
                    sortedTerminals.add(left);
                    terminalRegexps.put(left, middle);
                    synthesisCodes.put(left, new ArrayList<>());
                    parentCodes.put(left, new ArrayList<>());
                } else if (left.chars().allMatch(Character::isLowerCase)) {
                    if (!nonTerminals.containsKey(left)) {
                        nonTerminals.put(left, new ArrayList<>());
                        synthesisCodes.put(left, new ArrayList<>());
                        parentCodes.put(left, new ArrayList<>());
                    }
                    nonTerminals.get(left).add(
                            Arrays.stream(middle.split(" ")).filter(p -> !p.isBlank()).collect(Collectors.toList()));
                } else {
                    throw new RuntimeException("Invalid left part of '='");
                }

                i++;

                StringBuilder synCode = new StringBuilder();
                i++;
                while (!"#S".equals(lines.get(i))) {
                    synCode.append(String.format("%s\n", lines.get(i)));
                    i++;
                }

                StringBuilder parCode = new StringBuilder();
                i++;
                while (!"#P".equals(lines.get(i))) {
                    parCode.append(String.format("%s\n", lines.get(i)));
                    i++;
                }

                synthesisCodes.get(left).add(synCode.toString());
                parentCodes.get(left).add(parCode.toString());
            }
        }
        for (String p : terminalRegexps.keySet()) {
            first.put(p, List.of(Set.of(p)));
        }
        for (var p : nonTerminals.entrySet()) {
            List<Set<String>> l = new ArrayList<>();
            for (var ignored : p.getValue()) {
                l.add(new HashSet<>());
            }
            first.put(p.getKey(), l);
        }
        calcFirst();
        System.out.println("Grammar parsed successfully");
    }

    private void calcFirst() {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (var p : nonTerminals.entrySet()) {
                String name = p.getKey();
                List<List<String>> value = p.getValue();
                for (int i = 0; i < value.size(); i++) {
                    List<String> rule = value.get(i);
                    if (rule.isEmpty()) {
                        changed |= (first.get(name).get(i).add(""));
                    } else {
                        for (var lst : first.get(rule.get(0))) {
                            for (var subS : lst) {
                                changed |= (first.get(name).get(i).add(subS));
                            }
                        }
                    }
                }
            }
        }
    }

    public Map<String, String> getTerminalRegexps() {
        return terminalRegexps;
    }

    public Map<String, List<List<String>>> getNonTerminals() {
        return nonTerminals;
    }

    public Map<String, List<Set<String>>> getFirst() {
        return first;
    }

    public List<String> getFields() {
        return fields;
    }

    public Map<String, List<String>> getSynthesisCodes() {
        return synthesisCodes;
    }

    public Map<String, List<String>> getParentCodes() {
        return parentCodes;
    }

    public List<String> getSortedTerminals() {
        return sortedTerminals;
    }
}
