package com.url.shorter.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.url.shorter.api.entity.UrlEntity;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Integer> {
	UrlEntity findByShortUrl(String shortUrl);

	@Query(value = "Select u.totalVisit from UrlEntity u where u.shortUrl = :url")
	Long findTotalVisit(@Param("url") String shortUrl);
	
	List<UrlEntity> findByExpired(Boolean expired);
}
