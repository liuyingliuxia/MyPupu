# pupu demo BUG 记录 or 更新记录
 - 水平的线性布局的 recycler 没有 自动换行 
 
  学习笔记：
1. toolbar点击事件：
 setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener{
            item ->when (item.itemId){
            R.id.action_scan -> openCamera()
        }
            false
        }

2.  override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
	是所有actionBar的点击事件

3.Android中 shape 属性详解 ：https://www.cnblogs.com/MianActivity/p/5867776.html ：
        1.Corners ：圆角   2. solid 内部填充色     3.gradient 渐变色   4. stroke 描边属性   5.size 和padding （不常用）
        shape的属性 :1: rectangle矩形   2：oval 椭圆   3：line 线性    4.ring 环形

4.Fragment是到Android3.0+ 以后，Android新增了Fragments，在没有 Fragment 之前，一个屏幕只能放一个 Activity。这是一个起源时间大家要知道是什么时候开始引入的。


5. 当把 xml的 selector自定义为文字字体色，会出现紫色

6.    android:imeOptions="actionSearch" EditText修改键盘的回车键

7.activity_toolbox.xml中的 <View />作为分割线 会报错 ，改为 <TextView> 解决

8.使EditView框中的键盘回车变成搜索 ：
设置
android:imeOptions="actionSearch"
android:maxLines="1"
较低SDK版本中，还需要增加
android:singleLine="true"

9.android:alpha="0"   看不见
  android:alpha="0.5"   透视
  android:alpha="1"   全可见

10. recycler 用法 ：
    新建一个 adapter
   -> 初始化适配器
   -> 初始化布局管理器
   -> 设置布局管理器方向
   -> 把布局管理器赋给 recycler控件的布局管理器
   -> 把适配器赋给 recycler控件的适配器

11.animation的实现原理：在每次进行绘图的时候,通过对整块画布的矩阵进行变换,从而实现一种视图坐标的移动,
但实际上其在 View 内部真实的坐标位置及其他相关属性始终恒定，也就是说，你只是看见他的位置变了，但是，他其实还是在原来的位置。

12. 第一次点击 + 弹出控件 - num 之后点击 + num + 1 直到最大值
    点击 -  num -1 直到 0  直接收起控件

     初始值 num = 0
    + 的监听事件：
    if (num = 0){

    }
13. public int indexOf(int ch): 返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。

14.   //进入视图时 获取焦点并 强制显示软键盘
            actSearch.isFocusable = true
            actSearch.isFocusableInTouchMode = true
            actSearch.requestFocus()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
15. 实现分类页面时，使用recycler嵌套recycler的方法 过于复杂

16.getColor(Int) 的替代方案： ContextCompat.getColor(mContext,R.color.color23)

17. 在最外层的recyclerView布局文件中加上descendantFocusability属性的值有三种：android:descendantFocusability="beforeDescendants"

    beforeDescendants：viewgroup会优先其子类控件而获取到焦点
    afterDescendants：viewgroup只有当其子类控件不需要获取焦点时才获取焦点
    blocksDescendants：viewgroup会覆盖子类控件而直接获得焦点
18. 嵌套recycler触摸事件思路分析：
    子recycler 到达顶部或底部时，事件交给父容器拦截 处理（分发）
    在子recycler内部滑动时，不分发出去，自己内部处理

19. 局部圆角
    <!-- android:radius 圆角的半径 -->
        <corners
            android:topLeftRadius="8dp"
            android:topRightRadius="8dp"
            android:bottomRightRadius="0dp"
            android:bottomLeftRadius="0dp"/>

20.
    //Fragment 的扩展函数
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) = beginTransaction().func().commit()

    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) = supportFragmentManager.inTransaction { add(frameId, fragment) }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) = supportFragmentManager.inTransaction{replace(frameId, fragment)}

21. 要培养 代码洁癖
22.  getColor 方法被废弃 使用新的： ContextCompat.getColor(mActivity , R.color.color23
