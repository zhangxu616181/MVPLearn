package com.bingo.king.mvp.model.http;

import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.entity.zhihu.CommentBean;
import com.bingo.king.mvp.model.entity.zhihu.DailyListBean;
import com.bingo.king.mvp.model.entity.zhihu.DetailExtraBean;
import com.bingo.king.mvp.model.entity.zhihu.HotListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeListBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhihuDetailBean;

import io.reactivex.Observable;

/**
 * <获取数据的接口>
 * Created by adou on 2017/11/2:22:25.
 */
public interface IRepository
{
    /**
     * 干货
     */
    Observable<GankEntity> gank(String type,int pageSize,String page);
    /**
     * 福利
     */
    Observable<GankEntity> getRandomGirl();
    /**
     * 最新日报
     */
    Observable<DailyListBean> requestDailyList();
    /**
     * 主题日报
     */
    Observable<ThemeListBean> requestThemeList();
    /**
     * 主题日报详情
     */
    Observable<ThemeChildListBean> requestThemeChildList(int id);

    /**
     * 专栏日报
     */
    Observable<SectionListBean> requestSectionList();


    /**
     * 专栏日报详情
     */
    Observable<SectionChildListBean> requestSectionChildList(int id);

    /**
     * 热门日报
     */
    Observable<HotListBean> requestHotList();

    /**
     * 日报详情
     */
    Observable<ZhihuDetailBean> requestDetailInfo(int id);

    /**
     * 日报的额外信息
     */
    Observable<DetailExtraBean> requestDetailExtraInfo(int id);

    /**
     * 日报的长评论
     */
    Observable<CommentBean> requestLongCommentInfo(int id);

    /**
     * 日报的短评论
     */
    Observable<CommentBean> requestShortCommentInfo(int id);


}
