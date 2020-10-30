package com.url.shorter.api.service;

import com.url.shorter.api.pojo.UrlRequest;

public interface UrlService {
	String createShortUrl(UrlRequest request) throws RuntimeException;
	String accessShortUrl(String shortUrl)throws RuntimeException;
	Long totalVisit(String shortUrl);
}
