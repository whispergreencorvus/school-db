package com.hartmanmark.schooldb;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import com.hartmanmark.schooldb.validator.Validator;

class ValidatorTest {
    private Validator validator = new Validator();
    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose(null);
        });
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("");
        });
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringContaintSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("#");
        });
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringContainsNumberOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("8");
        });
    }

    @Test
    void verifyMenuChoose_shouldGetIllegalArgumentException_whenInputStringContainsMoreThanOneCharacter() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyMenuChoose("12");
        });
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger(null);
        });
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger("");
        });
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringContaintSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger("#1");
        });
    }

    @Test
    void verifyInteger_shouldGetIllegalArgumentException_whenInputStringContaintLetter() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.verifyInteger("R");
        });
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringContaintStudentIdOutOfRange() {
        String numberOfStudent = "200";
        String numberOfStudentOutOfRange = "201";
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption(numberOfStudentOutOfRange, numberOfStudent);
        });
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption(null, null);
        });
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption("", "");
        });
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringContainsSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption("%impl-eme&ntat_ion@", "$imp(leme#ntati)on*");
        });
    }

    @Test
    void veryfyRemoveOption_shouldGetIllegalArgumentException_whenInputStringContainsLetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyRemoveOption("1impleme2ntation0", "1impleme2ntation0");
        });
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString(null);
        });
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString("");
        });
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringContainsSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString("%impleme&ntation@");
        });
    }

    @Test
    void veryfyInputString_shouldGetIllegalArgumentException_whenInputStringContainsNumeric() {
        assertThrows(IllegalArgumentException.class, () -> {
            validator.veryfyInputString("1impleme5ntation0");
        });
    }
}
