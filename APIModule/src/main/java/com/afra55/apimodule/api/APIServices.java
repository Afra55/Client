package com.afra55.apimodule.api;

import com.afra55.apimodule.bean.TranslateBean;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Victor Yang on 2016/7/1.
 */
public interface APIServices {

    @POST(APIField.OtherHttp.TRANSLATE_API)
    Observable<TranslateBean> toTranslate(@QueryMap Map<String, String> map);
}
