package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import com.lanpet.domain.repository.S3UploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
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
                urlItems.items
                    .mapIndexed { index, url ->
                        s3UploadRepository
                            .uploadImageResource(
                                url = url,
                                byteArray = imageList[index],
                            ).collect()
                    }.asFlow()
            }.flowOn(Dispatchers.IO)
    }
