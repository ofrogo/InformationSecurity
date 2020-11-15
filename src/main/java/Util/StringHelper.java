package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringHelper {
    public static List<String> stringToBinary(String str) {
        return str.codePoints()
                .mapToObj(value -> String.format("%8s", Integer.toBinaryString(value & 0xFF)).replace(' ', '0'))
                .collect(Collectors.toList());
    }

    public static String binaryToString(List<String> binary) {
        return binary.stream()
                .map(bin -> String.valueOf((char) Integer.parseInt(bin, 2)))
                .reduce((s, s2) -> s + s2)
                .orElseThrow();
    }

    public static List<String> stringToListChar(String str) {
        return str.chars().mapToObj(cp -> String.valueOf((char) cp)).collect(Collectors.toList());
    }

    public static List<String> binaryStringSplitIntoBytes(String binaryStr) {
        List<String> binaryStrings = new ArrayList<>();
        for (int i = 0; i < binaryStr.length() - 8; i += 8) {
            String substring = binaryStr.substring(i, i + 8);
            if (!substring.equals("00000000")) {
                binaryStrings.add(substring);
            } else {
                break;
            }
        }
        return binaryStrings;
    }

    public static String encryptVigener(List<List<String>> tableVigener, String secret, String key) {
        String forKey = tableVigener.get(0).stream().reduce((s, s2) -> s + s2).orElseThrow();
        StringBuilder result = new StringBuilder();
        for (int i = 0, indexKey = 0; i < secret.length(); i++) {
            int indexText = tableVigener.get(0).indexOf(String.valueOf(secret.charAt(i)));
            result.append(tableVigener.get(forKey.indexOf(key.charAt(indexKey))).get(indexText));
            indexKey++;
            if (key.length() == indexKey) indexKey = 0;
        }

        return String.valueOf(result);
    }

    public static String decodeViginer(List<List<String>> tableVigener, String secret, String key) {
        String forKey = tableVigener.get(0).stream().reduce((s, s2) -> s + s2).orElseThrow();
        StringBuilder result = new StringBuilder();
        for (int i = 0, indexKey = 0; i < secret.length(); i++) {
            int indexText = tableVigener.get(forKey.indexOf(key.charAt(indexKey))).indexOf(String.valueOf(secret.charAt(i)));
            result.append(tableVigener.get(0).get(indexText));
            indexKey++;
            if (key.length() == indexKey) indexKey = 0;
        }
        return  String.valueOf(result);
    }
}
