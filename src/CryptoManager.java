/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * Class: CMSC203
 * Project: Assignment 3 - CryptoManager
 * Student: Fabian Serao
 * Instructor: Prof. Gary Thai
 * Due: October 25, 2026
 * Platform/compiler: Eclipse IDE / Java 25
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   
    
    public static String vigenereEncryption(String plainText, String key) {

        if (!isStringInBounds(plainText) ||
            !isStringInBounds(key) || key.length() == 0) {
            return "The selected string is not in bounds, Try again.";
        }

        String encrypted = "";

        for (int i = 0; i < plainText.length(); i++) {
            int plainIndex = ALPHABET64.indexOf(plainText.charAt(i));
            int keyIndex = ALPHABET64.indexOf(key.charAt(i % key.length()));

            int encryptedIndex = (plainIndex + keyIndex) % 64;

            encrypted += ALPHABET64.charAt(encryptedIndex);
        }

        return encrypted;
    }

    // Vigenere Decryption
    
    public static String vigenereDecryption(String encryptedText, String key) {

        if (!isStringInBounds(encryptedText) ||
            !isStringInBounds(key) || key.length() == 0) {
            return "The selected string is not in bounds, Try again.";
        }

        String decrypted = "";

        for (int i = 0; i < encryptedText.length(); i++) {
            int encryptedIndex = ALPHABET64.indexOf(encryptedText.charAt(i));
            int keyIndex = ALPHABET64.indexOf(key.charAt(i % key.length()));

            int plainIndex = (encryptedIndex - keyIndex + 64) % 64;

            decrypted += ALPHABET64.charAt(plainIndex);
        }

        return decrypted;
    }
    
    
    private static char[][] createPlayfairMatrix(String key) {

        String characters = "";

        for (int i = 0; i < key.length(); i++) {
            char current = key.charAt(i);

            if (characters.indexOf(current) == -1) {
                characters += current;
            }
        }

        for (int i = 0; i < ALPHABET64.length(); i++) {
            char current = ALPHABET64.charAt(i);

            if (characters.indexOf(current) == -1) {
                characters += current;
            }
        }

        char[][] matrix = new char[8][8];

        for (int i = 0; i < 64; i++) {
            matrix[i / 8][i % 8] = characters.charAt(i);
        }

        return matrix;
    }


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */ 
    
 // Playfair Encryption
    

public static String playfairEncryption(String plainText, String key) {

    if (!isStringInBounds(plainText) ||
        !isStringInBounds(key) || key.length() == 0) {
        return "The selected string is not in bounds, Try again.";
    }

    char[][] matrix = createPlayfairMatrix(key);

    String characters = "";

    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            characters += matrix[row][col];
        }
    }

    if (plainText.length() % 2 != 0) {
        plainText += "X";
    }

    String encrypted = "";

    for (int i = 0; i < plainText.length(); i += 2) {

        int first = characters.indexOf(plainText.charAt(i));
        int second = characters.indexOf(plainText.charAt(i + 1));

        int row1 = first / 8;
        int col1 = first % 8;
        int row2 = second / 8;
        int col2 = second % 8;

        if (row1 == row2) {
            encrypted += matrix[row1][(col1 + 1) % 8];
            encrypted += matrix[row2][(col2 + 1) % 8];
        }
        else if (col1 == col2) {
            encrypted += matrix[(row1 + 1) % 8][col1];
            encrypted += matrix[(row2 + 1) % 8][col2];
        }
        else {
            encrypted += matrix[row1][col2];
            encrypted += matrix[row2][col1];
        }
    }

    return encrypted;
}


    // Playfair Decryption

public static String playfairDecryption(String encryptedText, String key) {

    if (!isStringInBounds(encryptedText) ||
        !isStringInBounds(key) || key.length() == 0 ||
        encryptedText.length() % 2 != 0) {
        return "The selected string is not in bounds, Try again.";
    }

    char[][] matrix = createPlayfairMatrix(key);

    String characters = "";

    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            characters += matrix[row][col];
        }
    }

    String decrypted = "";

    for (int i = 0; i < encryptedText.length(); i += 2) {

        int first = characters.indexOf(encryptedText.charAt(i));
        int second = characters.indexOf(encryptedText.charAt(i + 1));

        int row1 = first / 8;
        int col1 = first % 8;
        int row2 = second / 8;
        int col2 = second % 8;

        if (row1 == row2) {
            decrypted += matrix[row1][(col1 + 7) % 8];
            decrypted += matrix[row2][(col2 + 7) % 8];
        }
        else if (col1 == col2) {
            decrypted += matrix[(row1 + 7) % 8][col1];
            decrypted += matrix[(row2 + 7) % 8][col2];
        }
        else {
            decrypted += matrix[row1][col2];
            decrypted += matrix[row2][col1];
        }
    }

    if (decrypted.endsWith("X")) {
        decrypted = decrypted.substring(0, decrypted.length() - 1);
    }

    return decrypted;
}


    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    

 // Caesar Encryption

public static String caesarEncryption(String plainText, int key) {

    if (!isStringInBounds(plainText)) {
        return "The selected string is not in bounds, Try again.";
    }

    String encrypted = "";

    for (int i = 0; i < plainText.length(); i++) {
        int currentIndex = plainText.charAt(i) - LOWER_RANGE;

        int newIndex = (currentIndex + (key % RANGE) + RANGE) % RANGE;

        encrypted += (char) (LOWER_RANGE + newIndex);
    }

    return encrypted;
}



    // Caesar Decryption


public static String caesarDecryption(String encryptedText, int key) {

    if (!isStringInBounds(encryptedText)) {
        return "The selected string is not in bounds, Try again.";
    }

    String decrypted = "";

    for (int i = 0; i < encryptedText.length(); i++) {
        int currentIndex = encryptedText.charAt(i) - LOWER_RANGE;

        int newIndex = (currentIndex - (key % RANGE) + RANGE) % RANGE;

        decrypted += (char) (LOWER_RANGE + newIndex);
    }

    return decrypted;
}



}

