import Util.CharacterHelper;
import Util.FileHelper;
import Util.StringHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Util.StringHelper.binaryStringSplitIntoBytes;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        FileHelper.writeTableVigener(Path.of("D:\\Projects\\untitled\\src\\main\\resources\\6t\\viginer.txt"));
        sixthTaskWrite();
        System.out.println("Finish reading");
        sixthTaskRead();
    }

    private static void firstTask() {
        System.out.println("Enter path of dir: ");
        Path dir = Path.of(scanner.nextLine());

        System.out.println("Enter file name for signature: ");
        try {
            byte[] signature = FileHelper.getSignature(Path.of(scanner.nextLine()), 0, 11);
            List<Path> filesFromDir = FileHelper.getFilesFromDir(dir);
            for (Path filePath : filesFromDir) {
                if (FileHelper.isContainingSignature(filePath, signature)) {
                    System.out.println(filePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void secondTaskWrite() {
        System.out.println("Enter path of file with text: ");
        Path file = Path.of(scanner.nextLine());
        System.out.println("Enter secret phrase: ");
        String secret = scanner.nextLine();
        System.out.println("Enter path for file with secret text: ");
        Path secretFile = Path.of(scanner.nextLine());


        try {
            List<String> lines = Files.readString(file).lines().collect(Collectors.toList());
            AtomicInteger index = new AtomicInteger();
            StringHelper.stringToBinary(secret).forEach(s -> {
                char[] chars = s.toCharArray();
                for (int i = 0; i < s.length(); i++) {
                    if (chars[i] == '1') {
                        lines.set(index.get(), lines.get(index.get()) + " ");
                    }
                    index.getAndIncrement();
                }
            });
            Files.writeString(secretFile, lines.stream().reduce((s, s2) -> s + "\n" + s2).orElseThrow(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void secondTaskRead() {
        System.out.println("Enter path for file with secret text: ");
        Path secretFile = Path.of(scanner.nextLine());

        try {
            String binary = Files.readString(secretFile).lines().map(s -> s.charAt(s.length() - 1) == ' ' ? "1" : "0").reduce((s, s2) -> s + s2).orElseThrow();
            System.out.println(StringHelper.binaryToString(binaryStringSplitIntoBytes(binary)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void thirdTaskWrite() {
        System.out.println("Enter path of file with text: ");
        Path file = Path.of(scanner.nextLine());
        System.out.println("Enter secret phrase: ");
        String secret = scanner.nextLine();
        System.out.println("Enter path for file with secret text: ");
        Path secretFile = Path.of(scanner.nextLine());

        Deque<Boolean> secretDeque = new ArrayDeque<>();
        StringHelper.stringToBinary(secret).forEach(s -> {
            char[] chars = s.toCharArray();
            for (int i = 0; i < s.length(); i++) {
                secretDeque.add(chars[i] == '1');
            }
        });

        try {
            Files.writeString(secretFile, Arrays.stream(Files.readString(file).split(" "))
                            .reduce((s, s2) -> s + (secretDeque.isEmpty() ? " " : secretDeque.pop() ? "  " : " ") + s2)
                            .orElseThrow(),
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void thirdTaskRead() {
        System.out.println("Enter path for file with secret text: ");
        Path secretFile = Path.of(scanner.nextLine());
        String binary = "";
        try {
            char[] chars = Files.readString(secretFile).toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == ' ') {
                    if (i < chars.length - 1) {
                        if (chars[i + 1] == ' ') {
                            binary = binary.concat("1");
                            i++;
                            continue;
                        }
                    }
                    binary = binary.concat("0");
                }
            }
            System.out.println(StringHelper.binaryToString(binaryStringSplitIntoBytes(binary)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fourthTaskWrite() {
        System.out.println("Enter path of file with text: ");
        Path file = Path.of(scanner.nextLine());
        System.out.println("Enter secret phrase: ");
        String secret = scanner.nextLine();
        System.out.println("Enter path for file with secret text: ");
        Path secretFile = Path.of(scanner.nextLine());

        Deque<Boolean> secretDeque = new ArrayDeque<>();
        StringHelper.stringToBinary(secret).forEach(s -> {
            char[] chars = s.toCharArray();
            for (int i = 0; i < s.length(); i++) {
                secretDeque.add(chars[i] == '1');
            }
        });
        try {
            Files.writeString(secretFile, Files.readString(file).codePoints()
                            .mapToObj(o -> {
                                if (CharacterHelper.isConvertible((char) o)) {
                                    if (!secretDeque.isEmpty()) {
                                        if (secretDeque.pop()) {
                                            return String.valueOf(CharacterHelper.convert((char) o));
                                        }
                                    }
                                }
                                return String.valueOf((char) o);
                            })
                            .reduce((s, s2) -> s + s2)
                            .orElseThrow(),
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fourthTaskRead() {
        System.out.println("Enter path for file with secret text: ");
        Path secretFile = Path.of(scanner.nextLine());

        try {
            String binaryStr = "";
            for (char c : Files.readString(secretFile).toCharArray()) {
                if (CharacterHelper.isConvertible(c)) {
                    binaryStr = binaryStr.concat(CharacterHelper.isConverted(c, true) ? "1" : "0");
                }
            }
            System.out.println(StringHelper.binaryToString(binaryStringSplitIntoBytes(binaryStr)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fifthTaskWrite() {
        try {
            System.out.println("Enter secret phrase: ");
            String secret = scanner.nextLine();

            System.out.println("Enter key: ");
            String key = scanner.nextLine();

            System.out.println("Enter path of file with table: ");
            List<List<String>> tableVigener = FileHelper.readTableVigener(Path.of(scanner.nextLine()));
            String s = StringHelper.encryptVigener(tableVigener, secret, key);

            System.out.println("Enter path for saving encrypted phrase: ");
            Files.writeString(Path.of(scanner.nextLine()), s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fifthTaskRead() {
        try {
            System.out.println("Enter path from secret phrase: ");
            String secret = Files.readString(Path.of(scanner.nextLine()));

            System.out.println("Enter key: ");
            String key = scanner.nextLine();

            System.out.println("Enter path of file with table: ");
            List<List<String>> tableVigener = FileHelper.readTableVigener(Path.of(scanner.nextLine()));
            String s = StringHelper.decodeViginer(tableVigener, secret, key);

            System.out.println("Result: ");
            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sixthTaskWrite() {
        try {
            System.out.println("Enter path for root dir: ");
            Path rootPath = Path.of(scanner.nextLine());
            List<Path> filesFromRoot = FileHelper.getFilesFromDir(rootPath);

            System.out.println("Enter key: ");
            String key = scanner.nextLine();

            System.out.println("Enter path of file with table: ");
            List<List<String>> tableVigener = FileHelper.readTableVigener(Path.of(scanner.nextLine()));

            StringBuilder result = new StringBuilder();
            for (Path path : filesFromRoot) {
                result.append(StringHelper.encryptVigener(tableVigener, path.toString().replace(rootPath.getParent().toString(), ""), key))
                        .append("@")
                        .append(StringHelper.encryptVigener(tableVigener, Files.readString(path), key))
                        .append("~");
            }
            FileHelper.deleteDirectory(rootPath);

            File file = rootPath.getParent().resolve("myFile.mf").toFile();
            file.createNewFile();
            Files.writeString(file.toPath(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sixthTaskRead() {
        try {
            System.out.println("Enter path for root dir: ");
            Path rootPath = Path.of(scanner.nextLine());

            System.out.println("Enter key: ");
            String key = scanner.nextLine();

            System.out.println("Enter path of file with table: ");
            List<List<String>> tableVigener = FileHelper.readTableVigener(Path.of(scanner.nextLine()));

            System.out.println("Enter path from secret: ");
            String secret = Files.readString(Path.of(scanner.nextLine()));


            for (String pathValue : secret.split("~")) {
                String[] split = pathValue.split("@");
                Path resultPath = rootPath.resolve(Path.of(StringHelper.decodeViginer(tableVigener, split[0], key).substring(1)));
                File dir = resultPath.getParent().toFile();
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        throw new RuntimeException("Don't create dirs");
                    }

                }
                File file = resultPath.toFile();
                if (!file.createNewFile()) {
                    if (!file.exists())
                        throw new RuntimeException("Don't create file");
                }
                Files.writeString(resultPath, StringHelper.decodeViginer(tableVigener, split[1], key));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
