package com.giga.ehospital.reservation.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class PopViewHelper {

    private QMUIPopup mNormalPopup;
    private QMUIListPopup mListPopup;
    private Context mContext;

    public PopViewHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 显示 popview 带有一个textview
     *
     * @param v                      显示在那个控件上方
     * @param content                内容
     * @param width                  宽度
     * @param popViewDismissListener dialog消失时的监听器
     */
    public QMUIPopup showNormalPopView(View v, String content, Integer width, PopupWindow.OnDismissListener popViewDismissListener) {
        mNormalPopup = new QMUIPopup(mContext, QMUIPopup.DIRECTION_NONE);
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                QMUIDisplayHelper.dp2px(mContext, width == null ? 250 : width),
                WRAP_CONTENT
        ));
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(mContext, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(content);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_description));
        mNormalPopup.setContentView(textView);
        if (popViewDismissListener != null) {
            mNormalPopup.setOnDismissListener(popViewDismissListener);
        }
        mNormalPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mNormalPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mNormalPopup.show(v);
        return mNormalPopup;
    }

    /**
     * 展示一个list类型的PopView
     *
     * @param v                      展示在哪个view上方
     * @param items                  待展示的item
     * @param width                  popview 的宽度
     * @param height                 popview 的盖度
     * @param itemClickListener      item点击的监听器
     * @param popViewDismissListener Popview消失时的监听器
     */
    public QMUIListPopup showListPopView(View v, List<String> items, Integer width, Integer height,
                                         AdapterView.OnItemClickListener itemClickListener, PopupWindow.OnDismissListener popViewDismissListener) {
        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.recyclerview_item_simple, items);
        mListPopup = new QMUIListPopup(mContext, QMUIPopup.DIRECTION_NONE, adapter);
        mListPopup.create(QMUIDisplayHelper.dp2px(mContext, width == null ? 250 : width),
                QMUIDisplayHelper.dp2px(mContext, height == null ? 200 : height), itemClickListener);
        if (popViewDismissListener != null) {
            mListPopup.setOnDismissListener(popViewDismissListener);
        }
        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mListPopup.show(v);
        return mListPopup;
    }

    public void popViewDismiss() {
        if (mNormalPopup != null) {
            mNormalPopup.dismiss();
        }
        if (mListPopup != null) {
            mListPopup.dismiss();
        }
    }
}
