
    签名秘钥信息：
	Password: dewin123$%^
    Alias: dewin
	MD5: 4D:22:A8:D9:31:2E:6E:DC:90:D8:57:B2:DB:CB:92:09
	SHA1: 39:FA:A9:0B:52:E0:5E:94:35:A2:1D:E1:2E:31:36:08:4C:35:CC:B7
	签名(MD5去掉冒号并且转小写): 4D22A8D9312E6EDC90D857B2DBCB9209

    # ---------------------------------------------------------------------------------------------
	资源文件名称定义规则：
	src/main/res/color ->
	    {type}_{color}.xml
	    color_xx2xx2xx.xml
	    说明：type区域：
	          1)类型，默认color开头

	          color区域：
	          1)默认颜色
	          2)按下或选中的颜色，即 Pressed/Selected/Checked = true
	          3)控件失效的颜色，即 Enable = false

	src/main/res/drawable ->
	    1、只有颜色的情况
	    {type}_{solid}_{stroke}_{dp}_{radius}.xml
	    xx_xx2xx2xx_xx2xx_xdp_rx.xml
	    说明：type区域
	          1)类型，即selector/shape/layer_list/level_list等，如果是特殊形状，例如oval/line等，可这样写shape_oval_xxx

	          solid区域
	          1)默认颜色
	          2)按下或选中后的颜色，即 Pressed或Selected或Checked = true
	          3)控件失效的颜色，即 Enable = false

	          dp区域
	          1)stroke的width，默认1dp，如果是1dp可不写

	          stroke区域
	          1)默认颜色
	          2)按下或选中的颜色，即 Pressed或Selected或Checked = true

	          radius区域
	          1)圆角大小，r1代表radius=1dp，r2代表radius=2dp

        2、引用图片的情况
	    {type}_{image}.xml
	    xx_xx.xml
	    例如：CheckBox点击的样式，有三种状态的图片checkbox_small_normal.png/checkbox_small_press.png/checkbox_small_disable.png，
	          可以根据图片名称来定义：selector_checkbox_small.xml
	    说明：type区域
	          1)类型，一般用图片的话，基本都是做变换效果的，所以一般默认用selector

	          image区域
	          1)根据引用的图片名称来命名
