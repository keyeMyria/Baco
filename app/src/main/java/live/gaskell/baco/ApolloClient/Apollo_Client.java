package live.gaskell.baco.ApolloClient;

import android.content.Context;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy;
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Nonnull;

import live.gaskell.baco.Cuenta.AccountInformationManager;
import live.gaskell.baco.Cuenta.UserInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by carlo on 11/02/2018.
 */

public class Apollo_Client implements UserInterface {
    private static final String BaseUrl = "https://api.graph.cool/simple/v1/cjfkiedkw399g0154dfo1tayt";
    private static final String HEADER = "Authorization";


    public static ApolloClient getApolloClient() {
        ApolloClient apolloClient;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        apolloClient = ApolloClient.builder()
                .serverUrl(BaseUrl)
                .okHttpClient(okHttpClient)
                .build();


        return apolloClient;
    }

    public static ApolloClient getApolloClientLogin(Context context) {
        NormalizedCacheFactory cacheFactory = new LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10 * 1024).build());
        CacheKeyResolver cacheKeyResolver = new CacheKeyResolver() {
            @Nonnull
            @Override
            public CacheKey fromFieldRecordSet(@Nonnull ResponseField field, @Nonnull Map<String, Object> recordSet) {
                if (recordSet.containsKey("id")) {
                    String id = (String) recordSet.get("id");
                    return CacheKey.from(id);
                }
                return CacheKey.NO_KEY;
            }

            @Nonnull
            @Override
            public CacheKey fromFieldArguments(@Nonnull ResponseField field, @Nonnull Operation.Variables variables) {
                return CacheKey.NO_KEY;
            }
        };

        ApolloClient apolloClient = ApolloClient.builder()
                .serverUrl(BaseUrl)
                .okHttpClient(okHttpClientLogin(context))
                .normalizedCache(cacheFactory, cacheKeyResolver)
                .build();

        return apolloClient;
    }

    private static OkHttpClient okHttpClientLogin(final Context context) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@Nonnull Chain chain) throws IOException {
                        return chain.proceed(chain.request()
                                .newBuilder()
                                .header(HEADER,
                                        AccountInformationManager.getAccountData(
                                                TOKEN,
                                                context))
                                .build());
                    }
                }).build();
    }

}
