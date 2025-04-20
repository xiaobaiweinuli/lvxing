# 旅行时间计划应用界面设计代码
# 采用现代UI及莫奈颜色系统设计

# 导入必要的库
import json

# 定义莫奈颜色系统的色彩组合
monet_colors = {
    "primary": "#8C736F",  # 主色调：灰褐色
    "secondary": "#D4B8B4",  # 次色调：浅粉色
    "accent": "#ADAAA5",  # 强调色：浅灰色
    "background": "#F4EBE4",  # 背景色：米白色
    "text_primary": "#53565C",  # 主要文本色：深灰色
    "text_secondary": "#999EA2",  # 次要文本色：浅灰色
    "button": "#AAB8AB",  # 按钮色：浅绿色
    "button_text": "#FFFFFF",  # 按钮文本色：白色
    "card": "#FFFFFF",  # 卡片背景色：白色
    "card_shadow": "#E2C6C4"  # 卡片阴影色：浅粉色
}

# 定义现代UI的布局和组件
modern_ui = {
    "app_bar": {
        "height": "56dp",
        "color": monet_colors["primary"],
        "text_color": monet_colors["text_primary"],
        "elevation": "4dp"
    },
    "bottom_navigation": {
        "height": "56dp",
        "color": monet_colors["primary"],
        "selected_item_color": monet_colors["accent"],
        "unselected_item_color": monet_colors["text_secondary"]
    },
    "cards": {
        "corner_radius": "8dp",
        "elevation": "2dp",
        "padding": "16dp",
        "margin": "8dp"
    },
    "buttons": {
        "height": "48dp",
        "corner_radius": "24dp",
        "padding": "16dp",
        "elevation": "4dp"
    },
    "text_fields": {
        "height": "56dp",
        "corner_radius": "4dp",
        "padding": "16dp",
        "hint_color": monet_colors["text_secondary"],
        "text_color": monet_colors["text_primary"]
    }
}

# 生成Kotlin代码
kotlin_code = """
// 旅行时间计划应用界面设计
// 采用现代UI及莫奈颜色系统

// 定义颜色资源
val primaryColor = Color.parseColor("${monet_colors['primary']}")
val secondaryColor = Color.parseColor("${monet_colors['secondary']}")
val accentColor = Color.parseColor("${monet_colors['accent']}")
val backgroundColor = Color.parseColor("${monet_colors['background']}")
val textPrimaryColor = Color.parseColor("${monet_colors['text_primary']}")
val textSecondaryColor = Color.parseColor("${monet_colors['text_secondary']}")
val buttonColor = Color.parseColor("${monet_colors['button']}")
val buttonTextColor = Color.parseColor("${monet_colors['button_text']}")
val cardColor = Color.parseColor("${monet_colors['card']}")
val cardShadowColor = Color.parseColor("${monet_colors['card_shadow']}")

// 应用栏设计
val appBar = AppBarLayout(context).apply {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    setBackgroundColor(primaryColor)
    elevation = ${modern_ui['app_bar']['elevation']}
}

val toolbar = Toolbar(context).apply {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ${modern_ui['app_bar']['height']}
    )
    setTitleTextColor(textPrimaryColor)
    setSubtitleTextColor(textSecondaryColor)
    setBackgroundColor(primaryColor)
}

appBar.addView(toolbar)

// 底部导航设计
val bottomNavigation = BottomNavigationView(context).apply {
    layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ${modern_ui['bottom_navigation']['height']}
    )
    setBackgroundColor(primaryColor)
    itemIconTintList = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
        intArrayOf(accentColor, textSecondaryColor)
    )
    itemTextColor = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
        intArrayOf(accentColor, textSecondaryColor)
    )
}

// 卡片设计
fun createCardView(): CardView {
    return CardView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        radius = ${modern_ui['cards']['corner_radius']}
        cardElevation = ${modern_ui['cards']['elevation']}
        setCardBackgroundColor(cardColor)
        setContentPadding(
            ${modern_ui['cards']['padding']}.toInt(),
            ${modern_ui['cards']['padding']}.toInt(),
            ${modern_ui['cards']['padding']}.toInt(),
            ${modern_ui['cards']['padding']}.toInt()
        )
        (layoutParams as ViewGroup.MarginLayoutParams).setMargins(
            ${modern_ui['cards']['margin']}.toInt(),
            ${modern_ui['cards']['margin']}.toInt(),
            ${modern_ui['cards']['margin']}.toInt(),
            ${modern_ui['cards']['margin']}.toInt()
        )
    }
}

// 按钮设计
fun createButton(): MaterialButton {
    return MaterialButton(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ${modern_ui['buttons']['height']}
        )
        cornerRadius = ${modern_ui['buttons']['corner_radius']}.toInt()
        setPadding(
            ${modern_ui['buttons']['padding']}.toInt(),
            0,
            ${modern_ui['buttons']['padding']}.toInt(),
            0
        )
        elevation = ${modern_ui['buttons']['elevation']}
        setBackgroundColor(buttonColor)
        setTextColor(buttonTextColor)
    }
}

// 文本输入框设计
fun createTextField(): TextInputLayout {
    return TextInputLayout(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        boxCornerRadiusTopStart = ${modern_ui['text_fields']['corner_radius']}.toFloat()
        boxCornerRadiusTopEnd = ${modern_ui['text_fields']['corner_radius']}.toFloat()
        boxCornerRadiusBottomStart = ${modern_ui['text_fields']['corner_radius']}.toFloat()
        boxCornerRadiusBottomEnd = ${modern_ui['text_fields']['corner_radius']}.toFloat()
        hintTextColor = ColorStateList.valueOf(Color.parseColor("${monet_colors['text_secondary']}"))
        defaultHintTextColor = ColorStateList.valueOf(Color.parseColor("${monet_colors['text_secondary']}"))
        setPadding(0, 0, 0, 0)

        val editText = EditText(context)
        editText.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ${modern_ui['text_fields']['height']}
        )
        editText.setTextColor(textPrimaryColor)
        editText.setHintTextColor(Color.parseColor("${monet_colors['text_secondary']}"))
        addView(editText)
    }
}
"""

# 保存设计规范和代码
with open("旅行时间计划应用界面设计规范.json", "w", encoding="utf-8") as f:
    json.dump({"colors": monet_colors, "ui": modern_ui}, f, ensure_ascii=False, indent=4)

with open("旅行时间计划应用界面设计代码.kt", "w", encoding="utf-8") as f:
    f.write(kotlin_code)

print("文件已成功保存：旅行时间计划应用界面设计规范.json 和 旅行时间计划应用界面设计代码.kt")
