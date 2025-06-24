package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        String task = "enc";
        String texto = "";
        int key = 0;
        String fileInput = "";
        String fileOutput = "";
        String algorithm = "shift";

        if (checkArguments(args)) {

            for (int i = 0; i < args.length; i = i + 2) {
                switch (args[i]) {
                    case "-mode":
                        task = args[i + 1];
                        break;
                    case "-key":
                        key = Integer.parseInt(args[i + 1]);
                        break;
                    case "-data":
                        texto = args[i + 1].replaceAll("\"", "");
                        break;
                    case "-in":
                        fileInput = args[i + 1];
                        break;
                    case "-out":
                        fileOutput = args[i + 1];
                        break;
                    case "-alg":
                        algorithm = args[i + 1];
                        break;
                }
            }

            if (texto.equals("")) {
                File file = new File(fileInput);
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNext()) {
                        texto += scanner.nextLine();
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("No file found: " + fileInput);
                }
            }

            if (task.equals("enc")) {
                encodeCeasar(texto, key , fileOutput, algorithm);
            } else {
                decodeCeasar(texto, key, fileOutput, algorithm);
            }

        } else {
            System.out.println("Error");
        }

    }

    //Esta función es solo para el Stage1
    public static String encrypt(String texto) {
            String encoded = "";

            for (int i = 0; i < texto.length(); i++) {
                if (Character.isLetter(texto.charAt(i))) {
                    encoded += String.valueOf((char) (122 - ((int) texto.charAt(i) - 97)));
                } else {
                    encoded += String.valueOf(texto.charAt(i));
                }
            }

            return encoded;

    }

    public static void encodeCeasar(String texto, int key, String fileOutput, String algorithm) {
        String encoded = "";


        for (int i = 0; i < texto.length(); i++) {
            if (Character.isLetter(texto.charAt(i))) {
                if (algorithm.equals("unicode")) {
                    encoded += String.valueOf((char) ((((int) texto.charAt(i) - 97) + key) + 97));
                }
                else {
                    if ((int) texto.charAt(i) >= 97) {
                        encoded += String.valueOf((char) (((int) texto.charAt(i) - 97 + key) % 26 + 97));
                    } else {
                        encoded += String.valueOf((char) (((int) texto.charAt(i) - 65 + key) % 26 + 65));
                    }
                }
            } else {
                if (algorithm.equals("unicode")) {
                    encoded += String.valueOf((char) ((int) texto.charAt(i) + key));
                } else {
                    encoded += String.valueOf(texto.charAt(i));
                }
            }
        }

        if (fileOutput.equals("")) {
            System.out.println(encoded);
        } else {
            File file = new File(fileOutput);
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.print(encoded);
            } catch (IOException e) {
                System.out.printf("An exception occurred %s", e.getMessage());
            }
        }
    }

    public static void decodeCeasar(String texto, int key, String fileOutput, String algorithm) {
        String decoded = "";

        for (int i = 0; i < texto.length(); i++) {
            if (Character.isLetter(texto.charAt(i))) {
                if (algorithm.equals("unicode")) {
                    decoded += String.valueOf((char) ((((int) texto.charAt(i) - 97) - key) + 97));
                } else {
                    if ((int) texto.charAt(i) >= 97) {
                        decoded += String.valueOf((char) ((26 + (int) texto.charAt(i) - 97 - key) % 26 + 97));
                    } else {
                        decoded += String.valueOf((char) ((26 + (int) texto.charAt(i) - 65 - key) % 26 + 65));
                    }
                }
            } else {
                if (algorithm.equals("unicode")) {
                    decoded += String.valueOf((char) ((int) texto.charAt(i) - key));
                } else {
                    decoded += String.valueOf(texto.charAt(i));
                }
            }
        }

        if (fileOutput.equals("")) {
            System.out.println(decoded);
        } else {
            File file = new File(fileOutput);
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.print(decoded);
            } catch (IOException e) {
                System.out.printf("An exception occurred %s", e.getMessage());
            }
        }
    }

    public static boolean checkArguments(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].matches("-[a-z]*") && args[i+1].matches("-[a-z]*")) {
                return false;
            } else {
                if (args[i].equals("-in")) {
                    File file = new File(args[i + 1]);
                    if (!file.exists()) {
                        return false;
                    }
                }
            }
        }
        if (args[args.length - 1].matches("-[a-z]*")) {
            return false;
        }
        return true;
    }


}
