/*
 * Class: CMSC203
 * Project: Assignment 3 - CryptoManager
 * Student: Fabian Serao
 * Instructor: Prof. Gary Thai
 * Description: JUnit tests for the CryptoManager class.
 * Due: October 25, 2026
 * Platform/compiler: Eclipse IDE / Java 25
 */
import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

public class CryptoManagerTestStudent {

    @Test
    public void testIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("PROJECT 3"));
        assertFalse(CryptoManager.isStringInBounds("project 3"));
    }
    

@Test
public void testCaesarEncryption() {
    String encrypted = CryptoManager.caesarEncryption("CAT", 2);
    assertEquals("ECV", encrypted);
}

@Test
public void testCaesarDecryption() {
    String decrypted = CryptoManager.caesarDecryption("ECV", 2);
    assertEquals("CAT", decrypted);
}



@Test
public void testVigenereEncryption() {
    String encrypted = CryptoManager.vigenereEncryption("DOG", "CAT");
    assertEquals("FOZ", encrypted);
}

@Test
public void testVigenereDecryption() {
    String decrypted = CryptoManager.vigenereDecryption("FOZ", "CAT");
    assertEquals("DOG", decrypted);
}


@Test
public void testPlayfairEncryption() {
    String encrypted = CryptoManager.playfairEncryption("ME", "MONTGOMERY");
    assertEquals("OR", encrypted);
}

@Test
public void testPlayfairDecryption() {
    String decrypted = CryptoManager.playfairDecryption("OR", "MONTGOMERY");
    assertEquals("ME", decrypted);
}



}
