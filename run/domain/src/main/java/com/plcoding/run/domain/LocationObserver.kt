package com.plcoding.run.domain

import com.plcoding.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {

    fun observerLocation(interval: Long): Flow<LocationWithAltitude>
}