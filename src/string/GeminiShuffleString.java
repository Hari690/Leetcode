package string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeminiShuffleString {
    public static void main(String[] args) {

        GeminiShuffleString anagrams = new GeminiShuffleString();

        for(int i=0;i<10;i++)
            System.out.println(anagrams.shuffleString("abcabcabcabcabcabcabcabc"));

        System.out.println(anagrams.checkAnagrams("abc","bac"));
        System.out.println(anagrams.checkAnagrams("abc","bacd"));
        System.out.println(anagrams.checkAnagrams("abc","aabc"));
        System.out.println(anagrams.checkAnagrams("a","b a 10000x"));
    }

    public boolean checkAnagrams(String s1, String s2) {
        if(s1.length()!=s2.length())
            return false;

        Map<Character,Integer> map1 = getCharCount(s1);
        Map<Character,Integer> map2 = getCharCount(s2);

        if(map1.size()!=map2.size())
            return false;

        for(Map.Entry<Character,Integer> entry : map1.entrySet()) {
            Integer count = map2.get(entry.getKey());
            if(count==null || count!=entry.getValue())
                return false;
        }
        return true;
    }

    private Map<Character,Integer> getCharCount(String s) {
        Map<Character,Integer> map = new HashMap<>();
        for(char c : s.toCharArray()) {
            map.put(c,map.getOrDefault(c,0)+1);
        }
        return map;
    }

    public String shuffleString(String s) {
        List<Character> list = new ArrayList<>();
        for(char c : s.toCharArray()) {
            list.add(c);
        }
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for(int i=0;i<s.length();i++) {
            int randIndex = random.nextInt(list.size());
            result.append(list.get(randIndex));
            list.set(randIndex, list.get(list.size()-1));
            list.remove(list.size()-1);
        }
        return result.toString();
    }
}
