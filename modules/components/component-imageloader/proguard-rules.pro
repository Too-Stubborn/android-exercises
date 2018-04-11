# Glide 4.6.1
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# 如果你的 target API 低于 Android API 27，请添加：
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
# for DexGuard only 如果你使用 DexGuard 你可能还需要添加：
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule