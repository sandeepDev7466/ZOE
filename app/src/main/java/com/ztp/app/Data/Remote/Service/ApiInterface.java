package com.ztp.app.Data.Remote.Service;

import com.ztp.app.Data.Remote.Model.Request.AddCommentRequest;
import com.ztp.app.Data.Remote.Model.Request.ApprovedCSORequest;
import com.ztp.app.Data.Remote.Model.Request.BlogSearchRequest;
import com.ztp.app.Data.Remote.Model.Request.CSOAllRequest;
import com.ztp.app.Data.Remote.Model.Request.ChangePasswordRequest;
import com.ztp.app.Data.Remote.Model.Request.ChangeStatusByCSORequest;
import com.ztp.app.Data.Remote.Model.Request.ChangeVolunteerStatusRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoDashboardCombinedRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoHangoutVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoMyVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_1;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_2;
import com.ztp.app.Data.Remote.Model.Request.CsoRegisterRequestStep_3;
import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Request.DeleteShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.DocumentDeleteRequest;
import com.ztp.app.Data.Remote.Model.Request.DocumentListRequest;
import com.ztp.app.Data.Remote.Model.Request.DuplicateEventRequest;
import com.ztp.app.Data.Remote.Model.Request.EventAddRequest;
import com.ztp.app.Data.Remote.Model.Request.EventRatingRequest;
import com.ztp.app.Data.Remote.Model.Request.EventUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.ForgotPasswordRequest;
import com.ztp.app.Data.Remote.Model.Request.GetAllNotificationRequest;
import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Request.GetEventsRequest;
import com.ztp.app.Data.Remote.Model.Request.GetProfileRequest;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.GetShiftDetailRequest;
import com.ztp.app.Data.Remote.Model.Request.GetStudentGradRequest;
import com.ztp.app.Data.Remote.Model.Request.GetUserStatusRequest;
import com.ztp.app.Data.Remote.Model.Request.GetUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Request.GradeRequest;
import com.ztp.app.Data.Remote.Model.Request.InsertFirebaseIdRequest;
import com.ztp.app.Data.Remote.Model.Request.LoginRequest;
import com.ztp.app.Data.Remote.Model.Request.MarkAttendanceRequest;
import com.ztp.app.Data.Remote.Model.Request.MarkRankRequest;
import com.ztp.app.Data.Remote.Model.Request.MyCSORequest_V;
import com.ztp.app.Data.Remote.Model.Request.MyFriendsRequest;
import com.ztp.app.Data.Remote.Model.Request.PostVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Request.PotentialFriendsRequest;
import com.ztp.app.Data.Remote.Model.Request.PublishRequest;
import com.ztp.app.Data.Remote.Model.Request.ReadNotificationRequest;
import com.ztp.app.Data.Remote.Model.Request.SearchCSORequest;
import com.ztp.app.Data.Remote.Model.Request.SearchEventRequest;
import com.ztp.app.Data.Remote.Model.Request.SearchEventbyAreaRequest;
import com.ztp.app.Data.Remote.Model.Request.ShiftTaskRequest;
import com.ztp.app.Data.Remote.Model.Request.ShiftUpdateRequest;
import com.ztp.app.Data.Remote.Model.Request.SiftAddRequest;
import com.ztp.app.Data.Remote.Model.Request.StateRequest;
import com.ztp.app.Data.Remote.Model.Request.StudentPendingRequest;
import com.ztp.app.Data.Remote.Model.Request.StudentRegisterRequest;
import com.ztp.app.Data.Remote.Model.Request.StudentSendRequest;
import com.ztp.app.Data.Remote.Model.Request.UnlinkUserRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateProfileRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateUserTimezoneRequest;
import com.ztp.app.Data.Remote.Model.Request.UpdateVolunteerHoursRequest;
import com.ztp.app.Data.Remote.Model.Request.ValidateOtpRequest;
import com.ztp.app.Data.Remote.Model.Request.ValidateParentEmailRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerConnectRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerDashboardCombineRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerMarkHoursRequest;
import com.ztp.app.Data.Remote.Model.Request.VolunteerTargetRequest;
import com.ztp.app.Data.Remote.Model.Response.AddCommentResponse;
import com.ztp.app.Data.Remote.Model.Response.AddEventResponse;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.Data.Remote.Model.Response.BlogSearchResponse;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.CSOQuestionResponse;
import com.ztp.app.Data.Remote.Model.Response.ChangePasswordResponse;
import com.ztp.app.Data.Remote.Model.Response.ChangeStatusByCSOResponse;
import com.ztp.app.Data.Remote.Model.Response.ChangeVolunteerStatusResponse;
import com.ztp.app.Data.Remote.Model.Response.CountryResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoDashboardCombinedResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoHangoutVolunteerResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoMarkHoursResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_1;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_2;
import com.ztp.app.Data.Remote.Model.Response.CsoRegisterResponseStep_3;
import com.ztp.app.Data.Remote.Model.Response.DeleteChannelResponse;
import com.ztp.app.Data.Remote.Model.Response.DeleteEventResponse;
import com.ztp.app.Data.Remote.Model.Response.DeleteShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentDeleteResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentListResponse;
import com.ztp.app.Data.Remote.Model.Response.DocumentTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.DuplicateEventResponse;
import com.ztp.app.Data.Remote.Model.Response.EthnicityResponse;
import com.ztp.app.Data.Remote.Model.Response.EventRatingResponse;
import com.ztp.app.Data.Remote.Model.Response.EventTypeResponse;
import com.ztp.app.Data.Remote.Model.Response.ForgotPasswordResponse;
import com.ztp.app.Data.Remote.Model.Response.GetAllNotificationResponse;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Model.Response.GetProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Model.Response.GetStudentGradResponse;
import com.ztp.app.Data.Remote.Model.Response.GetUserStatusResponse;
import com.ztp.app.Data.Remote.Model.Response.GetUserTimezoneResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Data.Remote.Model.Response.GradeResponse;
import com.ztp.app.Data.Remote.Model.Response.InsertFirebaseIdResponse;
import com.ztp.app.Data.Remote.Model.Response.LoginResponse;
import com.ztp.app.Data.Remote.Model.Response.MarkAttendanceResponse;
import com.ztp.app.Data.Remote.Model.Response.MarkRankResponse;
import com.ztp.app.Data.Remote.Model.Response.MyCSOResponse_V;
import com.ztp.app.Data.Remote.Model.Response.MyFriendsResponse;
import com.ztp.app.Data.Remote.Model.Response.PostVolunteerRequestResponse;
import com.ztp.app.Data.Remote.Model.Response.PotentialFriendsResponse;
import com.ztp.app.Data.Remote.Model.Response.PublishResponse;
import com.ztp.app.Data.Remote.Model.Response.ReadNotificationResponse;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchCSOResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchEventbyAreaResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftAddResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftTaskResponse;
import com.ztp.app.Data.Remote.Model.Response.ShiftUpdateResponse;
import com.ztp.app.Data.Remote.Model.Response.StateResponse;
import com.ztp.app.Data.Remote.Model.Response.StudentPendingResponse;
import com.ztp.app.Data.Remote.Model.Response.StudentRegisterResponse;
import com.ztp.app.Data.Remote.Model.Response.StudentSendResponse;
import com.ztp.app.Data.Remote.Model.Response.TimeZoneResponse;
import com.ztp.app.Data.Remote.Model.Response.UnlinkUserResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateEventResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateProfileResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateUserTimezoneResponse;
import com.ztp.app.Data.Remote.Model.Response.UpdateVolunteerHoursResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadCoverPicResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadDocumentResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadLockerDocumentResponse;
import com.ztp.app.Data.Remote.Model.Response.UploadProfilePicResponse;
import com.ztp.app.Data.Remote.Model.Response.ValidateOtpResponse;
import com.ztp.app.Data.Remote.Model.Response.ValidateParentEmailResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerConnectResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerDashboardCombineResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerMarkHoursResponse;
import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

    @POST("user-access.php?api_key=1234&action=getprofilemob")
    Call<GetProfileResponse> doGetProfile(@Body GetProfileRequest getProfileRequest);

    @POST("user-access.php?api_key=1234&action=update_account")
    Call<UpdateProfileResponse> doUpdateProfile(@Body UpdateProfileRequest updateProfileRequest);

    /*@POST("cso-action.php?api_key=1234&action=get_all_shift")
    Call<GetCSOShiftResponse> getEventShift(@Body GetCSOShiftRequest getEventShiftRequest);*/

    //@POST("cso-action.php?api_key=1234&action=get_event_detail")
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
    @POST("fileupload.php")
//http://hashtaglabs.in/staging/supercomp/filedata.php
    Call<ResponseBody> uploadDocument(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("user_file_title") String use_file_title, @Query("fileExtension") String extension);

    @Multipart
    @POST("file-upload.php")
//http://hashtaglabs.in/staging/supercomp/filedata.php
    Call<UploadDocumentResponse> uploadDocumentNew(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("user_file_title") String use_file_title, @Query("api_key") String apiKey, @Query("action") String action);

    @POST("cso-action.php?api_key=1234&action=get_shift_detail")
    Call<GetShiftDetailResponse> getShiftDetail(@Body GetShiftDetailRequest getShiftDetailRequest);

    @POST("cso-action.php?api_key=1234&action=get_all_event")
    Call<GetEventsResponse> getEventList(@Body GetEventsRequest getEventsRequest);

  /*  @POST("cso-action.php?api_key=1234&action=get_all_shift")
    Call<GetVolunteerShiftListResponse> getShiftList(@Body GetShiftListRequest getShiftListRequest);*/

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

   /* @POST("cso-action.php?api_key=1234&action=get_month_event")
    Call<GetMonthEventDateResponse> getGetMonthEventDate(@Body GetMonthEventDateRequest getMonthEventDateRequest);*/

    @POST("search-event.php?api_key=1234&action=get_all_shift_vol")
    Call<GetVolunteerShiftListResponse> getSearchShiftList(@Body GetSearchShiftListRequest getShiftListRequest);

    @POST("vol-action.php?api_key=1234&action=vol_request_status")
    Call<ChangeVolunteerStatusResponse> changeVolunteerStatus(@Body ChangeVolunteerStatusRequest changeVolunteerStatusRequest);

    @POST("cso-action.php?api_key=1234&action=col_request_status")
    Call<ChangeStatusByCSOResponse> changeStatusByCSO(@Body ChangeStatusByCSORequest changeStatusByCSORequest);

    @POST("cso-action.php?api_key=1234&action=p_event")
    Call<PublishResponse> publish(@Body PublishRequest publishRequest);

    @POST("cso-action.php?api_key=1234&action=get_all_shift")
    Call<GetCSOShiftResponse> getCSOShift(@Body GetCSOShiftRequest getEventShiftRequest);

    @Multipart
    @POST("file-upload.php")
    Call<UploadDocumentResponse> uploadEventImage(@Part MultipartBody.Part filePart, @Query("event_id") String event_id, @Query("user_id") String user_id, @Query("api_key") String apiKey, @Query("action") String action);

    //@POST("cso-action.php?api_key=1234&action=cso_dashboard_combine")
    @POST("cso-action.php?api_key=1234&action=cso_dashboard_combine_mob")
    Call<CsoDashboardCombinedResponse> getCsoDashoardCombined(@Body CsoDashboardCombinedRequest csoDashboardCombinedRequest);

    @POST("cso-action.php?api_key=1234&action=mark_rank")
    Call<MarkRankResponse> markRank(@Body MarkRankRequest markRankRequest);

    @POST("cso-action.php?api_key=1234&action=my_volunteers")
    Call<CsoMyFollowerResponse> getCsoMyVolunteer(@Body CsoMyVolunteerRequest csoMyVolunteerRequest);

    @POST("cso-action.php?api_key=1234&action=mark_hours")
    Call<CsoMarkHoursResponse> csoMarkHours(@Body CsoMarkHoursRequest csoMarkHoursRequest);

    @POST("vol-action.php?api_key=1234&action=vol_dashboard_combine_mob")
    Call<VolunteerDashboardCombineResponse> getVolunteerDashboardCombined(@Body VolunteerDashboardCombineRequest volunteerDashboardCombineRequest);

    @POST("vol-action.php?api_key=1234&action=update_vol_hours")
    Call<UpdateVolunteerHoursResponse> updateVolunteerHours(@Body UpdateVolunteerHoursRequest updateVolunteerHoursRequest);

    @POST("cso-action.php?api_key=1234&action=mark_hours_completed")
    Call<VolunteerMarkHoursResponse> volunteerMarkHoursCompleted(@Body VolunteerMarkHoursRequest volunteerMarkHoursRequest);

    @POST("cso-action.php?api_key=1234&action=get_all_request")
    Call<CsoHangoutVolunteerResponse> getHangoutVolunteers(@Body CsoHangoutVolunteerRequest csoHangoutVolunteerRequest);

    @POST("vol-action.php?api_key=1234&action=my_cso")
    Call<MyCSOResponse_V> getMyCSO_V(@Body MyCSORequest_V myCSORequest_v);

    @Multipart
    @POST("file-upload.php")
    Call<UploadCoverPicResponse> uploadCoverImage(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("api_key") String apiKey, @Query("action") String action);

    @Multipart
    @POST("file-upload.php")
    Call<UploadProfilePicResponse> uploadProfileImage(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("api_key") String apiKey, @Query("action") String action);

    @POST("vol-action.php?api_key=1234&action=get_vol_targets")
    Call<VolunteerTargetResponse> getVol_Targets(@Body VolunteerTargetRequest volunteerTargetRequest);

    @POST("cso-action.php?api_key=1234&action=shift_task_list")
    Call<ShiftTaskResponse> getShiftTaskList(@Body ShiftTaskRequest shiftTaskRequest);


    @POST("locker-documents.php?api_key=1234&action=select_locker_doc")
    Call<DocumentListResponse> getDocumentList(@Body DocumentListRequest documentListRequest);

    @Multipart
    @POST("file-upload.php")
    Call<UploadLockerDocumentResponse> uploadLockerDocument(@Part MultipartBody.Part filePart, @Query("user_id") String user_id, @Query("api_key") String apiKey, @Query("action") String action, @Query("document_type") String documentType, @Query("document_name") String documentName, @Query("user_device") String userDevice, @Query("user_type") String userType);

    @GET("master-data.php?api_key=1234&action=sdoctype")
    Call<DocumentTypeResponse> getDocumentType();

    @POST("locker-documents.php?api_key=1234&action=d_locker_doc")
    Call<DocumentDeleteResponse> deleteDocument(@Body DocumentDeleteRequest documentDeleteRequest);

    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @POST("site-data.php?api_key=1234&action=event_rating")
    Call<EventRatingResponse> rateEvent(@Body EventRatingRequest eventRatingRequest);

    @POST("log-data.php?api_key=1234&action=i_vol_log")
    Call<MarkAttendanceResponse> markAttendance(@Body MarkAttendanceRequest markAttendanceRequest);

    @POST("user-access.php?api_key=1234&action=forgot_pass_mob")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("user-access.php?api_key=1234&action=change_pass_mob")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @POST("search-event.php?api_key=1234&action=search_event_filter")
    Call<SearchEventbyAreaResponse> searchByArea(@Body SearchEventbyAreaRequest searchEventbyAreaRequest);

    @POST("cso-action.php?api_key=1234&action=dup_event")
    Call<DuplicateEventResponse> duplicateEvent(@Body DuplicateEventRequest duplicateEventRequest);

    @POST("site-data.php?api_key=1234&action=insert_firebase_id")
    Call<InsertFirebaseIdResponse> insertFirebaseId(@Body InsertFirebaseIdRequest insertFirebaseIdRequest);

    @POST("vol-action.php?api_key=1234&action=get_all_noti")
    Call<GetAllNotificationResponse> getAllNotification(@Body GetAllNotificationRequest getAllNotificationRequest);

    @POST("cso-action.php?api_key=1234&action=get_user_timezone")
    Call<GetUserTimezoneResponse> getUserTimezone(@Body GetUserTimezoneRequest getUserTimezoneRequest);

    @POST("cso-action.php?api_key=1234&action=u_timezone")
    Call<UpdateUserTimezoneResponse> updateUserTimezone(@Body UpdateUserTimezoneRequest updateUserTimezoneRequest);

    @POST("cso-action.php?api_key=1234&action=get_user_status")
    Call<GetUserStatusResponse> getUserStatus(@Body GetUserStatusRequest getUserStatusRequest);

    @POST("cso-action.php?api_key=1234&action=col_request_status")
    Call<AddCommentResponse> addComment(@Body AddCommentRequest addCommentRequest);

    @Headers({"Content-Type:application/json","Api-Token:39034b2fbfb3e6d5bc081284907f3899795593af"})
    @DELETE("https://api-83f1da53-7b63-4707-8b43-cb42349fdd4f.sendbird.com/v3/group_channels/{channel_id}")
    Call<DeleteChannelResponse> deleteChannel(@Path("channel_id") String channel_id);


    // 30-10-2019

    @POST("master-data.php?api_key=1234&action=sethnicity")
    Call<EthnicityResponse> getEthnicity();

    @POST("hsa-action.php?api_key=1234&action=get_setting_details")
    Call<GradeResponse> getGrade(@Body GradeRequest gradeRequest);

    @POST("stu-action.php?api_key=1234&action=get_stu_grad")
    Call<GetStudentGradResponse> getStudentGrade(@Body GetStudentGradRequest getStudentGradRequest);

    @POST("user-access.php?api_key=1234&action=validate_email")
    Call<ValidateParentEmailResponse> validateParentEmail(@Body ValidateParentEmailRequest validateParentEmailRequest);

    @POST("stu-action.php?api_key=1234&action=approved_cso")
    Call<ApprovedCSOResponse> approvedCSOList(@Body ApprovedCSORequest approvedCSORequest);

    @POST("stu-action.php?api_key=1234&action=potential_friends")
    Call<PotentialFriendsResponse> getPotentialFriends(@Body PotentialFriendsRequest getPotentialFriendsRequest);

    @POST("vol-action.php?api_key=1234&action=vol_connect")
    Call<VolunteerConnectResponse> volunteerConnect(@Body VolunteerConnectRequest volunteerConnectRequest);

    @POST("stu-action.php?api_key=1234&action=my_friends")
    Call<MyFriendsResponse> getMyFriendsResponse(@Body MyFriendsRequest myFriendsRequest);

    @POST("stu-action.php?api_key=1234&action=get_stu_send_req")
    Call<StudentSendResponse> getStudentSendRequest(@Body StudentSendRequest studentSendRequest);

    @POST("stu-action.php?api_key=1234&action=get_stu_pending_req")
    Call<StudentPendingResponse> getStudentPendingRequest(@Body StudentPendingRequest studentPendingRequest);

    @GET("user-access.php?api_key=1234&action=cso_question_data")
    Call<CSOQuestionResponse> getCSOQuestionResponse();

    @POST("stu-action.php?api_key=1234&action=search_cso")
    Call<SearchCSOResponse> getSearchCSO(@Body SearchCSORequest searchCSORequest);

    @POST("user-access.php?api_key=1234&action=unlink_user")
    Call<UnlinkUserResponse> unlinkUser(@Body UnlinkUserRequest unlinkUserRequest);

    @POST("site-data.php?api_key=1234&action=read_notifications")
    Call<ReadNotificationResponse> readNotification(@Body ReadNotificationRequest readNotificationRequest);

}