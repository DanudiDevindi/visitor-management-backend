package com.swehg.visitormanagement.constant;

public class ApplicationConstant {

    /**
     * API Response
     */
    public static final String API_BASE_URL = "/v1";

    /**
     * Exception error message constants
     */
    //don't use this variable for other error messages (only exception class use)
    public static final String APPLICATION_ERROR_OCCURRED_MESSAGE = "Application Error Occurred";


    /**
     * Error Codes
     */
    public static final int RESOURCE_NOT_FOUND = 404;
    public static final int DUPLICATE_CORPORATE_CODE = 603;
    public static final int DUPLICATE_BRC = 604;
    public static final int INVALID_ENTERED = 609;

    public static final int CANT_WIRTE_FILE = 500;
    public static final int PASSWORD_FORMAT_WRONG = 501;
    public static final int INVALID_PASSWORD_ENTERED = 502;
    public static final int INSUFFICIENT_ADMIN_COUNT = 504;

    public static final int ADMIN_USER_NAME_DUPLICATE = 605;
    public static final int ADMIN_USER_EMAIL_DUPLICATE = 606;
    public static final int DUPLICATE_IDENTIFICATION_VALUE = 600;
    public static final int DUPLICATE_EMAIL = 601;
    public static final int DUPLICATE_MOBILE = 602;
    public static final int DUPLICATE_EMP_ID = 603;
    public static final int DUPLICATE_SUBSCRIPTION_NUMBER= 604;
    public static final int EXTERNAL_RECEPTION_NOT_ALLOWED = 607;
    public static final int ADMIN_USER_MOBILE_DUPLICATE = 608;

    public static final int UNAUTHORIZED_REQUEST = 405;
    public static final int VERIFICATION_FAILED = 101;
    public static final int BULK_UPLOAD_DATA_ERROR = 723;
    /**
     * Token sign in key
     */
    public static final String TOKEN_SIGN_IN_KEY = "w*KLT+Q9$L8Sa@BB";

}
