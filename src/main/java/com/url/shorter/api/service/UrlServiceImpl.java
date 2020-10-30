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

@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlRepository urlRepository;

	@Override
	public String createShortUrl(UrlRequest request) throws RuntimeException {
		System.out.println("service is called");
		if (request == null) {
			throw new UrlException("Request body is empty!");
		}
		if (!request.getLongUrl().startsWith("http") || request.getLongUrl().startsWith("/https")) {
			throw new UrlException("Url is not valid!");
		}
		UrlEntity entity = urlRepository.findByLongUrl(request.getLongUrl());
		if (entity != null) {
			if (!entity.getExpired() && LocalDateTime.now().isBefore(entity.getExpiredAt())) {
				return entity.getShortUrl();
			}
			if (entity.getExpired() || LocalDateTime.now().isAfter(entity.getExpiredAt())) {
				entity.setExpired(false);
			}
			entity.setShortUrl(getUrl());
			entity.setExpiredAt(getExpireTime());
			urlRepository.save(entity);
			return entity.getShortUrl();
		} else {
			entity = new UrlEntity();
			entity.setLongUrl(request.getLongUrl());
			entity.setTotalVisit(0l);
			entity.setExpired(false);
			entity.setShortUrl(getUrl());
			entity.setExpiredAt(getExpireTime());
			urlRepository.save(entity);
			return entity.getShortUrl();
		}
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

	private String getUrl() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	private LocalDateTime getExpireTime() {
		return LocalDateTime.now().plusMinutes(5);
	}
}
