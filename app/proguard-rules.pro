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
#-if class androidx.credentials.CredentialManager
#-keep class androidx.credentials.playservices.** {
#  *;
#}

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# kotlinx-serialization-json specific
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable 어노테이션이 있는 클래스 보존
-keep,includedescriptorclasses class com.lanpet.app.**$$serializer { *; }
-keepclassmembers class com.lanpet.app.** {
    *** Companion;
}
-keepclasseswithmembers class com.lanpet.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# 소스파일 정보 유지
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# 매핑 파일 생성 (난독화된 클래스명과 원본 매핑)
-printmapping mapping.txt

# @Serializable 어노테이션이 있는 클래스 보존
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *;
}

# @HiltViewModel 어노테이션이 붙은 클래스의 이름만 보존
-keep @dagger.hilt.android.lifecycle.HiltViewModel class **

# Hilt
-keep,allowobfuscation @dagger.hilt.android.lifecycle.HiltViewModel class *
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-keep class * implements dagger.hilt.android.internal.lifecycle.* { *; }
-keep class * implements dagger.internal.* { *; }
-keep class **.*_HiltModules$* { *; }
-keep class **.Hilt_* { *; }