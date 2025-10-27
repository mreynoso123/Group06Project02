package example.client.api

import org.openapitools.client.infrastructure.CollectionFormats.*
import retrofit2.http.*
import retrofit2.Call
import okhttp3.RequestBody
import com.fasterxml.jackson.annotation.JsonProperty

import example.client.model.Family
import example.client.model.User

interface MyServerApi {
    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyName 
     * @param familyPassword 
     * @param newUserUuid 
     * @return [Call]<[Family]>
     */
    @POST("family/add-user")
    fun addFamilyUser(@Query("family_name") familyName: kotlin.String, @Query("family_password") familyPassword: kotlin.String, @Query("new_user_uuid") newUserUuid: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyName 
     * @param familyPassword 
     * @return [Call]<[Family]>
     */
    @POST("family/get-family")
    fun getFamily(@Query("family_name") familyName: kotlin.String, @Query("family_password") familyPassword: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userName 
     * @param userPassword 
     * @return [Call]<[User]>
     */
    @POST("user/get-user")
    fun getUser(@Query("user_name") userName: kotlin.String, @Query("user_password") userPassword: kotlin.String): Call<User>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyName 
     * @param familyPassword 
     * @param familyNamePretty 
     * @return [Call]<[Family]>
     */
    @POST("family/new-family")
    fun newFamily(@Query("family_name") familyName: kotlin.String, @Query("family_password") familyPassword: kotlin.String, @Query("family_name_pretty") familyNamePretty: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userUuid 
     * @param userPassword 
     * @param userNamePretty 
     * @param userType 
     * @param familyName 
     * @param userEmail  (optional)
     * @return [Call]<[User]>
     */
    @POST("user/new-user")
    fun newUser(@Query("user_uuid") userUuid: kotlin.String, @Query("user_password") userPassword: kotlin.String, @Query("user_name_pretty") userNamePretty: kotlin.String, @Query("user_type") userType: kotlin.Int, @Query("family_name") familyName: kotlin.String, @Query("user_email") userEmail: kotlin.String? = null): Call<User>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyName 
     * @param familyPassword 
     * @param removedUserUuid 
     * @return [Call]<[Family]>
     */
    @POST("family/remove-user")
    fun removeFamilyUser(@Query("family_name") familyName: kotlin.String, @Query("family_password") familyPassword: kotlin.String, @Query("removed_user_uuid") removedUserUuid: kotlin.String): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param familyName 
     * @param familyPassword 
     * @param newFamilyPassword  (optional)
     * @param newFamilyNamePretty  (optional)
     * @return [Call]<[Family]>
     */
    @POST("family/update-family")
    fun updateFamily(@Query("family_name") familyName: kotlin.String, @Query("family_password") familyPassword: kotlin.String, @Query("new_family_password") newFamilyPassword: kotlin.String? = null, @Query("new_family_name_pretty") newFamilyNamePretty: kotlin.String? = null): Call<Family>

    /**
     * 
     * 
     * Responses:
     *  - 200: OK
     *
     * @param userName 
     * @param userPassword 
     * @param newUserPassword  (optional)
     * @param newUserNamePretty  (optional)
     * @param newUserType  (optional)
     * @param newFamilyName  (optional)
     * @param newUserEmail  (optional)
     * @return [Call]<[User]>
     */
    @POST("user/update-user")
    fun updateUser(@Query("user_name") userName: kotlin.String, @Query("user_password") userPassword: kotlin.String, @Query("new_user_password") newUserPassword: kotlin.String? = null, @Query("new_user_name_pretty") newUserNamePretty: kotlin.String? = null, @Query("new_user_type") newUserType: kotlin.Int? = null, @Query("new_family_name") newFamilyName: kotlin.String? = null, @Query("new_user_email") newUserEmail: kotlin.String? = null): Call<User>

}
