package com.url.shorter.api.service;

import com.url.shorter.api.pojo.UrlRequest;
import com.url.shorter.api.pojo.UrlResponse;

public interface UrlService {
	UrlResponse createShortUrl(UrlRequest request) throws RuntimeException;
	String accessShortUrl(String shortUrl)throws RuntimeException;
	Long totalVisit(String shortUrl);
}
