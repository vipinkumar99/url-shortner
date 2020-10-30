package com.url.shorter.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shorter.api.pojo.BaseResponse;
import com.url.shorter.api.pojo.UrlRequest;
import com.url.shorter.api.service.UrlService;

@RestController
@RequestMapping(path = "/urls")
public class UrlController {

	@Autowired
	private UrlService urlService;

	@PostMapping(path = "/createShortUrl")
	public BaseResponse<String> createShortUrl(@RequestBody UrlRequest request) throws RuntimeException {
		return new BaseResponse<>("success", false, urlService.createShortUrl(request));
	}

	@GetMapping(path = "/{shortUrl}")
	public BaseResponse<String> accessShortUrl(@PathVariable("shortUrl") String shortUrl) throws RuntimeException {
		return new BaseResponse<>("success", false, urlService.accessShortUrl(shortUrl));
	}

	@GetMapping(path = "/shortUrlStats/{shortUrl}")
	public BaseResponse<Long> getShortUrlStats(@PathVariable("shortUrl") String shortUrl) throws RuntimeException {
		return new BaseResponse<>("success", false, urlService.totalVisit(shortUrl));
	}

}
