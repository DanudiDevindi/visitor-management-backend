package com.swehg.visitormanagement.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MobileValidator {

    private static final int VALID_MOBILE_LENGTH_STANDARD = 10;
    private static final int TAG_MOBILE_NUMBER_LENGTH = 11;
    private static final String MOBILE_NUMBER_START_PREFIX = "0";
    private static final String B2B_MOBILE_NUMBER_START_PREFIX = "94";
    private static final String HIDDEN_NUMBER_FORMAT = "XXXXXXX";

    public String getMobileStandardFormat(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isEmpty()) return null;

        String standardPhoneNumber = ""; //LIKE 07XXXXXXXX

        phoneNumber = phoneNumber.replaceAll("\\s+", "");
        phoneNumber = phoneNumber.replaceAll("\\D+", "");

        if (phoneNumber.startsWith("+940") && phoneNumber.length() == 13) {
            standardPhoneNumber = MOBILE_NUMBER_START_PREFIX + phoneNumber.substring(4);
        }else if (phoneNumber.startsWith("+94") && phoneNumber.length() == 12) {
            standardPhoneNumber = MOBILE_NUMBER_START_PREFIX + phoneNumber.substring(3);
        } else if (phoneNumber.startsWith("94") && phoneNumber.length() == 11) {
            standardPhoneNumber = MOBILE_NUMBER_START_PREFIX + phoneNumber.substring(2);
        } else if (phoneNumber.startsWith("0") && phoneNumber.length() == 10) {
            standardPhoneNumber = phoneNumber;
        } else if (phoneNumber.startsWith("7") && phoneNumber.length() == 9) {
            standardPhoneNumber = MOBILE_NUMBER_START_PREFIX + phoneNumber;
        } else {
            //check foreign number or not
            log.info("Method : getMobileStandardFormat Foreign mobile number found : {}", phoneNumber);

            standardPhoneNumber = phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
            return ((standardPhoneNumber.length() > 8 && standardPhoneNumber.length() <= 20) && standardPhoneNumber.matches("[0-9]+")) ? standardPhoneNumber : null;
        }
        log.info("Method : getMobileStandardFormat local mobile number found : {}", phoneNumber);
        return standardPhoneNumber.length() == VALID_MOBILE_LENGTH_STANDARD && standardPhoneNumber.matches("[0-9]+") ? standardPhoneNumber : null;

    }

    public String getMobileNumberVisitoStandardFormat(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.isEmpty()) return null;

        String b2bStandardPhoneNumber = ""; // LIKE 947XXXXXXXX

        mobileNumber = mobileNumber.replaceAll("\\s+", "");
        mobileNumber = mobileNumber.replaceAll("\\D+", "");

        if (mobileNumber.startsWith("+094") && mobileNumber.length() == 13) {
            b2bStandardPhoneNumber = mobileNumber.substring(2);
        } else if (mobileNumber.startsWith("+94") && mobileNumber.length() == 12) {
            b2bStandardPhoneNumber = mobileNumber.substring(1);
        }else if (mobileNumber.startsWith("94") && mobileNumber.length() == 11) {
            b2bStandardPhoneNumber = mobileNumber;
        } else if (mobileNumber.startsWith("0") && mobileNumber.length() == 10) {
            b2bStandardPhoneNumber = B2B_MOBILE_NUMBER_START_PREFIX + mobileNumber.substring(1);
        } else if (mobileNumber.startsWith("7") && mobileNumber.length() == 9) {
            b2bStandardPhoneNumber = B2B_MOBILE_NUMBER_START_PREFIX + mobileNumber;
        } else {
            //check foreign number or not
            log.info("Method : getMobileNumberTagsStandardFormat Foreign mobile number found : {}", mobileNumber);

            b2bStandardPhoneNumber = mobileNumber.startsWith("+") ? mobileNumber.substring(1) : mobileNumber;
            return ((b2bStandardPhoneNumber.length() > 8 && b2bStandardPhoneNumber.length() <= 20) && b2bStandardPhoneNumber.matches("[0-9]+")) ? b2bStandardPhoneNumber : null;
        }
        log.info("Method : getMobileNumberTagsStandardFormat local mobile number found : {}", mobileNumber);
        return b2bStandardPhoneNumber.length() == TAG_MOBILE_NUMBER_LENGTH && b2bStandardPhoneNumber.matches("[0-9]+") ? b2bStandardPhoneNumber : null;
    }

    public String getHiddenMobileNumber(String number) {

        String mobileStandardFormat = getMobileStandardFormat(number);
        if (mobileStandardFormat == null) return null;

        return HIDDEN_NUMBER_FORMAT + mobileStandardFormat.substring(7);
    }


}
