package com.server.global.utils;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

public class UriCreator {
<<<<<<< HEAD
    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
=======
	public static URI createUri(String defaultUrl, long resourceId) {
		return UriComponentsBuilder
			.newInstance()
			.path(defaultUrl + "/{resource-id}")
			.buildAndExpand(resourceId)
			.toUri();
	}

>>>>>>> 21e43da99e0660e1051c1412b2b6dade9bafe523
}
