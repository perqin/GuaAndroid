package com.perqin.gua.data.retrofit;

import com.perqin.gua.data.models.PollingModel;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public interface PollingService {
    String BASE_URL = "http://gua.perqin.com/api/";

    @GET("pollings/{student_id}")
    Observable<PollingModel> getPolling(@Path("student_id") String studentId);

    @POST("pollings/{student_id}")
    Observable<PollingModel> startPolling(@Path("student_id") String studentId, @Body PostPollingReq req);

    @DELETE("pollings/{student_id}")
    Observable<Void> stopPolling(@Path("student_id") String studentId);
}
