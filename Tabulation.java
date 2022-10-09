import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tabulation {
    public static void main(String[] args) {
        System.out.println("///////////////////////////////////////");
        System.out.println("Fibonacci with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(fib(6));
        System.out.println(fib(7));
        System.out.println(fib(7));
        System.out.println(fib(50));

        System.out.println("///////////////////////////////////////");
        System.out.println("Grid Traveler with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(gridTraveler(3, 2));
        System.out.println(gridTraveler(18, 18));

        System.out.println("///////////////////////////////////////");
        System.out.println("Can Sum with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(canSum(7, new long[]{5, 3, 4}));
        System.out.println(canSum(10, new long[]{7, 4}));

        System.out.println("///////////////////////////////////////");
        System.out.println("How Sum with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(howSum(7, new long[]{5, 3, 4})));
        System.out.println(Arrays.toString(howSum(7, new long[]{2, 4})));

        System.out.println("///////////////////////////////////////");
        System.out.println("Best Sum with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(Arrays.toString(bestSum(8, new long[]{2, 3, 5})));
        System.out.println(Arrays.toString(bestSum(7, new long[]{2, 4})));
        System.out.println(Arrays.toString(bestSum(100, new long[]{25, 2, 1, 5})));

        System.out.println("///////////////////////////////////////");
        System.out.println("Can Construct with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(canConstruct("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd"}));
        System.out.println(canConstruct("skateboard", new String[]{"bo", "rd", "ate", "t", "ska","sk", "boar"}));


        System.out.println("///////////////////////////////////////");
        System.out.println("Count Construct with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(countConstruct("abcdef", new String[]{"abc", "de", "def", "ab", "a", "cd", "ef"}));
        System.out.println(countConstruct("purple", new String[]{"purp", "p", "ur", "le", "purpl"}));
        System.out.println(countConstruct("enterapotentpot", new String[]{"a", "p", "ent", "enter", "ot", "o", "t"}));

        System.out.println("///////////////////////////////////////");
        System.out.println("All Construct with Tabu");
        System.out.println("-------------------------------------");
        System.out.println(Arrays.deepToString(allConstruct("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd", "ef", "c"})));

    }

    public static long fib(int n) {
        long[] fibTabu = new long[n + 1];
        fibTabu[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibTabu[i] = fibTabu[i - 1] + fibTabu[i - 2];
        }
        return fibTabu[n];
    }

    public static long gridTraveler(int r, int c) {
        long[][] grid = new long[r + 1][c + 1];
        grid[1][1] = 1;
        for (int i = 0; i <= r; i++) {
            for (int j = 0; j <= c; j++) {
                long current = grid[i][j];
                if (i < r) grid[i + 1][j] += current;
                if (j < c) grid[i][j + 1] += current;
            }
        }
        return grid[r][c];
    }

    public static boolean canSum(int target, long[] elements) {
        if (target == 0) return true;
        boolean[] table = new boolean[target + 1];
        table[0] = true;

        for (int i = 0; i < table.length; i++) {
            if (table[i]) {
                for (long element : elements) {
                    if (i + (int) element <= target) {
                        table[i + (int) element] = table[i];
                    }
                }
            }
        }
        return table[target];
    }

    public static long[] howSum(int target, long[] elements) {
        if (target == 0) return new long[0];
        long[][] table = new long[target + 1][];
        table[0] = new long[0];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (long element : elements) {
                    if (i + (int) element <= target) {

                        table[i + (int) element] = Stream.of(table[i], new long[]{element}).flatMapToLong(Arrays::stream).toArray();

                    }
                }
            }
        }
        return table[target];
    }

    public static long[] bestSum(int target, long[] elements) {
        if (target == 0) return new long[0];
        long[][] table = new long[target + 1][];
        table[0] = new long[0];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (long element : elements) {
                    if (i + (int) element <= target) {
                        long[] combination = Stream.of(table[i], new long[]{element}).flatMapToLong(Arrays::stream).toArray();
                        if(table[i + (int) element] == null || combination.length < table[i + (int) element].length) {
                            table[i + (int) element] = combination;
                        }

                    }
                }
            }
        }
        return table[target];
    }

    public static boolean canConstruct(String target, String[] wordBank) {
        if(target.isBlank()) return true;
        boolean[] table = new boolean[target.length() + 1];
        table[0] = true;
        for (int i = 0; i < target.length(); i++) {
            if(table[i]){
                for (String word : wordBank) {
                    if(target.startsWith(word, i)){
                        table[i + word.length()] = true;
                    }
                }
            }
        }
        return table[target.length()];
    }

    public static int countConstruct(String target, String[] wordBank) {
        if(target.isBlank()) return 1;
        int[] table = new int[target.length() + 1];
        table[0] = 1;
        for (int i = 0; i < target.length(); i++) {
            if(table[i] > 0){
                for (String word : wordBank) {
                    if(target.startsWith(word, i)){
                        table[i + word.length()] += table[i];
                    }
                }
            }
        }
        return table[target.length()];
    }

    public static String[][] allConstruct(String target, String[] wordBank) {
        if(target.isBlank()) return new String[target.length()][];
        String[][][] table = new String[target.length()+1][0][];
        table[0] = new String[1][0];
        for (int i = 0; i < target.length(); i++) {
            for (String word : wordBank) {
                if(target.startsWith(word, i)) {
                    String[][] combination = Stream.of(table[i]).map(e -> Stream.concat(Stream.of(word) , Arrays.stream(e)).toArray(String[]::new)).toArray(String[][]::new);
                    table[i + word.length()] = Stream.of(table[i + word.length()], combination).flatMap(Arrays::stream).toArray(String[][]::new);
                }
            }
        }
        return table[target.length()];
    }
}


























