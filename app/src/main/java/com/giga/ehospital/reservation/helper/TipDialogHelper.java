package com.giga.ehospital.reservation.helper;

import android.content.Context;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class TipDialogHelper {

    private QMUITipDialog mQmuiTipDialog;

    private long mDefaultTipDialogShowTime = 1500l;

    private Context mContext;

    public TipDialogHelper(Context mContext) {
        this.mContext = mContext;
    }

    public QMUITipDialog closeTipDialog() {
        if (mQmuiTipDialog != null && mQmuiTipDialog.isShowing()) {
            mQmuiTipDialog.hide();
        }
        return mQmuiTipDialog;
    }

    public QMUITipDialog showNetworkLoadingTipDialog(String detailMsg) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(detailMsg == null ? "正在加载" : detailMsg)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
        return mQmuiTipDialog;
    }

    // 指定时间消失
    public QMUITipDialog showNetworkLoadingTipDialog(String detailMsg, long delayTime, View viewToPostDelayed) {
        mQmuiTipDialog = showNetworkLoadingTipDialog(detailMsg);
        viewToPostDelayed.postDelayed(() -> closeTipDialog(), delayTime);
        return mQmuiTipDialog;
    }

    public QMUITipDialog showErrorTipDialog(String errorMsg, View viewToPostDelayed) {
        showErrorTipDialog(errorMsg, mDefaultTipDialogShowTime, viewToPostDelayed);
        return mQmuiTipDialog;
    }

    public QMUITipDialog showInfoTipDialog(String infoMsg, View viewToPostDelayed) {
        showInfoTipDialog(infoMsg, mDefaultTipDialogShowTime, viewToPostDelayed);
        return mQmuiTipDialog;
    }

    public QMUITipDialog showSuccessTipDialog(String successMsg, View viewToPostDelayed) {
        showSuccessTipDialog(successMsg, mDefaultTipDialogShowTime, viewToPostDelayed);
        return mQmuiTipDialog;
    }

    public QMUITipDialog showErrorTipDialog(String errorMsg, Long delayMills, View viewToPostDelayed) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(errorMsg == null ? "发送失败" : errorMsg)
                .create();
        mQmuiTipDialog.show();
        viewToPostDelayed.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
        return mQmuiTipDialog;
    }

    public QMUITipDialog showInfoTipDialog(String infoMsg, Long delayMills, View viewToPostDelayed) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(infoMsg == null ? "请勿重复操作" : infoMsg)
                .create();
        mQmuiTipDialog.show();
        viewToPostDelayed.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
        return mQmuiTipDialog;
    }

    public QMUITipDialog showSuccessTipDialog(String successMsg, Long delayMills, View viewToPostDelayed) {
        closeTipDialog();
        mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(successMsg == null ? "发送成功" : successMsg)
                .create();
        mQmuiTipDialog.show();
        viewToPostDelayed.postDelayed(mQmuiTipDialog::hide, delayMills == null ? 1500 : delayMills);
        return mQmuiTipDialog;
    }
}
