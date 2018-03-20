# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#------------------------- 所有App通用混淆 -------------------------
#指定代码的压缩级别
-optimizationpasses 5
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#优化  不优化输入的类文件
-dontoptimize
#不做预校验，可以加快混淆速度
-dontpreverify
#包明不混合大小写
-dontusemixedcaseclassnames
#混淆时是否记录日志，产生映射文件
-verbose
#指定日志映射文件
-printmapping proguardMapping.txt
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#不去忽略非公共的库类的成员
-dontskipnonpubliclibraryclassmembers
#保护注解
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Exceptions,InnerClasses,Signature
#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#忽略警告
-ignorewarning

#---------------------  Android基本类过滤  -------------------
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class android.webkit.**
-keep public class javax.**

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
##保持 Serializable 不被混淆并且enum 类也不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
 #不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}

#---------------------  下方是共性的排除项目  -------------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除
-keepclasseswithmembers class * {
    ... *JNI*(...);
}
-keepclasseswithmembernames class * {
	... *JRI*(...);
}
-keep class **JNI* {*;}

#---------------------  第三方包 -------------------
#-keep class android.support.v4.** { *; }
#-keep class android.support.v7.** { *; }
#-keep class android.support.v13.** { *; }
#-keep class org.apache.commons.net.** { *; }
#-keep class android.support.design.** { *; }
#-keep class com.google.gson.** { *; }
#-keep class com.android.volley.** { *; }
#-keep class com.viewpagerindicator.** { *; }
#-keep class uk.co.senab.photoview.** { *; }
#-keep class com.github.mikephil.charting.** { *; }
#-keep class android.support.annotation.** { *; }
#-keep class com.readystatesoftware.systembartint.** { *; }
#-keep class com.google.zxing.** { *; }

#忽略警告
-keep class android.support.**{*;}
-dontwarn android.support.**
-dontwarn android.webkit.WebView

# 百度地图过滤
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

# butterknife7.0
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepnames class * { @butterknife.Bind *;}
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# retrofit1.9.0
-dontwarn rx.**
-dontwarn com.google.appengine.api.urlfetch.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keepattributes Signature
-keepattributes *Annotation*

# retrofit2.0
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# okhttp
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# universal-image-loader
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }

# 阿里百川
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }

-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**

-keep class com.ut.** {*;}
-dontwarn com.ut.**

-keep class com.ta.** {*;}
-dontwarn com.ta.**

-keep class anet.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**

# ShareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

# 微信支付分享
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

# 支付宝
-keep class com.alipay.android.app.IALiPay{*;}
-keep class com.alipay.android.app.IALixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}

# 百度云推送
#-libraryjars libs/pushservice-5.5.0.50.jar
#-dontwarn com.baidu.**
#-keep class com.baidu.**{*; }

#---------------------  App自定义混淆 -------------------
# 保留实体类和成员
#-keep class com.packagename.bean.** { *; }

# 保留R资源文件
#-keep public class com.packagename.R$*{
#    public static final int *;
#}

# 保留部分内部类用符号"$"连接
#-keep class com.packagename.MainActivity$* { *; }

# 保留所有WebView的JS交互接口类，可以使用全局搜索"addJavascriptInterface"来查找
#-keep class com.packagename.MainActivity$$CommonJavaScriptInterface { *; }

# 保留反射类，以下情况都要keep
# Class.forName("com.xxx.ClassA");ClassA.class.getField("field");ClassA.class.getDeclaredField("field");
# ClassA.class.getMethod("method",new Class[]{});ClassA.class.getDeclaredMethod("method",new Class[]{})
#-keep class com.packagename.ClassA { *; }
