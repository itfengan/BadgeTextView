

# BadgeTextView

A Badge TextView and support text wrapping haha~



![](https://ws2.sinaimg.cn/large/006tNc79gy1fqkc925ubqj30t415040z.jpg)
<img src = https://ws2.sinaimg.cn/large/006tNc79gy1fqkc925ubqj30t415040z.jpg width=400></img>


use as follows 

```xml
<com.fengan.ui.badgetextview.BadgeTitleView
        android:id="@+id/badgeTitleView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="10dp"
        app:badage_content="新闻推荐"
        app:badge_background="@color/colorPrimary"
        app:badge_heigh="15dp"
        app:badge_margin="5dp"
        app:badge_radius="2dp"
        app:badge_textColor="#FFFFFF"
        app:badge_textSize="10sp"
        app:badge_width="53dp" />
```

attrs

```xml
 <!--角标宽度-->
        <attr name="badge_width" format="dimension" />
        <!--角标高度-->
        <attr name="badge_heigh" format="dimension" />
        <!--角标圆角-->
        <attr name="badge_radius" format="dimension" />
        <!--角标距离title文本距离-->
        <attr name="badge_margin" format="dimension" />
        <!--角标文字大小-->
        <attr name="badge_textSize" format="dimension" />
        <!--角标背景-->
        <attr name="badge_background" format="color" />
        <!--角标文字颜色-->
        <attr name="badge_textColor" format="color" />
        <!--角标文字内容-->
        <attr name="badage_content" format="string" />
```

