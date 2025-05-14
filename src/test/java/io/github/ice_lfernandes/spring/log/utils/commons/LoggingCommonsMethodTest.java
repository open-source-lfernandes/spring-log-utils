package io.github.ice_lfernandes.spring.log.utils.commons;

import io.github.ice_lfernandes.spring.log.utils.features.enums.MaskedType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggingCommonsMethodTest {


    @Test
    void shouldMaskAllWithSuccess() {
        var emailExample = "john.doe@gmail.com";
        var emailMaskedExpected = "******************";
        assertEquals(emailMaskedExpected, LoggingCommonsMethods.mask(emailExample, MaskedType.ALL.getRegex()));
    }

    @Test
    void shouldMaskEmailWithSuccess() {
        var emailExample = "john.doe@gmail.com";
        var emailMaskedExpected = "**.doe***.com";
        assertEquals(emailMaskedExpected, LoggingCommonsMethods.mask(emailExample, MaskedType.EMAIL.getRegex()));
    }

    @Test
    void shouldMaskDocumentWithSuccess() {
        var documentExample = "12345678911";
        var documentMaskedExpected = "********911";
        assertEquals(documentMaskedExpected, LoggingCommonsMethods.mask(documentExample, MaskedType.DOCUMENT.getRegex()));
    }

    @Test
    void shouldMaskNameWithSuccess() {
        var nameExample = "John Doe";
        var nameMaskedExpected = "*hn*oe";
        assertEquals(nameMaskedExpected, LoggingCommonsMethods.mask(nameExample, MaskedType.NAME.getRegex()));
    }

    @Test
    void shouldMaskDateWithSuccess() {
        String str = "1986-04-08";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var date = LocalDate.parse(str, formatter);
        var dateMaskedExpected = "***4-08";
        assertEquals(dateMaskedExpected, LoggingCommonsMethods.mask(date.toString(), MaskedType.DATE.getRegex()));
    }

    @Test
    void shouldMaskAddressWithSuccess() {
        var street = "Rua Flamengo 745, RJ";
        var streetMaskedExpected = "*a****o**, RJ";
        assertEquals(streetMaskedExpected, LoggingCommonsMethods.mask(street, MaskedType.ADDRESS.getRegex()));
    }

    @Test
    void shouldMaskZipCodeWithSuccess() {
        var zipCode = "20720011";
        var zipCodeMaskedExpected = "***11";
        assertEquals(zipCodeMaskedExpected, LoggingCommonsMethods.mask(zipCode, MaskedType.ZIP_CODE.getRegex()));
    }

    @Test
    void shouldMaskNumberWithSuccess() {
        var number = "150000";
        var numberMaskedExpected = "******";
        assertEquals(numberMaskedExpected, LoggingCommonsMethods.mask(number, MaskedType.NUMBER.getRegex()));
    }

    @Test
    void shouldMaskTelephoneWithSuccess() {
        var phone = "99999-9999";
        var phoneMaskedExpected = "**9**9";
        assertEquals(phoneMaskedExpected, LoggingCommonsMethods.mask(phone, MaskedType.TELEPHONE.getRegex()));
    }

}
