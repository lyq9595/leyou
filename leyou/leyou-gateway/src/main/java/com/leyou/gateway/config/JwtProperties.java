package com.leyou.gateway.config;

import com.leyou.common.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
@ConfigurationProperties(prefix = "leyou.jwt")
public class JwtProperties {

  private String pubKeyPath; // 公钥

  private String cookieName;

  private PublicKey publicKey; // 公钥

  private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

  /** @PostContruct：在构造方法执行之后执行该方法 */
  @PostConstruct
  public void init() {
    try {
      // 获取公钥
      this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    } catch (Exception e) {
      logger.error("初始化失败！", e);
      throw new RuntimeException();
    }
  }

  // getter setter ...

  public String getPubKeyPath() {
    return pubKeyPath;
  }

  public void setPubKeyPath(String pubKeyPath) {
    this.pubKeyPath = pubKeyPath;
  }

  public String getCookieName() {
    return cookieName;
  }

  public void setCookieName(String cookieName) {
    this.cookieName = cookieName;
  }

  public PublicKey getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(PublicKey publicKey) {
    this.publicKey = publicKey;
  }

  public static Logger getLogger() {
    return logger;
  }
}
