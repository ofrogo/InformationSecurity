package Util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterHelper {
    private static final Map<Character, Character> mapRusToEng = new HashMap<>() {
        {
            put('у', 'y');
            put('К', 'K');
            put('е', 'e');
            put('Е', 'E');
            put('Н', 'H');
            put('х', 'x');
            put('Х', 'X');
            put('В', 'B');
            put('а', 'a');
            put('А', 'A');
            put('р', 'p');
            put('Р', 'P');
            put('о', 'o');
            put('О', 'O');
            put('с', 'c');
            put('С', 'C');
            put('Т', 'T');
        }
    };

    private static final Map<Character, Character> mapEngToRus = mapRusToEng.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));


    public static boolean isConvertible(Character character) {
        return mapRusToEng.containsKey(character) || mapEngToRus.containsKey(character);
    }

    public static boolean isConverted(Character c, boolean isRusLanguage) {
        return isConvertible(c) && (isRusLanguage ? mapEngToRus.containsKey(c) : mapRusToEng.containsKey(c));
    }

    public static Character convert(Character c) {
        if (mapEngToRus.containsKey(c)) {
            return mapEngToRus.get(c);
        } else if (mapRusToEng.containsKey(c)) {
            return mapRusToEng.get(c);
        } else throw new RuntimeException("Can't convert this character!");
    }

}
