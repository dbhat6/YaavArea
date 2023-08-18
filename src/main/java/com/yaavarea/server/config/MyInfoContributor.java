package com.yaavarea.server.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class MyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("name", "YaavArea");
        infoMap.put("description", "This is the spring boot server application for YaavArea");
        infoMap.put("version", "0.0.1");
        builder.withDetail("app", infoMap);
    }
}
