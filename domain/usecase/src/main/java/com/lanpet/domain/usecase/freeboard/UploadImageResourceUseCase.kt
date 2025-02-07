package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import com.lanpet.domain.repository.S3UploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UploadImageResourceUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
        private val s3UploadRepository: S3UploadRepository,
    ) {
        @OptIn(FlowPreview::class)
        operator fun invoke(
            sarangbangId: String,
            imageList: List<ByteArray>,
        ) = freeBoardRepository
            .getResourceUploadUrl(sarangbangId, imageList.size)
            .flatMapConcat { urlItems ->
                flow {
                    coroutineScope {
                        val results =
                            urlItems.items.mapIndexed { index, url ->
                                async {
                                    s3UploadRepository
                                        .uploadImageResource(
                                            url = url,
                                            byteArray = imageList[index],
                                        ).first()
                                }
                            }
                        delay(2000)
                        emit(results.awaitAll())
                    }
                }
            }.flowOn(Dispatchers.IO)
    }
