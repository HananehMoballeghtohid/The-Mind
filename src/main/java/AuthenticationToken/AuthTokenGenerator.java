package AuthenticationToken;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class AuthTokenGenerator {
    private final SecureRandom random;

    /**
     * Generates a SecureRandom
     */

    public AuthTokenGenerator() {
        random = new SecureRandom();
    }

    /**
     * Creates a new token with the use of
     * secureRandom and the time of which
     * the client joined the server
     * @return String AuthenticationToken
     */

    public String nextToken() {
        byte bytes[] = new byte[20];
        LocalDateTime localTime = LocalDateTime.now();
        int time = localTime.getHour() +
                localTime.getMinute() +
                localTime.getSecond() +
                localTime.getNano();
        random.nextBytes(bytes);
        return bytes.toString() + "" + time;
    }
}
