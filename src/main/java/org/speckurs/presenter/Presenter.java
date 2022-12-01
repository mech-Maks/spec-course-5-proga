package org.speckurs.presenter;

public class Presenter {
    public static void present(StringBuilder algorithm, int pivot, String state) {
        System.out.println(tabs(pivot) + "|");
        StringBuilder res = new StringBuilder();
        res.append("[");

        for (int i=0; i<algorithm.length(); i++) {
            res.append("'" + algorithm.charAt(i) + "',  ");
        }
        res.delete(res.length()-2, res.length());
        res.append("]");
        System.out.println(res.toString());
        System.out.println("state: " + state);
        System.out.println();
    }

    public static void printInstructions() {
        System.out.println("*******************************");
        System.out.println("* <any key> = step in machine *");
        System.out.println("* initial state: 0            *");
        System.out.println("*******************************");
    }

    private static String tabs(int n) {
        StringBuilder tabs = new StringBuilder();
        tabs.append("  ");
        while (n > 0) {
            tabs.append("      ");
            n--;
        }
        return tabs.toString();
    }
}
