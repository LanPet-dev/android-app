package com.lanpet.core.common

import androidx.compose.ui.graphics.vector.ImageVector
import com.lanpet.core.common.myiconpack.Alert
import com.lanpet.core.common.myiconpack.AlertFill
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.myiconpack.Bell
import com.lanpet.core.common.myiconpack.BellFill
import com.lanpet.core.common.myiconpack.Bookmark
import com.lanpet.core.common.myiconpack.BookmarkFill
import com.lanpet.core.common.myiconpack.CheckBox
import com.lanpet.core.common.myiconpack.Close
import com.lanpet.core.common.myiconpack.CloseMini
import com.lanpet.core.common.myiconpack.Community
import com.lanpet.core.common.myiconpack.CommunityFill
import com.lanpet.core.common.myiconpack.Contents
import com.lanpet.core.common.myiconpack.ContentsFill
import com.lanpet.core.common.myiconpack.Edit
import com.lanpet.core.common.myiconpack.EditFill
import com.lanpet.core.common.myiconpack.File
import com.lanpet.core.common.myiconpack.Heart
import com.lanpet.core.common.myiconpack.HeartFill
import com.lanpet.core.common.myiconpack.Image
import com.lanpet.core.common.myiconpack.Like
import com.lanpet.core.common.myiconpack.LikeFill
import com.lanpet.core.common.myiconpack.Message
import com.lanpet.core.common.myiconpack.More
import com.lanpet.core.common.myiconpack.My
import com.lanpet.core.common.myiconpack.MyFill
import com.lanpet.core.common.myiconpack.Plus
import com.lanpet.core.common.myiconpack.Search
import com.lanpet.core.common.myiconpack.Send
import com.lanpet.core.common.myiconpack.Setting
import kotlin.collections.List as ____KtList

public object MyIconPack

private var allIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (allIcons != null) {
            return allIcons!!
        }
        allIcons =
            listOf(
                Search,
                Like,
                HeartFill,
                Alert,
                CheckBox,
                Community,
                CloseMini,
                File,
                AlertFill,
                BellFill,
                Bell,
                Contents,
                Plus,
                Close,
                Image,
                Setting,
                Message,
                Send,
                My,
                EditFill,
                More,
                BookmarkFill,
                Edit,
                ContentsFill,
                LikeFill,
                MyFill,
                Bookmark,
                CommunityFill,
                ArrowLeft,
                Heart,
            )
        return allIcons!!
    }
