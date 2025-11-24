package controller;

import service.UrlService;

public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    public String createShortUrl(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            return "Invalid URL";
        }

        String code = service.shortenUrl(longUrl);
        return "Short URL: http://tinyurl/" + code;
    }

    public String getOriginalUrl(String code) {
        if (code == null || code.isEmpty()) {
            return "Invalid short code";
        }

        String longUrl = service.getLongUrl(code);
        return longUrl == null ? "Not found" : "Original URL: " + longUrl;
    }
}
