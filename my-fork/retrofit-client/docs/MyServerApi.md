# MyServerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**familyChange**](MyServerApi.md#familyChange) | **POST** family/change-family | 
[**familyGet**](MyServerApi.md#familyGet) | **POST** family/get-family | 
[**familyNew**](MyServerApi.md#familyNew) | **POST** family/new-family | 
[**userChange**](MyServerApi.md#userChange) | **POST** user/change-user | 
[**userGet**](MyServerApi.md#userGet) | **POST** user/get-user | 
[**userNew**](MyServerApi.md#userNew) | **POST** user/new-user | 





### Example
```kotlin
// Import classes:
//import org.openapitools.client.*
//import org.openapitools.client.infrastructure.*
//import example.client.model.*

val apiClient = ApiClient()
val webService = apiClient.createWebservice(MyServerApi::class.java)
val familyId : kotlin.Int = 56 // kotlin.Int | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val newFamilyPassword : kotlin.String = newFamilyPassword_example // kotlin.String | 
val newFamilyName : kotlin.String = newFamilyName_example // kotlin.String | 

val result : Family = webService.familyChange(familyId, familyPassword, newFamilyPassword, newFamilyName)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyId** | **kotlin.Int**|  |
 **familyPassword** | **kotlin.String**|  |
 **newFamilyPassword** | **kotlin.String**|  | [optional]
 **newFamilyName** | **kotlin.String**|  | [optional]

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
val familyId : kotlin.Int = 56 // kotlin.Int | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 

val result : Family = webService.familyGet(familyId, familyPassword)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyId** | **kotlin.Int**|  |
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
val familyId : kotlin.Int = 56 // kotlin.Int | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val familyName : kotlin.String = familyName_example // kotlin.String | 

val result : Family = webService.familyNew(familyId, familyPassword, familyName)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyId** | **kotlin.Int**|  |
 **familyPassword** | **kotlin.String**|  |
 **familyName** | **kotlin.String**|  |

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
val familyId : kotlin.Int = 56 // kotlin.Int | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val newFamilyPassword : kotlin.String = newFamilyPassword_example // kotlin.String | 
val newFamilyName : kotlin.String = newFamilyName_example // kotlin.String | 

val result : Family = webService.userChange(familyId, familyPassword, newFamilyPassword, newFamilyName)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyId** | **kotlin.Int**|  |
 **familyPassword** | **kotlin.String**|  |
 **newFamilyPassword** | **kotlin.String**|  | [optional]
 **newFamilyName** | **kotlin.String**|  | [optional]

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
val familyId : kotlin.Int = 56 // kotlin.Int | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 

val result : Family = webService.userGet(familyId, familyPassword)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyId** | **kotlin.Int**|  |
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
val familyId : kotlin.Int = 56 // kotlin.Int | 
val familyPassword : kotlin.String = familyPassword_example // kotlin.String | 
val familyName : kotlin.String = familyName_example // kotlin.String | 

val result : Family = webService.userNew(familyId, familyPassword, familyName)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **familyId** | **kotlin.Int**|  |
 **familyPassword** | **kotlin.String**|  |
 **familyName** | **kotlin.String**|  |

### Return type

[**Family**](Family.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

