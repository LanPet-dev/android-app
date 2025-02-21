package com.lanpet.core.common.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.AsyncImage
import com.lanpet.core.designsystem.R
import com.lanpet.core.manager.LocalCoilManager

/**
 * 주어진 URL을 통해 네트워크에서 이미지를 로드하고 표시하는 컴포저블 함수입니다.
 * URL이 null이거나 비어있는 경우, 기본 이미지를 대신 표시합니다.
 *
 * @param url 로드할 이미지의 URL. null이거나 비어있을 경우 기본 이미지가 표시됩니다.
 * @param modifier 이미지에 적용할 Modifier. 기본값은 Modifier입니다.
 * @param imageLoader 이미지를 로드하는 데 사용할 ImageLoader. 기본값은 LocalCoilManager의 memoryCacheImageLoader입니다.
 * @param drawableRes 이미지 로딩이 실패했을 경우 사용될 DrawableRes ID 입니다. 기본값은 R.drawable.img_animals입니다.
 */
@Composable
fun NetworkImage(
    url: String?,
    modifier: Modifier = Modifier,
    @DrawableRes drawableRes: Int? = R.drawable.img_animals,
    imageLoader: ImageLoader = LocalCoilManager.current.memoryCacheImageLoader,
) {
    if (url.isNullOrEmpty()) {
        Box(
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = url,
            contentDescription = "network_image",
            contentScale = ContentScale.Crop,
            imageLoader = imageLoader,
            modifier = modifier,
            error = painterResource(id = R.drawable.img_animals),
        )
    }
}
