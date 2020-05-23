package com.parking.serverP.util;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UriUtil {

    public static URI buildUri(String path, Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(id).toUri();
    }
}