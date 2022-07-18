package com.hartmanmark.schooldb;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hartmanmark.schooldb.validator.Validator;

class ValidatorTest {
    private String dataIsNull = "Input data is null";
    private String emptyString = "Input string is empty.";
    private String enterIntiger = "Please enter an integer value.";
    private String oneCharacter = "Input must be contain only one character";
    private String valueRange = "Please enter an integer value between 1 and 7";
    private String valueBetween = "Please enter an integer value between 1 and ";
    private String dataIsSymbol = "Input data contains symbol";
    private String dataIsNumeric = "Input data contains numeric";
    private String dataIsLetter = "Input data contains letter";
    private Validator validator = new Validator();
    private Throwable exception;

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose(null);
        });
        assertEquals(dataIsNull, exception.getMessage());
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("");
        });
        assertEquals(emptyString, exception.getMessage());
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringContaintSymbol() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("#");
        });
        assertEquals(valueRange, exception.getMessage());
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringContainsNumberOutOfRange() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("8");
        });
        assertEquals(valueRange, exception.getMessage());
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringContainsMoreThanOneCharacter() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("12");
        });
        assertEquals(oneCharacter, exception.getMessage());
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger(null);
        });
        assertEquals(dataIsNull, exception.getMessage());
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger("");
        });
        assertEquals(emptyString, exception.getMessage());
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringContaintSymbol() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger("#1");
        });
        assertEquals(enterIntiger, exception.getMessage());
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringContaintLetter() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger("R");
        });
        assertEquals(enterIntiger, exception.getMessage());
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringContaintStudentIdOutOfRange() {
        String numberOfStudent = "200";
        String numberOfStudentOutOfRange = "201";
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption(numberOfStudentOutOfRange, numberOfStudent);
        });
        assertEquals(valueBetween + numberOfStudent, exception.getMessage());
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption(null, null);
        });
        assertEquals(dataIsNull, exception.getMessage());
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption("", "");
        });
        assertEquals(emptyString, exception.getMessage());
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringContainsSymbol() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption("%impl-eme&ntat_ion@", "$imp(leme#ntati)on*");
        });
        assertEquals(dataIsSymbol, exception.getMessage());
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringContainsLetters() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption("1impleme2ntation0", "1impleme2ntation0");
        });
        assertEquals(dataIsLetter, exception.getMessage());
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString(null);
        });
        assertEquals(dataIsNull, exception.getMessage());
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString("");
        });
        assertEquals(emptyString, exception.getMessage());
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringContainsSymbol() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString("%impleme&ntation@");
        });
        assertEquals(dataIsSymbol, exception.getMessage());
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringContainsNumeric() {
        exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString("1impleme5ntation0");
        });
        assertEquals(dataIsNumeric, exception.getMessage());
    }
}
