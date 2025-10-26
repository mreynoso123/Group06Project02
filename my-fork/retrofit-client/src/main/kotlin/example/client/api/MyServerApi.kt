package example.client.api

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.fasterxml.jackson.annotation.JsonProperty

import example.client.model.Family

interface MyServerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyId 
     * @param familyPassword 
     * @param newFamilyPassword  (optional)
     * @param newFamilyName  (optional)
     * @return [Call]<[Family]>
     */
    @POST("family/change-family")
    fun familyChange(@Query("family_id") familyId: kotlin.Int, @Query("family_password") familyPassword: kotlin.String, @Query("new_family_password") newFamilyPassword: kotlin.String? = null, @Query("new_family_name") newFamilyName: kotlin.String? = null): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyId 
     * @param familyPassword 
     * @return [Call]<[Family]>
     */
    @POST("family/get-family")
    fun familyGet(@Query("family_id") familyId: kotlin.Int, @Query("family_password") familyPassword: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyId 
     * @param familyPassword 
     * @param familyName 
     * @return [Call]<[Family]>
     */
    @POST("family/new-family")
    fun familyNew(@Query("family_id") familyId: kotlin.Int, @Query("family_password") familyPassword: kotlin.String, @Query("family_name") familyName: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyId 
     * @param familyPassword 
     * @param newFamilyPassword  (optional)
     * @param newFamilyName  (optional)
     * @return [Call]<[Family]>
     */
    @POST("user/change-user")
    fun userChange(@Query("family_id") familyId: kotlin.Int, @Query("family_password") familyPassword: kotlin.String, @Query("new_family_password") newFamilyPassword: kotlin.String? = null, @Query("new_family_name") newFamilyName: kotlin.String? = null): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyId 
     * @param familyPassword 
     * @return [Call]<[Family]>
     */
    @POST("user/get-user")
    fun userGet(@Query("family_id") familyId: kotlin.Int, @Query("family_password") familyPassword: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyId 
     * @param familyPassword 
     * @param familyName 
     * @return [Call]<[Family]>
     */
    @POST("user/new-user")
    fun userNew(@Query("family_id") familyId: kotlin.Int, @Query("family_password") familyPassword: kotlin.String, @Query("family_name") familyName: kotlin.String): Call<Family>

}
