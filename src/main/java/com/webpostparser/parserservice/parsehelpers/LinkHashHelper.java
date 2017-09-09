package com.webpostparser.parserservice.parsehelpers;

import com.webpostparser.appconfig.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Domagoj Pordan on 20.08.17..
 */
@Service
public final class LinkHashHelper {
    private ConfigService configService;
    private Charset encoding;
    private MessageDigest messageDigest;

    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    @PostConstruct
    public void initLinkHashHelper()
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.encoding  = Charset.forName(configService.getEncodingString());
        this.messageDigest = MessageDigest.getInstance(configService.gethashAlgorithm());
    }

    public int getHashValueOfString(String value) {
        try {
            byte[] linkBytes = value.getBytes(encoding.name());
            byte[] mdVal = messageDigest.digest(linkBytes);
            return ByteBuffer.wrap(mdVal).getInt();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
