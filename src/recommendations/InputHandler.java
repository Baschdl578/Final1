package recommendations;

import edu.kit.informatik.Terminal;
import recommendations.nodes.Product;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Sebastian on 23.02.2015.
 *
 * @author = Sebastian Schindler
 * @version = 1.0
 */
public class InputHandler {
    /**
     * Waits for Input and calls appropriate Methods
     */
    public static void start() {
        String command = Terminal.readLine();
        command = cleanup(command);
        if (command.equals("quit")) {
            System.exit(0);
        } else {
            if (command.equals("nodes")) {
                Main.printNodes();
            } else {
                if (command.equals("edges")) {
                    Main.printEdges();
                } else {
                    if (command.equals("export")) {
                        Main.export();
                    } else {

                        if (command.startsWith("recommend ")) {
                            String newCmd = command.substring(10);
                            try {
                                Main.printRecommendation(recommend(newCmd));
                            } catch (ParseError e) {
                                Terminal.printLine(e.getMessage());
                                Main.printUsage();
                            } catch (FinderError e) {
                                Terminal.printLine(e.getMessage());
                            }
                        } else {
                            Terminal.printLine("Error, " + Main.getErrors().getString("Input.1"));
                            Main.printUsage();
                        }
                    }
                }
            }
        }
        start();
    }


    private static String cleanup(String in) {
        String out = in;

        while (out.contains("  ")) {
            out = out.replace("  ", " ");
        }
        out = out.replace("( ", "(");
        out = out.replace(" )", ")");
        out = out.replace(" (", "(");
        out = out.replace(" ,", ",");
        out = out.replace(", ", ",");
        out = out.replace(") ", ")");
        while (out.endsWith(" ")) {
            out = out.substring(0, out.length() - 1);
        }
        while (out.startsWith(" ")) {
            out = out.substring(1);
        }
        return out;
    }


    private static LinkedList<Product> intersect(LinkedList<Product> a, LinkedList<Product> b) {
        LinkedList<Product> out = new LinkedList<Product>();
        out.addAll(a);
        for (Product prodB: b) {
            boolean in = true;
            for (Product prodA: out) {
                if (prodA.equals(prodB)) in = false;
            }
            if (in) out.add(prodB);
        }


        return out;
    }

    private static LinkedList<Product> union(LinkedList<Product> a, LinkedList<Product> b) {
        LinkedList<Product> out = new LinkedList<Product>();

        for (Product prod: a) {
            out.add(prod);
        }
        for (Product prod: b) {
            out.add(prod);
        }

        Collections.sort(out);
        int i = 0;  //Check for duplicates
        while (i < out.size() - 1) {
            if (out.get(i).equals(out.get(i + 1))) {
                out.remove(i + 1);
            } else i++;
        }
        return out;
    }

    private static LinkedList<Product> recommend(String command) throws ParseError, FinderError {
        String line = command;
        while (command.startsWith(" ")) {
            line = line.substring(1);
        }
        if (lookAhead(line).equals("intersect")) {
            line = line.substring(13, line.length() - 1);
            String[] sides = getLeftRight(line);
            return intersect(recommend(sides[0]), recommend(sides[1]));
        } else {
                if (lookAhead(line).equals("union")) {
                line = line.substring(6, line.length() - 1);
                String[] sides = getLeftRight(line);
                return union(recommend(sides[0]), recommend(sides[1]));
            } else {
                    if (lookAhead(line).equals("final")) {
                        String tmp = "";
                        tmp += line.charAt(1);
                        int strategy = Integer.parseInt(tmp);
                        Product product;
                        product = Main.findProduct(line.substring(3));

                        switch (strategy) {
                            case 1: return Main.recommendationS1(product);
                            case 2: return Main.recommendationS2(product);
                            case 3: return Main.recommendationS3(product);
                            default:
                        }
                    } else throw new ParseError("Error, " + Main.getErrors().getString("Input.1"));
                    }
                }
        throw new ParseError("Error, " + Main.getErrors().getString("Input.1"));
    }

    private static String[] getLeftRight(String line) {
        int index = 0;
        int open = 0;
        char current;

        while (index < line.length()) {
            current = line.charAt(index);
            index++;
            if (current == '(') {
                open++;
            } else if (current == ')') {
                open--;
            } else if (current == ',' && open == 0) {
                break;
            }
        }
        String[] out = new String[2];
        out[0] = line.substring(0, index - 1);
        out[1] = line.substring(index);
        return out;
    }

    private static String lookAhead(String command) {
        if (command.startsWith("INTERSECTION")) return "intersect";
        if (command.startsWith("UNION")) return "union";
        if (command.startsWith("S1") | command.startsWith("S2") | command.startsWith("S3")) return "final";
        else return "fail";
    }

    /**
     * Error in User Input
     */
    private static class ParseError extends Exception {
        ParseError(String message) {
            super(message);
        }
    }
}
