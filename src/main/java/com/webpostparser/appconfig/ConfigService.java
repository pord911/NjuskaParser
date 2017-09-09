package com.webpostparser.appconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Domagoj Pordan on 20.08.17..
 */
@Service
public class ConfigService {
    @Value("${encoding}")
    private String encodingString;
    @Value("${hashalgorithm}")
    private String hashAlgorithm;

    public String getEncodingString() {
        return encodingString;
    }

    public String gethashAlgorithm() {
        return hashAlgorithm;
    }
}
