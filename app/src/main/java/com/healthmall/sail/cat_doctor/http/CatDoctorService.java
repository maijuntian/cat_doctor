package com.healthmall.sail.cat_doctor.http;


import com.healthmall.sail.cat_doctor.bean.BloodOxygenReport;
import com.healthmall.sail.cat_doctor.bean.BloodPressureReport;
import com.healthmall.sail.cat_doctor.bean.BodyReport;
import com.healthmall.sail.cat_doctor.bean.BodyRespone;
import com.healthmall.sail.cat_doctor.bean.CDRespone;
import com.healthmall.sail.cat_doctor.bean.Question;
import com.healthmall.sail.cat_doctor.bean.QuestionReport;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mai on 2017/11/20.
 */
public interface CatDoctorService {

    @POST("doctorMall/report/body")
    Observable<CDRespone<BodyRespone>> bodyReport(@Body RequestBody json);


    @POST("doctorMall/report/bloodOxygen")
    Observable<CDRespone<Object>> bloodOxygenReport(@Body RequestBody json);


    @POST("doctorMall/report/bloodPressure")
    Observable<CDRespone<Object>> bloodPressureReport(@Body RequestBody json);

    @Multipart
    @POST("doctorMall/report/testUpload")
    Observable<CDRespone<Object>> faceTonUpload(@PartMap Map<String, RequestBody> params);


    @POST("sail/deviceManager/bind")
    Observable<CDRespone> bindDevice(@Body RequestBody json);

    @POST("sail/question/answer")
    Observable<CDRespone<Question>> questionAnswer(@Query("accessToken") String accessToken, @Body RequestBody json);

    @POST("sail/question/pre_subject")
    Observable<CDRespone<Question>> preQuestion(@Query("accessToken") String accessToken, @Body RequestBody json);

    @POST("sail/question/result")
    Observable<CDRespone<QuestionReport>> questionResult(@Query("accessToken") String accessToken, @Body RequestBody json);

    @POST("doctorMall/report/saveUserInfo")
    Observable<CDRespone<Object>> saveUserInfo(@Query("accessToken") String accessToken, @Body RequestBody json);

    @POST("doctorMall/report/quit")
    Observable<CDRespone<Object>> quit(@Query("accessToken") String accessToken, @Body RequestBody json);

}

