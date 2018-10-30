package com.example.potikorn.kotlincoroutine.model

import com.squareup.moshi.Json

data class RandomUserModel(
    @field:Json(name = "results") val results: MutableList<RandomUserResult>?,
    @field:Json(name = "info") val info: Info?
)

data class RandomUserResult(
    @field:Json(name = "gender") val gender: String?,
    @field:Json(name = "name") val name: Name?,
    @field:Json(name = "location") val location: Location?,
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "login") val login: Login?,
    @field:Json(name = "dob") val dob: DateAndAge?,
    @field:Json(name = "registered") val registered: DateAndAge?,
    @field:Json(name = "phone") val phone: String?,
    @field:Json(name = "cell") val cell: String?,
    @field:Json(name = "id") val id: ID?,
    @field:Json(name = "picture") val picture: Picture?
)

data class Name(
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "first") val first: String?,
    @field:Json(name = "last") val last: String?
)

data class Location(
    @field:Json(name = "street") val street: String?,
    @field:Json(name = "city") val city: String?,
    @field:Json(name = "state") val state: String?,
    @field:Json(name = "postcode") val postCode: String?,
    @field:Json(name = "coordinates") val coordinates: Coordinates?,
    @field:Json(name = "timezone") val timezone: TimeZone?
)

data class Login(
    @field:Json(name = "uuid") val uuid: String?,
    @field:Json(name = "username") val username: String?,
    @field:Json(name = "password") val password: String?,
    @field:Json(name = "salt") val salt: String?,
    @field:Json(name = "md5") val md5: String?,
    @field:Json(name = "sha1") val sha1: String?,
    @field:Json(name = "sha256") val sha256: String?
)

data class DateAndAge(
    @field:Json(name = "date") val date: String?,
    @field:Json(name = "age") val age: Int?
)

data class ID(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "value") val value: String?
)

data class Picture(
    @field:Json(name = "large") val large: String?,
    @field:Json(name = "medium") val medium: String?,
    @field:Json(name = "thumbnail") val thumbnail: String?
)

data class Coordinates(
    @field:Json(name = "latitude") val latitude: String,
    @field:Json(name = "longitude") val longitude: String
)

data class TimeZone(
    @field:Json(name = "offset") val offset: String?,
    @field:Json(name = "description") val description: String?
)

data class Info(
    @field:Json(name = "seed") val seed: String,
    @field:Json(name = "results") val results: Int,
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "version") val version: String
)