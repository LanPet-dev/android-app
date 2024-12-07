# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Compose UI 관련 규칙
-keep class androidx.compose.** { *; }

# Composable 함수 보존
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Compose State 관련
-keepclassmembers class * extends androidx.compose.runtime.State {
    <fields>;
}

# common 패키지의 모든 클래스와 멤버 이름 보존
-keep public class com.lanpet.core.common.** {
    public *;
}

# Modifier extension 함수 보존
-keep class com.lanpet.core.common.CustomModifiersKt { *; }

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses

# @ComposeView 어노테이션이 있는 메서드와 클래스 보존
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

-keepclassmembers class com.lanpet.core.common.* {
    @androidx.compose.runtime.Composable *;
}


