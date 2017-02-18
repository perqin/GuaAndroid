package com.perqin.gua.data.repositories;

import com.perqin.gua.data.models.PollingModel;

import rx.Observable;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

interface IPollingsRepository {
    Observable<PollingModel> startPolling(String studentId, String cookie, String service, String clientToken);

    Observable<Void> stopPolling(String studentId, String cookie);

    Observable<PollingModel> getPolling(String studentId, String cookie);
}
