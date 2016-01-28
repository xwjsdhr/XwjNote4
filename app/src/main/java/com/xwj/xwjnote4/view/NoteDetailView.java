package com.xwj.xwjnote4.view;

import com.xwj.xwjnote4.model.Note;

/**
 * note详情页面的视图层接口
 * <p/>
 * Created by xwjsd on 2016-01-05.
 */
public interface NoteDetailView {
    /**
     * 绑定指定便签的信息
     *
     * @param note
     */
    void bindNote(Note note);

    /**
     * 结束当前的Activity
     */
    void finishActivity();

    /**
     * 获取note的标题
     */
    String getNoteTitle();

    /**
     * 获取标题的内容
     *
     * @return
     */
    String getNoteContent();

    /**
     * 改变toolBar上的Navigation的图标
     */
    void changeNavigationIcon(int resId);

    /**
     * 显示对话框
     */
    void showDialog();

    /**
     * 判断当前的文本框是否都是未聚焦。
     *
     * @return
     */
    boolean isEditTextUnFocused();


    /**
     * 显示snackbar
     * @param msg
     */
    void showdSnackbar(String msg);

    /**
     * 获取当前title的字数
     * @return
     */
    int getTitleNum();

    /**
     * 获取内容字数
     * @return
     */
    int getContentNum();

    /**
     * 绑定标题字数
     * @param num
     */
    void setTitleNum(int num);

    /**
     * 绑定内容字数
     * @param num
     */
    void setContentNum(int num);

    /**
     * 获取总字数
     * @return
     */
    int getTotalNum();

    /**
     * 设置总字数
     * @param num
     */
    void setTotalNum(int num);

    /**
     * 显示完成按钮
     */
    void showFinishBtn();

    /**
     * 隐藏完成按钮
     */
    void hideFinishBtn();

    /**
     * 判断当前标题和内容是否都不为空
     * @return
     */
    boolean isEditTextUnEmpty();

    /**
     * 获取intent中的Note
     * @return
     */
    Note detailGetIntent();
}
