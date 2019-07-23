package com.grocers.hub.network;

import com.grocers.hub.models.GeneralRequest;
import com.grocers.hub.models.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*Interface for all api's*/
public interface APIInterface {

    @Headers({"Accept: application/json"})
    @POST("rest/V1/customers")
    Call<GeneralResponse> userLogin(@Body GeneralRequest generalRequest);

    @Headers({"Accept: application/json"})
    @POST("rest/V1/customers")
    Call<GeneralResponse> checkBankDetails(@Header("Authorization") String auth, @Query("Aadhaar") String Aadhaar, @Query("BankAccountNo") String BankAccountNo, @Query("IFSCCode") String IFSCCode, @Query("BorrowerID") String BorrowerID);

   /* @Headers({"Accept: application/json"})
    @POST("client/centre-meeting")
    Call<GeneralResponse> centerMeetingCreate(@Header("Authorization") String auth, @Body CenterMeetingRequest centerMeetingRequest);

    @Headers({"Accept: application/json"})
    @GET("auth/token/refresh")
    Call<GeneralResponse> getRefreshToken(@Query("RefreshToken") String refreshToken);

    @Headers({"Accept: application/json"})
    @GET("/client/appversions")
    Call<VersionsResponse> getVersionDetails();


    @Headers({"Accept: application/json"})
    @POST("/client/masters/all-masters")
    Call<MastersResponse> getMasters(@Header("Authorization") String auth, @Body MastersRequest mastersRequest);

    @Headers({"Accept: application/json"})
    @POST("/client/masters/branch-master")
    Call<BranchMasterResponse> getBranchMasters(@Header("Authorization") String auth, @Body MastersRequest mastersRequest);

    *//*@Headers({"Accept: application/json"})
    @GET("/client/masters/bank-master")
    Call<BanksResponse> getBanks(@Header("Authorization") String auth);*//*

    @Headers({"Accept: application/json"})
    @POST("/client/masters/new-banks")
    Call<BanksResponse> getNewBanks(@Header("Authorization") String auth,@Body NewBanksRequestModel newBanksRequestModel);

    @Headers({"Accept: application/json"})
    @GET("/client/masters/status-master")
    Call<StatusResponse> getStatus(@Header("Authorization") String auth);

    @Headers({"Accept: application/json"})
    @GET("/client/clients/aadhaar?")
    Call<AadhaarResponse> getAadhaarData(@Header("Authorization") String auth, @Query("Aadhaar") String aadhaar, @Query("BranchID") String branchID, @Query("ProductType") String productType);

    @Headers({"Accept: application/json"})
    @GET("/client/clients/mmid/{id}")
    Call<AadhaarResponse> getMMIDData(@Header("Authorization") String auth, @Path("id") String Id);

    @Headers({"Accept: application/json"})
    @POST("client/clients")
    Call<GeneralResponse> createClientRequest(@Body ClientEnrollmentRequest clientEnrollmentRequest);

    @Multipart
    @Headers({"Accept: application/json"})
    @POST("resources/uploads/file?")
    Call<UploadFileResponse> uploadFile(@Header("Authorization") String auth, @Part MultipartBody.Part file, @Query("category") String category);

    @Headers({"Accept: application/json"})
    @POST("client/qr-verification/")
    Call<GeneralResponse> qrVerification(@Header("Authorization") String auth, @Body QRVerificationModel qrVerificationModel);

    @Headers({"Accept: application/json"})
    @POST("client/clients/")
    Call<GeneralResponse> createClient(@Header("Authorization") String auth, @Body ClientEnrollmentRequest clientEnrollmentRequest);

    @Headers({"Accept: application/json"})
    @GET("/client/clients/application-status?")
    Call<ApplicationStatusViewModel> getApplicationStatus(@Header("Authorization") String auth, @Query("BranchID") String branchID,
                                                          @Query("MMI_NAME") String name,
                                                          @Query("PageSize") int pg, @Query("PageNumber") int pgnumber);


    @Headers({"Accept: application/json"})
    @GET("/client/clients/application-status?")
    Call<ApplicationStatusViewModel> getApplicationStatus(@Header("Authorization") String auth, @Query("BranchID") String branchID, @Query("PageSize") int pg, @Query("PageNumber") int pgnumber);

    @Headers({"Accept: application/json"})
    @GET("/client/clients/application-status?")
    Call<ApplicationStatusViewModel> getApplicationStatusFilter(@Header("Authorization") String auth,
                                                                @Query("BranchID") String branchID,
                                                                @Query("Aadhar") String aadhaar, @Query("FormNo") String formNo,
                                                                @Query("MVI_ID") String mvi_Id, @Query("MGI_Id") String mgi_Id,
                                                                @Query("CenterID") String mci_Id, @Query("MMI_NAME") String mmi_name,
                                                                @Query("Status") String status, @Query("CreatedDateStart") String createDate,
                                                                @Query("CreatedDateEnd") String endDate, @Query("PageSize") int pg, @Query("PageNumber") int pgnumber);

    @Headers({"Accept: application/json"})
    @GET("/client/clients/cbcheck")
    Call<CbSummaryResponse> getCbSummary(@Header("Authorization") String auth, @Query("FormNo") String formNo);

    @Headers({"Accept: application/json"})
    @GET("/client/centre-meeting/search")
    Call<CentreMeetingListModel> getCenterMeetingList(@Header("Authorization") String auth, @Query("CreatedDateStart") String createdDateStart,
                                                      @Query("CreatedDateEnd") String createdDateEnd);

    @Headers({"Accept: application/json"})
    @GET("/client/centre-meeting/{id}")
    Call<CenterMeetingDetailsModel> getCenterMeetingByID(@Header("Authorization") String auth, @Path("id") int Id);


    @Headers({"Accept: application/json"})
    @GET("/collection/collections/center-meeting-schedule")
    Call<CollectionListResponse> getCollectionList(@Header("Authorization") String auth, @Query("BranchID") String BranchID,
                                                   @Query("TransactionDate") String TransactionDate);

    @Headers({"Accept: application/json"})
    @POST("/collection/collections")
    Call<GeneralResponse> collectionSync(@Header("Authorization") String auth, @Body CollectionRequest collectionRequest);
*/
}