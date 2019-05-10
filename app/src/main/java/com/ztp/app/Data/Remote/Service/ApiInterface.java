package com.ztp.app.Data.Remote.Service;

import com.ztp.app.Data.Remote.Model.Request.BlogSearchRequest;
import com.ztp.app.Data.Remote.Model.Request.CSOAllRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_1;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_2;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_3;
import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Request.DeleteShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventsRequest;
import com.ztp.app.Data.Remote.Model.Request.GetMonthEventDateRequest;
import com.ztp.app.Data.Remote.Model.Request.GetProfileRequest;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.GetShiftDetailRequest;
import com.ztp.app.Data.Remote.Model.Request.GetShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.LoginRequest;
import com.ztp.app.Data.Remote.Model.Request.PostVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Request.SearchEventRequest;
import com.ztp.app.Data.Remote.Model.Request.ShiftUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.SiftAddRequest;
import com.ztp.app.Data.Remote.Model.Request.StudentRegisterRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateProfileRequest;
import com.ztp.app.Data.Remote.Model.Request.ValidateOtpRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Response.AddEventResponse;
import com.ztp.app.Data.Remote.Model.Response.BlogSearchResponse;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_1;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_2;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_3;
import com.ztp.app.Data.Remote.Model.Response.DeleteEventResponse;
import com.ztp.app.Data.Remote.Model.Response.DeleteShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetMonthEventDateResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftListResponse;
import com.ztp.app.Data.Remote.Model.Response.LoginResponse;
import com.ztp.app.Data.Remote.Model.Response.PostVolunteerRequestResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftAddResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftUpdateResponse;
import com.ztp.app.Data.Remote.Model.Response.StudentRegisterResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateEventResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadDocumentResponse;
import com.ztp.app.Data.Remote.Model.Response.ValidateOtpResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("user-access.php?api_key=1234&action=login")
    Call<LoginResponse> doLogin(@Body LoginRequest loginRequest);

    @GET("master-data.php?api_key=1234&action=scountry")
    Call<CountryResponse> getCountry();

    @POST("master-data.php?api_key=1234&action=sstate")
    Call<StateResponse> getStates(@Body StateRequest stateRequest);

    @GET("master-data.php?api_key=1234&action=sschool")
    Call<SchoolResponse> getSchool();

    @POST("user-access.php?api_key=1234&action=step1")
    Call<StudentRegisterResponse> doStudentRegister(@Body StudentRegisterRequest studentRegisterRequest);

    @POST("user-access.php?api_key=1234&action=validate_otp")
    Call<ValidateOtpResponse> validateOtp(@Body ValidateOtpRequest validateOtpRequest);

    @POST("user-access.php?api_key=1234&action=step1")
    Call<CsoRegisterResponseStep_1> doCsoRegisterStep_1(@Body CsoRegisterRequestStep_1 csoRegisterRequestStep_1);

    @POST("user-access.php?api_key=1234&action=csostep2")
    Call<CsoRegisterResponseStep_2> doCsoRegisterStep_2(@Body CsoRegisterRequestStep_2 csoRegisterRequestStep_2);

    @POST("user-access.php?api_key=1234&action=csostep3")
    Call<CsoRegisterResponseStep_3> doCsoRegisterStep_3(@Body CsoRegisterRequestStep_3 csoRegisterRequestStep_3);

    @POST("user-access.php?api_key=1234&action=getprofile")
    Call<GetProfileResponse> doGetProfile(@Body GetProfileRequest getProfileRequest);

    @POST("user-access.php?api_key=1234&action=update_account")
    Call<UpdateProfileResponse> doUpdateProfile(@Body UpdateProfileRequest updateProfileRequest);

    @POST("cso-action.php?api_key=1234&action=get_all_shift")
    Call<GetEventShiftResponse> getEventShift(@Body GetEventShiftRequest getEventShiftRequest);

    @POST("cso-action.php?api_key=1234&action=get_event_detail")
    Call<GetEventDetailResponse> getEventDetail(@Body GetEventDetailRequest getEventDetailRequest);

    @GET("master-data.php?api_key=1234&action=stimezone")
    Call<TimeZoneResponse> getTimezone();

    @GET("master-data.php?api_key=1234&action=sevents")
    Call<EventTypeResponse> getEventtype();

    @POST("cso-action.php?api_key=1234&action=i_shift")
    Call<ShiftAddResponse> doAddShift(@Body SiftAddRequest siftAddRequest);

    @POST("cso-action.php?api_key=1234&action=i_event")
    Call<AddEventResponse> doAddEvent(@Body EventAddRequest eventAddRequest);

    @POST("cso-action.php?api_key=1234&action=u_event")
    Call<UpdateEventResponse> doUpdateEvent(@Body EventUpdateRequest eventUpdateRequest);

    @Multipart
    @POST("fileupload.php")//http://hashtaglabs.in/staging/supercomp/filedata.php
    Call<ResponseBody> uploadDocument(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("user_file_title") String use_file_title,@Query("fileExtension") String extension);

    @Multipart
    @POST("file-upload.php")//http://hashtaglabs.in/staging/supercomp/filedata.php
    Call<UploadDocumentResponse> uploadDocumentNew(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("user_file_title") String use_file_title,@Query("api_key") String apiKey,@Query("action") String action);

    @POST("cso-action.php?api_key=1234&action=get_shift_detail")
    Call<GetShiftDetailResponse> getShiftDetail(@Body GetShiftDetailRequest getShiftDetailRequest);

    @POST("cso-action.php?api_key=1234&action=get_all_event")
    Call<GetEventsResponse> getEventList(@Body GetEventsRequest getEventsRequest);

    @POST("cso-action.php?api_key=1234&action=get_all_shift")
    Call<GetShiftListResponse> getShiftList(@Body GetShiftListRequest getShiftListRequest);

    @POST("cso-action.php?api_key=1234&action=d_event")
    Call<DeleteEventResponse> getDeleteEvent(@Body DeleteEventRequest deleteEventRequest);

    @POST("site-data.php?api_key=1234&action=blog_search")
    Call<BlogSearchResponse> getBlogSearch(@Body BlogSearchRequest blogSearchRequest);

    @POST("cso-action.php?api_key=1234&action=d_shift")
    Call<DeleteShiftResponse> getDeleteShift(@Body DeleteShiftRequest deleteShiftRequest);

    @POST("cso-action.php?api_key=1234&action=u_shift")
    Call<ShiftUpdateResponse> doUpdateShift(@Body ShiftUpdateRequest shiftUpdateRequest);

    @POST("search-event.php?api_key=1234&action=search_event")
    Call<SearchEventResponse> getSearchedEvents(@Body SearchEventRequest searchEventRequest);

    @POST("search-event.php?api_key=1234&action=event_send_request")
    Call<PostVolunteerRequestResponse> postVolunteerRequest(@Body PostVolunteerRequest postVolunteerRequest);

    @POST("cso-action.php?api_key=1234&action=cso_all_request")
    Call<CSOAllResponse> getCSOAllRequest(@Body CSOAllRequest csoAllRequest);

    @POST("vol-action.php?api_key=1234&action=vol_all_request")
    Call<VolunteerAllResponse> getVolunteerAllRequest(@Body VolunteerAllRequest volunteerAllRequest);

    @POST("cso-action.php?api_key=1234&action=get_month_event")
    Call<GetMonthEventDateResponse> getGetMonthEventDate(@Body GetMonthEventDateRequest getMonthEventDateRequest);

    @POST("search-event.php?api_key=1234&action=get_all_shift_vol")
    Call<GetShiftListResponse> getSearchShiftList(@Body GetSearchShiftListRequest getShiftListRequest);

}