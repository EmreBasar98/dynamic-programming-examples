import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Memoization {
    public static void main(String[] args) {
        /////////////////////fibonacci with memoization
        System.out.println("Fibonacci with Memo");
        System.out.println("-------------------------------------");
        HashMap<Integer, Double> fibMemo = new HashMap<>();
        System.out.printf("%.0f\n", fib(6, fibMemo));

        /////////////////////grid traveler with memoization
        System.out.println("-------------------------------------");
        System.out.println("Grid Traveler with Memo");
        HashMap<String, Double> gridMemo = new HashMap<>();
        System.out.printf("%.0f\n", gridTraveler(10, 10, gridMemo));

        /////////////////////can sum
        System.out.println("-------------------------------------");
        System.out.println("Can Sum");
        HashMap<Integer, Boolean> cansumMemo = new HashMap<>();
        int[] elements = {7, 4};
        System.out.println(canSum(300, elements, cansumMemo));

        /////////////////////how sum
        System.out.println("-------------------------------------");
        System.out.println("How Sum");
        HashMap<Integer, ArrayList<Integer>> howsumMemo = new HashMap<>();
        int[] howsumelements = {7, 15};
        System.out.println(howSum(302, howsumelements, howsumMemo));

        /////////////////////best sum
        System.out.println("-------------------------------------");
        System.out.println("Best Sum");
        HashMap<Integer, List<Integer>> bestSumMemo = new HashMap<>();
        int[] bestsumelements = {2, 3, 4, 25};
        System.out.println(bestSum(100, bestsumelements, bestSumMemo));

        /////////////////////can consturct
        System.out.println("-------------------------------------");
        System.out.println("Can Construct");
        HashMap<String, Boolean> canConstructMemo = new HashMap<>();
        String[] wordBank = {"e", "ee", "eee", "eeee", "eeeee" , "eeeeee"};
        System.out.println(canConstruct("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef", wordBank, canConstructMemo));

        String[] wordBank2 = {"bo", "rd", "ate", "d", "ska" , "sk", "boar"};
        System.out.println(canConstruct("skateboard", wordBank2, canConstructMemo));

        /////////////////////count consturct
        System.out.println("-------------------------------------");
        System.out.println("Count Construct");
        HashMap<String, Integer> countConstructMemo = new HashMap<>();
        String[] wordBank3 = {"bo", "rd", "ate", "t", "ska" , "sk", "boar"};
        System.out.println(countConstruct("skateboard", wordBank3, countConstructMemo));
        String[] wordBank4 = {"e", "ee", "eee", "eeee", "eeeee" , "eeeeee"};
        System.out.println(countConstruct("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", wordBank4, countConstructMemo));

        /////////////////////all consturct
        System.out.println("-------------------------------------");
        System.out.println("All Construct");

        HashMap<String, List<List<String>>> allConstructMemo = new HashMap<>();
        String[] wordBank5 = {"ab", "abc", "cd", "def", "abcd" , "ef", "c"};
        System.out.println(allConstruct("abcdef", wordBank5, allConstructMemo));


        String[] wordBank6 = {"purp", "p", "ur", "le", "purpl"};
        System.out.println(allConstruct("purple", wordBank6, allConstructMemo));


        String[] wordBank7 = {"bo", "rd", "ate", "t", "ska" , "sk", "boar"};
        System.out.println(allConstruct("skateboard", wordBank7, allConstructMemo));


        String[] wordBank8 = {"a", "aa", "aaa", "aaaa", "aaaaa"};
        System.out.println(allConstruct("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", wordBank8, allConstructMemo));
    }

    public static double fib(int n, HashMap<Integer, Double> memo) {
        if (n <= 2) return 1;
        if (memo.get(n) != null) {
            return memo.get(n);
        }
        memo.put(n, fib(n - 1, memo) + fib(n - 2, memo));
        return memo.get(n);
    }

    public static double gridTraveler(int r, int c, HashMap<String, Double> gridMemo) {
        if (r == 1 && c == 1) return 1;
        if (r == 0 || c == 0) return 0;
        if ((r == 1 && c > 1) || (r > 1 && c == 1)) return 1;
        String key = r + "," + c;
        String revKey = c + "," + r;
        if (gridMemo.get(key) != null) {
            return gridMemo.get(key);
        } else if (gridMemo.get(revKey) != null) {
            return gridMemo.get(revKey);
        }
        gridMemo.put(key, gridTraveler(r - 1, c, gridMemo) + gridTraveler(r, c - 1, gridMemo));
        return gridMemo.get(key);
    }

    public static boolean canSum(int targetSum, int[] elements, HashMap<Integer, Boolean> cansumMemo) {
        if (cansumMemo.get(targetSum) != null) return cansumMemo.get(targetSum);
        if (targetSum == 0) return true;
        if (targetSum < 0) return false;

        for (int elem : elements) {
            int remainder = targetSum - elem;
            if (canSum(remainder, elements, cansumMemo)) {
                cansumMemo.put(remainder, true);
                return true;
            }
        }
        cansumMemo.put(targetSum, false);
        return false;
    }

    public static ArrayList<Integer> howSum(int targetSum, int[] elements, HashMap<Integer, ArrayList<Integer>> howsumMemo) {
        if (howsumMemo.containsKey(targetSum)) return howsumMemo.get(targetSum);
        ArrayList<Integer> res = new ArrayList<>();
        if (targetSum < 0) return null;
        if (targetSum == 0) return res;

        for (int elem : elements) {
            int remainder = targetSum - elem;
            res = howSum(remainder, elements, howsumMemo);

            if (res != null) {
                res.add(elem);
                howsumMemo.put(remainder, res);
                return howsumMemo.get(remainder);
            }
        }
        howsumMemo.put(targetSum, null);
        return null;
    }

    public static List<Integer> bestSum(int targetSum, int[] elements, HashMap<Integer, List<Integer>> bestSumMemo) {
        if (bestSumMemo.containsKey(targetSum)) return bestSumMemo.get(targetSum);
        if (targetSum < 0) return null;
        if (targetSum == 0) return new ArrayList<>();

        List<Integer> shortestRes = null;
        for (int elem : elements){
            int remainder = targetSum - elem;
            List<Integer> res  = bestSum(remainder, elements, bestSumMemo);
            if (res != null) {
                List<Integer> combination = new ArrayList<>(res);
                combination.add(elem);
                if(shortestRes == null || combination.size() < shortestRes.size()) {
                    shortestRes = combination;
                }
            }
        }
        bestSumMemo.put(targetSum, shortestRes);
        return shortestRes;
    }

    public static boolean canConstruct(String target, String[] wordBank, HashMap<String, Boolean> canConstructMemo) {
        if (canConstructMemo.containsKey(target)) return canConstructMemo.get(target);
        if(target.isBlank()) return true;
        for (String word : wordBank) {
            if (target.startsWith(word)) {
                String suffix = target.replace(word,"");
                if(canConstruct(suffix, wordBank, canConstructMemo)) {
                    canConstructMemo.put(suffix, true);
                    return true;
                }
            }
        }
        canConstructMemo.put(target, false);
        return false;
    }

    public static int countConstruct(String target, String[] wordBank, HashMap<String, Integer> countConstructMemo) {
        if(countConstructMemo.containsKey(target)) return countConstructMemo.get(target);
        if(target.isBlank()) return 1;
        int numberOfWays = 0;
        for (String word : wordBank) {
            if (target.startsWith(word)) {
                String suffix = target.replace(word,"");
                numberOfWays += countConstruct(suffix, wordBank, countConstructMemo);
            }
        }
        countConstructMemo.put(target, numberOfWays);
        return numberOfWays;
    }

    public static List<List<String>> allConstruct(String target, String[] wordBank, HashMap<String, List<List<String>>> allConstructMemo) {
        if(allConstructMemo.containsKey(target)) return allConstructMemo.get(target);

        if(target.isBlank()) return new ArrayList<>(List.of(new ArrayList<>()));

        List<List<String>> result = new ArrayList<>();
        for (String word : wordBank) {
            if (target.startsWith(word)) {
                String suffix = target.replace(word,"");
                List<List<String>> suffixWays = allConstruct(suffix, wordBank, allConstructMemo);
                List<List<String>> targetWays = suffixWays.stream().map(e -> Stream.concat(Stream.of(word) , e.stream()).collect(Collectors.toList())).toList();
                result.addAll(targetWays);
            }
        }
        allConstructMemo.put(target, result);
        return result;
    }
}

