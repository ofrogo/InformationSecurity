package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class FileHelper {

    public static List<Path> getFilesFromDir(Path path) throws IOException {
        return Files.walk(path).filter(path1 -> !Files.isDirectory(path1)).collect(Collectors.toList());
    }

    public static boolean isContainingSignature(Path path, byte[] signature) throws IOException {
        byte[] bytesFile = Files.readAllBytes(path);
        for (int i = 0; i < bytesFile.length - signature.length; i++) {
            if (Arrays.compare(signature, Arrays.copyOfRange(bytesFile, i, i + signature.length)) == 0) {
                return true;
            }
        }
        return false;
    }

    public static byte[] getSignature(Path path, int offset, int length) throws IOException {
        return Arrays.copyOfRange(Files.readAllBytes(path), offset, length);
    }

    public static void generateText(Path file, int stringLength, int countStrings) {
        try {
            for (int i = 0; i < countStrings; i++) {
                int leftLimit = 97; // letter 'a'
                int rightLimit = 122; // letter 'z'
                Random random = new Random();
                String generatedString = random.ints(leftLimit, rightLimit + 1)
                        .limit(stringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                Files.writeString(file, generatedString.trim() + "\n", StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTableVigener(Path file) throws IOException {
        String baseStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя0123456789!\"№;%:?*()-+=,./\\ ";
        List<String> resultTable = new ArrayList<>();
        int finalIndex = baseStr.indexOf(baseStr.charAt(baseStr.length() - 1)) + 1;
        for (char c : baseStr.toCharArray()) {
            int thisIndex = baseStr.indexOf(c);
            resultTable.add(baseStr.substring(thisIndex, finalIndex) + baseStr.substring(0, thisIndex));
        }
        Files.writeString(file, resultTable.stream().reduce((s, s2) -> s + "\n" + s2).orElseThrow());
    }

    public static List<List<String>> readTableVigener(Path file) throws IOException {
        String table = Files.readString(file);
        return table.lines().map(StringHelper::stringToListChar).collect(Collectors.toList());
    }

    public static void deleteDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

}
