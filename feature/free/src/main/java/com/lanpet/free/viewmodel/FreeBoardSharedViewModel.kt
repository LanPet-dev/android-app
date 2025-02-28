package com.lanpet.free.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FreeBoardSharedViewModel
    @Inject
    constructor() : ViewModel() {
        /**
         * 삭제된 게시글 ID
         */
        val deletedPostId =
            MutableStateFlow<String?>(null)
    }
