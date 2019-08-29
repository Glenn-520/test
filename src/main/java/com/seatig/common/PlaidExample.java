package com.seatig.common;


import java.net.*;
import java.io.*;

import com.plaid.client.PlaidClient;
import com.sun.net.httpserver.*;

import com.plaid.client.*;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

/**
 * @version V1.0
 * @className: PlaidExample
 * @description: Plaid实例
 * @author: glenn
 * @create: 2019-08-23 10:07
 **/
public class PlaidExample {
    private static final String PUBLIC_KEY = "PLAID_PUBLIC_KEY";
    private static final String CLIENT_ID = "PLAID_CLIENT_ID";
    private static final String SECRET = "PLAID_SECRET";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(
                new InetSocketAddress("localhost", 8000), 0);
        server.createContext("/get_access_token", new GetAccessToken());
        server.setExecutor(null);
        server.start();
    }

    static class GetAccessToken implements HttpHandler {
        private static PlaidClient plaidClient;

        private String publicToken;
        private String accessToken;
        private String itemId;

        public void handle(HttpExchange t) throws IOException {
            //通过public token交换私有密钥
            InputStream is = t.getRequestBody();
            publicToken = (String) t.getAttribute("public_token");
//            publicToken = is.publicToken();

            // 通过bulid方法生成PlaidClient
            PlaidClient plaidClient = PlaidClient.newBuilder()
                    .clientIdAndSecret(CLIENT_ID, SECRET)
                    .publicKey(PUBLIC_KEY)
                    .sandboxBaseUrl() // sandbox Plaid environment
                    .logLevel(HttpLoggingInterceptor.Level.BODY)
                    .build();

            // 交换 public_token 和 access_token

            Response<ItemPublicTokenExchangeResponse> execute = plaidClient
                    .service()
                    .itemPublicTokenExchange(
                            new ItemPublicTokenExchangeRequest(publicToken))
                    .execute();


            accessToken = execute.body().getAccessToken();
            itemId = execute.body().getItemId();
        }
    }
}
