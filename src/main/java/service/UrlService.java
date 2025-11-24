package service;

import store.DatabaseStore;

import java.util.Random;

public class UrlService {

    private final DatabaseStore store;
    private final Random random = new Random();

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 7;

    public UrlService(DatabaseStore store) {
        this.store = store;
    }

    public String shortenUrl(String longUrl) {
        // check if already exists
        String existing = store.getShortCode(longUrl);
        if (existing != null) {
            return existing;
        }

        String code;
        do {
            code = generateShortCode();
        } while (store.exists(code));

        store.save(code, longUrl);
        return code;
    }

    public String getLongUrl(String code) {
        return store.getLongUrl(code);
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
