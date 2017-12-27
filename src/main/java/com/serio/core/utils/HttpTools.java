package com.serio.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpTools {
  static Logger logger = LoggerFactory.getLogger(HttpTools.class);
  public static String doGet(String url, String queryString) {
    String responseStr = null;
    HttpClient client = HttpClientBuilder.create().build();
    if (StringUtils.isNotBlank(queryString))
      url += "?" + queryString;
    HttpGet request = new HttpGet(url);
    // get response string
    try {
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        responseStr = getResponseString(response);
      }
      logger.info("HttpGet {} response code {}", url, response.getStatusLine().getStatusCode());
    } catch (IOException e) {
       logger.error("Error {} when HttpGet {}", e.getMessage(), url);
    }
    return responseStr;
  }

  public static String doPost(String url, Map<String,String> params){
    String responseStr = null;
    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpPost post = new HttpPost(url);
      // add post parameters
      List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
      if(!params.isEmpty()){
        for (Map.Entry<String, String> entry : params.entrySet()) {
          urlParameters.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
      }
      // get response string
      HttpResponse response = client.execute(post);
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        responseStr = getResponseString(response);
      }
      logger.info("HttpPost {} response code {}", url, response.getStatusLine().getStatusCode());
    } catch(IOException e) {
      logger.error("Error {} when HttpPost {}", e.getMessage(), url);
    }
        
    return responseStr;
  }
    
  
//HTTP POST request
  public static String sendPost(String url, JSONObject jsonParam, Map<String,String> headers) {
      String responseStr = null;
      try {
          HttpClient client = HttpClientBuilder.create().build();
          HttpPost post = new HttpPost(url);
   
          // add header
          if (headers != null) {
            post.setHeader("Content-Type", "application/json");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
          }
  
          StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
          entity.setContentEncoding("UTF_8");  
          entity.setContentType("application/json");
          post.setEntity(entity);

          HttpResponse response = client.execute(post);
          if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            responseStr = getResponseString(response);
          }
          logger.info("HttpPost {} response code {}", url, response.getStatusLine().getStatusCode());
      } catch (IOException e) {
        logger.error("Error {} when HttpPost {}", e.getMessage(), url);
      }
      return responseStr;
  }
    
  private static String getResponseString(HttpResponse response) throws UnsupportedOperationException, IOException {
    StringBuffer result = new StringBuffer();
    BufferedReader rd = new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()));

    String line = "";
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
    return result.toString();
  }
  
  public static String doGetURL(String url ) {
    String responseStr = null;
    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);
    // get response string
    try {
      HttpResponse response = client.execute(request);
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        responseStr = getResponseString(response);
      }
      logger.info("HttpGet {} response code {}", url, response.getStatusLine().getStatusCode());
    } catch (IOException e) {
       logger.error("Error {} when HttpGet {}", e.getMessage(), url);
    }
    return responseStr;
  }
  
  
}
