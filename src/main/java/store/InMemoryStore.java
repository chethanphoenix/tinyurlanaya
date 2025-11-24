package store;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStore {

    // shortCode -> longURL
    private final Map<String, String> urlMap = new HashMap<>();

    // Optional: longURL -> shortCode (avoid duplicates)
    private final Map<String, String> reverseMap = new HashMap<>();

    public void save(String shortCode, String longUrl) {
        urlMap.put(shortCode, longUrl);
        reverseMap.put(longUrl, shortCode);
    }

    public String getLongUrl(String shortCode) {
        return urlMap.get(shortCode);
    }

    public String getShortCode(String longUrl) {
        return reverseMap.get(longUrl);
    }

    public boolean exists(String shortCode) {
        return urlMap.containsKey(shortCode);
    }
}
