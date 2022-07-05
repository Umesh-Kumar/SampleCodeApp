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
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#-dontoptimize
#-dontpreverify

# If you want to enable optimization, you should include the
# following:
# Enable Optimization. # Optimization is turned off by default.
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
#-dontpreverify

#
# Note that you cannot just include these flags in your own
# configuration file; if you are including this file, optimization
# will be turned off. You'll need to either edit this file, or
# duplicate the contents of this file and remove the include of this
# file from your project's proguard.config path property.

# -------------------------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.DialogFragment
-keep public class * extends android.app.Fragment

-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends com.actionbarsherlock.app.SherlockListFragment
-keep public class * extends com.actionbarsherlock.app.SherlockFragment
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends java.lang.Exception


-keep public class * extends androidx.drawerlayout.widget.DrawerLayout
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends androidx.fragment.app.FragmentManager
-keep public class * extends androidx.fragment.app.FragmentTransaction
-keep public class * extends androidx.lifecycle.Observer
-keep public class * extends androidx.lifecycle.ViewModelProvider
-keep public class * extends androidx.navigation.NavController
-keep public class * extends androidx.navigation.Navigation
-keep public class * extends androidx.navigation.ui.NavigationUI
-keep public class * extends androidx.appcompat.app.AppCompatActivity

#-applymapping mapping.txt
-useuniqueclassmembernames

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}

-keep class **$Properties
#-keep class android.support.v4.app.** { *; }
#-keep interface android.support.v4.app.** { *; }
-keep class org.bouncycastle.** { *; }
-keep interface org.bouncycastle.** { *; }
-keep class com.viewpagerindicator.** { *; }
-keep interface com.viewpagerindicator.** { *; }
-keep class org.joda.** { *; }
-keep interface org.joda.** { *; }
-keep class de.greenrobot.** { *; }
-keep interface de.greenrobot.** { *; }
-keep class SecuGen.FDxSDKPro.** { *; }
-keep interface SecuGen.FDxSDKPro.** { *; }
-keep class com.datamini.tpos.usb.api.** { *; }
-keep interface com.datamini.tpos.usb.api.** { *; }

-keep class com.morpho.** { *; }
-keep interface com.morpho.** { *; }

-keep class com.sothree.slidinguppanel.** { *; }
-keep interface com.sothree.slidinguppanel.** { *; }

# Dontwarn-----------------------------------------
# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontwarn android.support.**
-dontwarn org.bouncycastle.**
-dontwarn com.viewpagerindicator.**
-dontwarn org.joda.**
-dontwarn com.example.vi218_blue_audio_demo.**
-dontwarn com.visiontek.app.bt.library.pdftoimage.**
-dontwarn javax.**
-dontwarn java.lang.management.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn com.google.ads.**
-dontwarn org.slf4j.**
-dontwarn org.json.**

##---------------Begin: proguard configuration for Gson ----------
# Gson uses generic type information stored in a class file when working with
#fields. Proguard removes such information by default, so configure it to keep
#all of it.
-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

#-keep class com.finopaytech.fos.models.ExternalAccountModel { *; }
#-keep class com.finopaytech.fos.models.UpiTxnHistoryBean { *; }
#-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson ----------

# common ssl classes
-keepnames class org.apache.** {*;}
-keep public class org.apache.** {*;}
-dontwarn org.apache.commons.ssl.**
-keep class com.mantra.mfs100.** { *; }

##-------------------------------------Dexter Start-------------------------------
-renamesourcefileattribute SourceFile

# Preserve all Dexter classes and method names
-keepattributes InnerClasses, Signature, *Annotation*
-keep class com.karumi.dexter.** { *; }
-keep interface com.karumi.dexter.** { *; }
-keepclasseswithmembernames class com.karumi.dexter.** { *; }
-keepclasseswithmembernames interface com.karumi.dexter.** { *; }

##-------------------------------------Dexter End-----------------------------------
-dontwarn com.sunyard.**

-keep class com.paxsz.easylink.** { *;}
-keep class com.pax.gl.** { *;}
-keep class com.pax.serialport.** { *;}
-keep class pax.ecr.protocol.** { *;}
-ignorewarnings

# GMC -------------------------------------------------
# Remove Log command from code
-assumenosideeffects class android.util.Log{
 public static *** d(...);
 public static *** i(...);
 public static *** v(...);
}

#-injars libs/simple-xml-2.7.1.jar
-keepattributes *Annotation*, Exceptions,InnerClasses,Signature,Deprecated,
                SourceFile,LineNumberTable,EnclosingMethod
# Keep source file name and line number
-keepattributes SourceFile,LineNumberTable
-keepattributes JavascriptInterface
# If you are using custom exceptions, add this line so that custom exception types are skipped during obfuscation:
-keep public class * extends java.lang.Exception

-keep class okhttp3.** {*;}
-keep interface okhttp3.** {*;}
-dontwarn okhttp3.**

-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okio.**

-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-dontwarn org.apache.**

-keep class com.activeandroid.** { *; }
-keep class com.activeandroid.**.** { *; }
-dontwarn com.activeandroid.**

-keep class * extends com.activeandroid.Model
-keep class * extends com.activeandroid.serializer.TypeSerializer

-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-dontwarn android.support.v7.**

# -------------------------------------------------

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# enable for webview
#-keepclassmembers class * {
#    @android.webkit.JavascriptInterface <methods>;
#}

# Support Library
-keep class android.support.** {*;}
-keep interface android.support.** {*;}

# Needed when building against Marshmallow SDK.
  -dontwarn android.app.Notification

# Retrofit and GSON
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**
# Lru Cache
 -keep class com.squareup.picasso.LruCache { *; }
 -dontwarn com.squareup.picasso.LruCache.**

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

#-keep class sun.misc.Unsafe.** { *; }
#-dontwarn sun.misc.Unsafe.**

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keep public class com.google.gson.** {*;}
-keep class * implements com.google.gson.** {*;}
-keep class com.google.gson.stream.** { *; }

#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer

-keepclasseswithmembers class * {@retrofit2.http.* <methods>;}
-keepclasseswithmembers interface * { @retrofit2.* <methods>;}
-dontwarn com.google.appengine.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**
-dontwarn org.codehaus.mojo.**
-dontwarn retrofit2.Platform$Java8
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
#-dontwarn org.codehaus.mojo.animal_sniffer.*

## Butterknife 8.0
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

-dontwarn rx.**

 # Realm
 -keepnames public class * extends io.realm.RealmObject
 -keep @io.realm.annotations.RealmModule class *
 -keep class io.realm.** { *; }
 -dontwarn io.realm.**

 # Firebase
 -keep class com.firebase.** { *; }
 -dontwarn com.firebase.**

-dontwarn androidx.**
#-keep class androidx.** { *; }
#-keep interface androidx.** { *; }

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**

# sqlcipher for room db
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

#-adaptresourcefilenames    **.properties,**.gif,**.jpg
#-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF

#-flattenpackagehierarchy 'myobfuscated'
-overloadaggressively
-flattenpackagehierarchy 'com.samplecodeapp'
-repackageclasses 'com.samplecodeapp'

#-------------------------GM APP---------------------------
-keepclassmembernames class com.samplecodeapp.database.model.* {*;}
-keepclassmembernames class com.samplecodeapp.database.dao.* {*;}
#-keep class com.samplecodeapp.aeps.** {*;}
#-keep class com.samplecodeapp.database.db.GmDatabase {public protected static *;}

-keep class com.samplecodeapp.pojo.** { *; }
-keep class com.samplecodeapp.pojo.* { *; }
-keepclasseswithmembers class com.samplecodeapp.pojo.* { *; }

-keep class org.simpleframework.xml.** {*;}
-keep class java.io.** {*;}

# the following line is for illustration purposes
-keep class com.android.internal.http.multipart.** {*;}

-dontwarn okhttp3.internal.platform.*

-keepattributes EnclosingMethod
-keepattributes InnerClasses

-dontwarn org.xmlpull.v1.**
-dontnote org.xmlpull.v1.**
-keep class org.xmlpull.** { *; }
-keepclassmembers class org.xmlpull.** { *; }

