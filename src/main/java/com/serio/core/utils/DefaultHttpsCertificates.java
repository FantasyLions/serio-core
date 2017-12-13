package com.serio.core.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * https 服务器端证书认证，这个{@link initHostnameVerifier}函数会初始化证书认证器，这个认证器默认信任所有服务器端证书。
 * @author zl.shi
 *
 */
public class DefaultHttpsCertificates {
	
	private static final Logger	log	= LoggerFactory.getLogger(DefaultHttpsCertificates.class);
	
	/**
	 * 初始化CA证书验证器，默认信任所有的证书
	 * @throws Exception
	 */
	public static void initHostnameVerifier() throws Exception {
		HostnameVerifier hv = new HostnameVerifier() {  
	        public boolean verify(String urlHostName, SSLSession session) {
	        	log.info("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
	            return true;  
	        }
	    };
	    
	    trustAllHttpsCertificates();
	    HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
	
	
	/**
	 * 信任所有的CA证书
	 * @throws Exception
	 */
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    
    
    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;  
        }  
  
        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }  
  
        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }
  
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {  
            return;
        }
  
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {  
            return;
        }
    }

}
