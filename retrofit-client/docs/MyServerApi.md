# MyServerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addFamilyUser**](MyServerApi.md#addFamilyUser) | **POST** family/add-user | 
[**getFamily**](MyServerApi.md#getFamily) | **POST** family/get-family | 
[**getUser**](MyServerApi.md#getUser) | **POST** user/get-user | 
[**newFamily**](MyServerApi.md#newFamily) | **POST** family/new-family | 
[**newUser**](MyServerApi.md#newUser) | **POST** user/new-user | 
[**removeFamilyUser**](MyServerApi.md#removeFamilyUser) | **POST** family/remove-user | 
[**updateFamily**](MyServerApi.md#updateFamily) | **POST** family/update-family | 
[**updateUser**](MyServerApi.md#updateUser) | **POST** user/update-user | 





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val familyName : kotlin.String = familyName_example // kotlin.String | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val newUserUuid : kotlin.String = newUserUuid_example // kotlin.String | 

val result : Family = webService.addFamilyUser(familyName, familyPassword, newUserUuid)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyName** | **kotlin.String**|  |
 **familyPassword** | **kotlin.String**|  |
 **newUserUuid** | **kotlin.String**|  |

### Return type

[**Family**](Family.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val familyName : kotlin.String = familyName_example // kotlin.String | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 

val result : Family = webService.getFamily(familyName, familyPassword)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyName** | **kotlin.String**|  |
 **familyPassword** | **kotlin.String**|  |

### Return type

[**Family**](Family.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val userName : kotlin.String = userName_example // kotlin.String | 
val userPassword : kotlin.String = userPassword_example // kotlin.String | 

val result : User = webService.getUser(userName, userPassword)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userName** | **kotlin.String**|  |
 **userPassword** | **kotlin.String**|  |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val familyName : kotlin.String = familyName_example // kotlin.String | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val familyNamePretty : kotlin.String = familyNamePretty_example // kotlin.String | 

val result : Family = webService.newFamily(familyName, familyPassword, familyNamePretty)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyName** | **kotlin.String**|  |
 **familyPassword** | **kotlin.String**|  |
 **familyNamePretty** | **kotlin.String**|  |

### Return type

[**Family**](Family.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val userUuid : kotlin.String = userUuid_example // kotlin.String | 
val userPassword : kotlin.String = userPassword_example // kotlin.String | 
val userNamePretty : kotlin.String = userNamePretty_example // kotlin.String | 
val userType : kotlin.Int = 56 // kotlin.Int | 
val familyName : kotlin.String = familyName_example // kotlin.String | 
val userEmail : kotlin.String = userEmail_example // kotlin.String | 

val result : User = webService.newUser(userUuid, userPassword, userNamePretty, userType, familyName, userEmail)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userUuid** | **kotlin.String**|  |
 **userPassword** | **kotlin.String**|  |
 **userNamePretty** | **kotlin.String**|  |
 **userType** | **kotlin.Int**|  |
 **familyName** | **kotlin.String**|  |
 **userEmail** | **kotlin.String**|  | [optional]

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val familyName : kotlin.String = familyName_example // kotlin.String | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val removedUserUuid : kotlin.String = removedUserUuid_example // kotlin.String | 

val result : Family = webService.removeFamilyUser(familyName, familyPassword, removedUserUuid)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyName** | **kotlin.String**|  |
 **familyPassword** | **kotlin.String**|  |
 **removedUserUuid** | **kotlin.String**|  |

### Return type

[**Family**](Family.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val familyName : kotlin.String = familyName_example // kotlin.String | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val newFamilyPassword : kotlin.String = newFamilyPassword_example // kotlin.String | 
val newFamilyNamePretty : kotlin.String = newFamilyNamePretty_example // kotlin.String | 

val result : Family = webService.updateFamily(familyName, familyPassword, newFamilyPassword, newFamilyNamePretty)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyName** | **kotlin.String**|  |
 **familyPassword** | **kotlin.String**|  |
 **newFamilyPassword** | **kotlin.String**|  | [optional]
 **newFamilyNamePretty** | **kotlin.String**|  | [optional]

### Return type

[**Family**](Family.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*




### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val userName : kotlin.String = userName_example // kotlin.String | 
val userPassword : kotlin.String = userPassword_example // kotlin.String | 
val newUserPassword : kotlin.String = newUserPassword_example // kotlin.String | 
val newUserNamePretty : kotlin.String = newUserNamePretty_example // kotlin.String | 
val newUserType : kotlin.Int = 56 // kotlin.Int | 
val newFamilyName : kotlin.String = newFamilyName_example // kotlin.String | 
val newUserEmail : kotlin.String = newUserEmail_example // kotlin.String | 

val result : User = webService.updateUser(userName, userPassword, newUserPassword, newUserNamePretty, newUserType, newFamilyName, newUserEmail)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userName** | **kotlin.String**|  |
 **userPassword** | **kotlin.String**|  |
 **newUserPassword** | **kotlin.String**|  | [optional]
 **newUserNamePretty** | **kotlin.String**|  | [optional]
 **newUserType** | **kotlin.Int**|  | [optional]
 **newFamilyName** | **kotlin.String**|  | [optional]
 **newUserEmail** | **kotlin.String**|  | [optional]

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

