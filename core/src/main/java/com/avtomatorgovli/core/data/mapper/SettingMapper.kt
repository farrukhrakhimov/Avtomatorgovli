package com.avtomatorgovli.core.data.mapper

import com.avtomatorgovli.core.data.local.entity.AppSettingEntity
import com.avtomatorgovli.core.domain.model.AppSetting

fun AppSettingEntity.toDomain() = AppSetting(key, value)
fun AppSetting.toEntity() = AppSettingEntity(key, value)
