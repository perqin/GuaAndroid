package com.perqin.gua.data.retrofit;

import com.perqin.gua.BuildConfig;
import com.perqin.gua.data.models.PollingModel;
import com.perqin.gua.data.models.SyncModel;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public interface PollingService {
    String BASE_URL = BuildConfig.API_HOST;

    @GET("pollings/{student_id}")
    Observable<PollingModel> getPolling(@Path("student_id") String studentId, @Header("Authorization") String cookie);

    @POST("pollings/{student_id}")
    Observable<PollingModel> startPolling(@Path("student_id") String studentId, @Body PostPollingReq req);

    @POST("pollings/{studentId}/sync")
    Observable<SyncModel> postScoresSync(@Path("studentId") String studentId, @Header("Authorization") String cookie);

    @PUT("pollings/{studentId}")
    Observable<PollingModel> changePushService(@Path("studentId") String studentId, @Header("Authorization") String cookie, @Body PutPollingReq req);

    @DELETE("pollings/{student_id}")
    Observable<Void> stopPolling(@Path("student_id") String studentId, @Header("Authorization") String cookie);
}
