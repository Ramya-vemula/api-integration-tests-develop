package com.tissl.api.utils;

public final class EndPoint {

    public static final String ACCOUNTS = "/accounts";

    public static final String ACCOUNT_BY_ID = ACCOUNTS + "/{accountId}";

    public static final String VERIFY_ACCOUNTS = ACCOUNTS + "/_verify";

    public static final String RUNGWAYS = "/rungways";

    public static final String RUNGWAY_BY_ID = RUNGWAYS + "/{rungwayId}";

    public static final String GET_ACCESS_LIST_BY_RUNGWAY_ID = RUNGWAY_BY_ID + "/access_list";

    public static final String GET_RUNGWAYS_BY_ACCOUNT_ID = ACCOUNT_BY_ID + "/rungways";

    public static final String PROFILE_BY_RUNGWAY_ID_AND_ACCOUNT_ID = GET_RUNGWAYS_BY_ACCOUNT_ID + "/{rungwayId}/profile";

    public static final String DISCUSSIONS_BY_RUNGWAY_ID = RUNGWAY_BY_ID + "/discussions";

    public static final String DISCUSSIONS_BY_DISCUSSION_ID = DISCUSSIONS_BY_RUNGWAY_ID + "/{discussionId}";

    public static final String RESPONSES_BY_DISCUSSION_ID = DISCUSSIONS_BY_DISCUSSION_ID + "/responses";

    public static final String RESPONSES_BY_RUNGWAY_ID = RUNGWAY_BY_ID + "/responses";

    public static final String RESPONSES_BY_RUNGWAY_ID_BY_STATE = RUNGWAY_BY_ID + "/responses?states={states}";

    public static final String RESPONSE_BY_RESPONSE_ID = RESPONSES_BY_DISCUSSION_ID + "/{responseId}";

    public static final String COMMENTS_BY_RESPONSE_ID = RESPONSE_BY_RESPONSE_ID + "/comments";

    public static final String OAUTH = "/oauth/token";

    public static final String RESET_PASSWORD = ACCOUNTS + "/password/reset-tokens";

    public static final String RESET_PASSWORD_BY_TOKEN_ID = ACCOUNT_BY_ID + "/reset-tokens/{resetTokenId}";

    public static final String RESET_PASSWORD_BY_ACCOUNT_ID = ACCOUNT_BY_ID + "password";

    public static final String EVENTS = "events";

    private EndPoint() {
        super();
    }
}
