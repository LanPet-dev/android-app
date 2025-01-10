package com.lanpet.core.manager

import android.content.Context
import coil.ImageLoader
import coil.memory.MemoryCache

class CoilManager(
    private val context: Context,
) {
    val defaultImageLoader: ImageLoader by lazy {
        ImageLoader
            .Builder(context)
            .build()
    }

    val memoryCacheImageLoader: ImageLoader by lazy {
        ImageLoader
            .Builder(context)
            .memoryCache {
                MemoryCache
                    .Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }.build()
    }
}
