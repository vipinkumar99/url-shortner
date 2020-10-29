package com.url.shorter.api.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.url.shorter.api.dao.UrlRepository;
import com.url.shorter.api.entity.UrlEntity;
import com.url.shorter.api.exception.UrlException;
import com.url.shorter.api.pojo.UrlRequest;
import com.url.shorter.api.pojo.UrlResponse;

@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlRepository urlRepository;

	@Override
	public UrlResponse createShortUrl(UrlRequest request) throws RuntimeException {
		if (request == null) {
			throw new UrlException("Request body is empty!");
		}
		if (!request.getLongUrl().startsWith("http") || request.getLongUrl().startsWith("/https")) {
			throw new UrlException("Url is not valid!");
		}
		
		UrlEntity entity = new UrlEntity();
		entity.setLongUrl(request.getLongUrl());
		entity.setExpired(false);
		entity.setShortUrl(UUID.randomUUID().toString().replaceAll("-", ""));
		entity.setTotalVisit(0l);
		entity.setExpiredAt(LocalDateTime.now().plusMinutes(15));
		urlRepository.save(entity);

		UrlResponse response = new UrlResponse();
		response.setLongUrl(entity.getLongUrl());
		response.setShortUrl(entity.getShortUrl());
		return response;
	}

	@Override
	public String accessShortUrl(String shortUrl) throws RuntimeException {
		if (StringUtils.isEmpty(shortUrl)) {
			throw new UrlException("url can not be empty!");
		}
		UrlEntity entity = urlRepository.findByShortUrl(shortUrl);
		if (entity == null || entity.getExpired()) {
			throw new UrlException("This url is invalid or expired!");
		}
		if (LocalDateTime.now().isAfter(entity.getExpiredAt())) {
			entity.setExpired(true);
			urlRepository.save(entity);
			throw new UrlException("This url is expired!");
		}
		entity.setTotalVisit(entity.getTotalVisit() + 1);
		urlRepository.save(entity);
		return entity.getLongUrl();
	}

	@Override
	public Long totalVisit(String shortUrl) {
		Long totalVisit = urlRepository.findTotalVisit(shortUrl);
		return totalVisit == null ? 0l : totalVisit;
	}

}
