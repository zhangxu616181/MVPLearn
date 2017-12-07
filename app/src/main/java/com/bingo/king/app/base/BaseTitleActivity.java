package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.utils.CommonUtils;

/**
 *
 */

public abstract class BaseTitleActivity<P extends IPresenter> extends LoadingBaseActivity<P>
{
    protected Toolbar toolbar;
    protected TextView tv_title;
    protected TextView toolbar_right_action;
    @Override
    public int initView(Bundle savedInstanceState)
    {
        return R.layout.activity_base_toolbar;
    }

    @Override
    public int getBaseFrameLayoutId()
    {
        return R.id.base_fl_content;
    }

    @Override
    protected void loadData(Bundle savedInstanceState)
    {
        initControlViews();
    }

    /**
     * 控件初始化操作
     */
    private void initControlViews()
    {
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.toolbar_title);
        toolbar_right_action = findViewById(R.id.toolbar_right_action);
        //设置相关默认操作
        setTitleNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setTitleBgColor(R.color.base_title_color);

        setInflateMenu();

        //左边Navigation Button监听回调
        toolbar.setNavigationOnClickListener(this::callbackOnClickNavigationAction);
        //右边菜单item监听回调
        toolbar.setOnMenuItemClickListener(this::callbackOnMenuAction);

        clicks(toolbar_right_action).subscribe(o -> callbackOnClickRightAction(toolbar_right_action));
    }

    /**
     * 为toolbar设置menu项
     */
    private void setInflateMenu() {
        if (getMenuLayoutId() > 0)
            toolbar.inflateMenu(getMenuLayoutId());
    }

    /**
     * 获取菜单资源id，默认无，子类可重写
     *
     */
    protected int getMenuLayoutId() {
        return 0;
    }

    /**
     * 设置主标题
     *
     */
    public void setToorBarTitle(Object object) {
        toolbar.setTitle(CommonUtils.getResultString(object));
    }

    /**
     * 设置子类标题
     *
     */
    public void setSubTitle(Object object) {
        toolbar.setSubtitle(CommonUtils.getResultString(object));
    }

    /**
     * 设置主标题字体颜色
     *
     */
    public void setToorBarTitleColor(Object object) {
        toolbar.setTitleTextColor(CommonUtils.getResultColor(object));
    }

    /**
     * 设置子标题字体颜色
     *
     */
    public void setSubTitleColor(Object object) {
        toolbar.setSubtitleTextColor(CommonUtils.getResultColor(object));
    }

    /**
     * 设置logoIcon
     *
     */

    public void setLogoIcon(int resId) {
        toolbar.setLogo(resId);
    }

    /**
     * 设置中间标题
     *
     */
    public void setToolbarMiddleTitle(Object object) {
        tv_title.setText(CommonUtils.getResultString(object));
    }

    /**
     * 设置右边单个按钮
     *
     */
    public void setToolbarRightAction(Object object) {
        toolbar_right_action.setText(CommonUtils.getResultString(object));
    }

    /**
     * 设置标题栏背景颜色
     *
     */
    protected void setTitleBgColor(int color) {
        toolbar.setBackgroundColor(CommonUtils.getResultColor(color));
    }


    /**
     * 设置左边标题图标
     *
     */
    public void setTitleNavigationIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }


    /**
     * 隐藏标题栏
     */
    protected void hideToolbar() {
        if (toolbar.getVisibility() == View.VISIBLE)
            toolbar.setVisibility(View.GONE);
    }

    /**
     * 不显示 NavigationButton
     */
    public void hideTitleNavigationButton() {
        toolbar.setNavigationIcon(null);
    }


    /**
     * Navigation Button点击回调，默认回退销毁页面，其他操作子类可重写
     *
     */
    protected void callbackOnClickNavigationAction(View view) {
        onBackPressed();
    }

    /**
     * 右边操作点击回调，其他操作子类可重写
     *
     */
    protected void callbackOnClickRightAction(View view){

    }


    /**
     * menu点击回调，默认无，子类可重写
     *
     */
    protected boolean callbackOnMenuAction(MenuItem item) {
        return false;
    }



//    protected int getMenuLayoutId() {
//        return R.menu.menu_main;
//    }

//    public boolean callbackOnMenuAction(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_test:
//                ToastTool.showNativeShortToast(ToolbarActivity.this, "测试");
//                return true;
//            case R.id.menu_search:
//                ToastTool.showNativeShortToast(ToolbarActivity.this, "搜索");
//                return true;
//            case R.id.menu_settings:
//                ToastTool.showNativeShortToast(ToolbarActivity.this, "设置");
//                return true;
//            case R.id.menu_check_update:
//                ToastTool.showNativeShortToast(ToolbarActivity.this, "检查更新");
//                return true;
//            case R.id.menu_about:
//                ToastTool.showNativeShortToast(ToolbarActivity.this, "关于");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


}
