package stellarburgers.util;

import stellarburgers.model.UserCredentials;

import java.util.UUID;

public final class TestUserFactory {
    private TestUserFactory() {
    }

    public static UserCredentials randomUser() {
        String unique = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        return new UserCredentials("ui-test-" + unique + "@example.com", "password123", "Ui Test");
    }
}
