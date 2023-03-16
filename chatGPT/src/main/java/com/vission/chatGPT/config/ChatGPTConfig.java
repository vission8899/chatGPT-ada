package com.vission.chatGPT.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import com.vission.chatGPT.properties.ChatGPTProperties;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import retrofit2.Retrofit;

@Configuration
@RequiredArgsConstructor
@Import(ChatGPTProperties.class)
public class ChatGPTConfig {

    private final ChatGPTProperties properties;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);


    @Bean
    public OpenAiService openAiService() {
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(properties.getProxyHostName(), properties.getProxyPort()));
        OkHttpClient client = OpenAiService.defaultClient(properties.getApiKey(), Duration.ofSeconds(500))
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api);
    }

//    @Bean
//    public OpenAiClient openAiClient() {
//        //代理可以为null
//        Proxy proxy = new Proxy(Proxy.Type.HTTP,
//                new InetSocketAddress(properties.getProxyHostName(), properties.getProxyPort()));
//        //日志输出可以不添加
//        List<Interceptor> interceptors = new ArrayList<>();
//        if (properties.getLog()) {
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            interceptors.add(httpLoggingInterceptor);
//        }
//        return OpenAiClient.builder()
//                .apiKey(properties.getApiKey())
//                .connectTimeout(properties.getConnectTimeout())
//                .writeTimeout(properties.getWriteTimeout())
//                .readTimeout(properties.getReadTimeout())
//                .interceptor(interceptors)
//                .proxy(proxy)
//                .apiHost(properties.getApiHost())
//                .build();
//    }

}
