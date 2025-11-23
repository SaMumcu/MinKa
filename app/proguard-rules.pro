# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-keep class androidx.room.** { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
    @androidx.room.* <fields>;
}
-keep class * implements androidx.room.RoomDatabase
-keep class * implements androidx.room.RoomDatabase$Callback
-keep @androidx.room.Dao class *
-keepclassmembers class * {
    @androidx.room.Dao <methods>;
}


-keep class javax.inject.** { *; }
-dontwarn javax.inject.**
-keepattributes RuntimeVisibleAnnotations
-keep class com.cattishapps.minka.**_Factory { *; }
-keep class com.cattishapps.minka.**_MembersInjector { *; }

-keep class com.cattishapps.MinKaApp { *; }

