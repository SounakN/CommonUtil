package Constants;

import java.util.HashMap;

public class ConstantsForTeamsSettings {
    private final static String COMPANY_EMAIL_ID = "Company Email ID";
    private final static String PERSONAL_EMAIL_ID = "Personal Email ID";
    private final static String MOBILE_NUMBER = "Mobile Number";
    private final static String FIRST_NAME = "First Name";
    private final static String LAST_NAME = "Last Name";
    private final static String DATE_OF_BIRTH = "Date of Birth";
    public static HashMap<String, String> teamSettingsMapping = new HashMap<>() {{
        put(COMPANY_EMAIL_ID, "companyEmail");
        put(PERSONAL_EMAIL_ID, "personal_email");
        put(MOBILE_NUMBER, "mobileNumber");
        put(FIRST_NAME, "firstName");
        put(LAST_NAME, "lastName");
        put(DATE_OF_BIRTH, "DOB");
    }};
}
