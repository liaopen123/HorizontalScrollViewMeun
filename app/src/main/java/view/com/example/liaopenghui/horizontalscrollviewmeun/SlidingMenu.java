package view.com.example.liaopenghui.horizontalscrollviewmeun;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by liaopenghui on 16/7/30.
 */
public class SlidingMenu extends HorizontalScrollView {
    /**
     * 屏幕的宽度
     */
    private  int mScreenWidth;
    /**距离右边的值    px*/
    private static int mMenuRightPadding= 50;
    /**菜单栏的宽度*/
    private  int mMenuWidth;
    private  int mHalfMenuWidth;
    private boolean once;
    private boolean isOpen;//菜单栏的打开状态

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕的宽度
        DensityUtil densityUtil =  new DensityUtil(context);
        mScreenWidth = densityUtil.getScreenWidth();
    }

    public SlidingMenu(Context context) {
        super(context);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        if(!once) {
            //先得到第一个包裹对象,然后得到布局中的第一个view也就是菜单的view
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
            ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
            //dp2px
            mMenuRightPadding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content
                            .getResources().getDisplayMetrics());

            //菜单栏的宽度 =  屏幕宽度-RightPadding值
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            //重新改变菜单栏的宽度
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            //将菜单栏隐藏
            this.scrollTo(mMenuWidth,0);
            once = true;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                }else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        if (isOpen)
        {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle()
    {
        if (isOpen)
        {
            closeMenu();
        } else
        {
            openMenu();
        }
    }

}
