package org.lgq.iot.sdk.chatgpt.api;

import io.reactivex.Single;
import org.lgq.iot.sdk.chatgpt.entity.billing.CreditGrantsResponse;
import org.lgq.iot.sdk.chatgpt.entity.chat.ChatCompletion;
import org.lgq.iot.sdk.chatgpt.entity.chat.ChatCompletionResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 *
 */
public interface Api {

    String DEFAULT_API_HOST = "https://api.openai.com/";

    /**
     * chat
     */
    @POST("v1/chat/completions")
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletion chatCompletion);


    /**
     * 余额查询
     */
    @GET("dashboard/billing/credit_grants")
    Single<CreditGrantsResponse> creditGrants();




}
