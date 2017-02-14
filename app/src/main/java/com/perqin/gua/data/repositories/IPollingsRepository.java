package com.perqin.gua.data.repositories;

import com.perqin.gua.data.models.PollingModel;

import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public interface IPollingsRepository {
    Observable<PollingModel> startPolling(String studentId, String cookies);

    Observable<Void> stopPolling(String studentId);

//    Observable<List<PollingModel>> getPollings(String studentId);

    Observable<PollingModel> getPolling(String studentId);
}
