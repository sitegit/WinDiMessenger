package com.example.windimessenger.data.mapper

import com.example.windimessenger.data.model.profile.Avatar
import com.example.windimessenger.data.model.profile.UserResponseDto
import com.example.windimessenger.data.model.profile.UserUpdateDto
import com.example.windimessenger.domain.entity.profile.UserRequest
import com.example.windimessenger.domain.entity.profile.UserInfo

fun UserResponseDto.toEntity() = UserInfo(
    avatarUrl = profileDataDto.avatarsDto.avatar ?: "",
    birthday = profileDataDto.birthday ?: "",
    city = profileDataDto.city ?: "",
    phone = profileDataDto.phone ?: "",
    status = profileDataDto.status ?: "",
    username = profileDataDto.username ?: ""
)

fun UserRequest.toDto() = UserUpdateDto(
    avatar = Avatar(avatar = avatarUrl, filename = fileName),
    birthday = birthday,
    city = city,
    status = status,
    username = username,
    name = name
)

fun UserRequest.toUserInfo() = UserInfo(
    avatarUrl = avatarUrl,
    birthday = birthday,
    city = city,
    phone = phone,
    status = status,
    username = username,
    avatarUri = avatarUri
)
