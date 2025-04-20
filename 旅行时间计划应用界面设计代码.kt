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
