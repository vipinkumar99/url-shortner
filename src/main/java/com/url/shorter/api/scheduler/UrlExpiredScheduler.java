package com.url.shorter.api.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import com.url.shorter.api.dao.UrlRepository;
import com.url.shorter.api.entity.UrlEntity;

@Configuration
@EnableScheduling
public class UrlExpiredScheduler {

	@Autowired
	private UrlRepository urlRepository;

	@Scheduled(initialDelay = 1000, fixedRate = 15000)
	public void checkAndUpdateUrlExpired() {
		List<UrlEntity> urls = urlRepository.findByExpired(false);
		if (!CollectionUtils.isEmpty(urls)) {
			for (UrlEntity entity : urls) {
				if (LocalDateTime.now().isAfter(entity.getExpiredAt())) {
					entity.setExpired(true);
					urlRepository.save(entity);
				}
			}
		}
	}

}
