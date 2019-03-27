package com.rungway.api.utils;

import com.tissl.api.TestSetup;
import org.joda.time.DateTime;

public final class TestData {
    private static TestSetup setup;

    static {
        setup = new TestSetup();
    }

    public static String getProperty(final String propertyKey) {
        return setup.getProperties().getProperty(propertyKey);
    }

    public static final long RESPONSE_TIME = 2000;

    public static final String TEST_MESSAGE = "Automation Test Message " + DateTime.now();

    public static final String USER_STATUS_ACTIVE = "ACTIVE";

    public static final String ROLE_ADMIN = "ADMIN";

    public static final String APP_ROLE_RW = "READ_WRITE";

    public static final String USER_STATUS_DEACTIVATED = "DEACTIVATED";

    public static final String STATUS_DRAFT = "DRAFT";

    public static final String STATUS_TO_REVIEW = "TO_REVIEW";

    public static final String STATUS_APPROVED = "APPROVED";

    public static final String STATUS_DELETED = "DELETED";

    public static final String INVALID_TOKEN = "InvalidToken";

    public static final String RESPONSE_CREATED_TIME = "2018-05-11T15:43:50.626990Z";

    public static final String COMMENT_CREATED_TIME = "2018-05-30T15:54:23.333356Z";

    public static final String DISCUSSION_CREATED_TIME = "2018-05-11T15:39:55.750449Z";

    public static final String DISCUSSION_TITLE = "I have been asked to moderate a panel for the first time";

    public static final String DISCUSSION_BODY = "Any tips on making it flow well?";

    public static final String RESPONSE_BODY = "Expresso! But seriously, I am sure the company spends a lot OF"
            + " money WITH this hotel AND has the leverage TO GET you a room change TO a quieter part OF the hotel.";

    public static final String COMMENT_BODY = "Thanks!";

    public static final String USER_EMAIL = getProperty("USER_EMAIL");

    public static final String USER_ACCOUNT_ID = getProperty("USER_ACCOUNT_ID");

    public static final String USER_PASSWORD = getProperty("USER_PASSWORD");

    public static final String INVALID_ID = "1111111-1111-1111-1111-111111111111";

    public static final String INVALID_USER_EMAIL = "test@rungway.com";

    public static final String EDIT_USER_EMAIL = getProperty("EDIT_USER_EMAIL");

    public static final String EDIT_USER_ACCOUNT_ID = getProperty("EDIT_USER_ACCOUNT_ID");

    public static final String RUNGWAY_NAME = getProperty("RUNGWAY_NAME");

    public static final String RUNGWAY_ID = getProperty("RUNGWAY_ID");

    public static final String PUBLIC_DISCUSSION_ID = getProperty("PUBLIC_DISCUSSION_ID");

    public static final String ANONYMOUS_DISCUSSION_ID = getProperty("ANONYMOUS_DISCUSSION_ID");

    public static final String RESPONSE_ID = getProperty("RESPONSE_ID");

    public static final String EDIT_RESPONSE_ID = getProperty("EDIT_RESPONSE_ID");

    public static final String EDIT_DISCUSSION_ID = getProperty("EDIT_DISCUSSION_ID");

    public static final String NO_RESPONSE_DISCUSSION_ID = getProperty("NO_RESPONSE_DISCUSSION_ID");

    public static final String OTHERS_RESPONSE_ID = getProperty("OTHERS_RESPONSE_ID");

    public static final String OTHERS_DISCUSSION_ID = getProperty("OTHERS_DISCUSSION_ID");

    public static final String RESPONSE_CREATED_BY = getProperty("RESPONSE_CREATED_BY");

    public static final String COMMENT_AUTHOR_ID = getProperty("COMMENT_AUTHOR_ID");

    private TestData() {
        super();
    }

}
