package com.giga.ehospital.reservation.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;


public class DialogHelper {

    private Context mContext;
    private QMUIDialog dialog;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    public DialogHelper(Context mContext) {
        this.mContext = mContext;
    }

    public interface MultiChoiceDialogConfirmListener {
        void onClick(QMUIDialog dialog, int index, int[] selectedIndexes);
    }

    /**
     * 生成带取消以及确认的对话框，取消及其确认使用的默认的主题颜色(蓝色)，
     *
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public QMUIDialog showMessagePositiveDialog(String title, String content,
                                                String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        dialog = new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 生成带取消以及删除的对话框，删除字体采用的红色字体，
     *
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public QMUIDialog showMessageNegativeDialog(String title, String content,
                                                String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        dialog = new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .addAction(0, cancelMsg, QMUIDialogAction.ACTION_PROP_NEGATIVE, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * @param title           题目
     * @param content         很长的内容
     * @param cancelMsg       显示取消位置的按钮  文字 暂时未使用,可以传入null
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器  暂时未使用,可以传入null
     * @param confirmListener 点击确认的监听器  暂时未使用,客串日null
     */
    public QMUIDialog showLongMessageDialog(String title, String content,
                                            String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                            String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        dialog = new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 菜单类型对话框
     *
     * @param itemClickListener 选中某一个时的监听器
     * @param items             可供选择的子项名称
     */
    public QMUIDialog showMenuDialog(List<String> items, DialogInterface.OnClickListener itemClickListener) {
        dialog = new QMUIDialog.MenuDialogBuilder(mContext)
                .addItems(items.toArray(new String[0]), itemClickListener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }


    /**
     * 带一个单选框的dialog
     *
     * @param title           题目
     * @param content         内容
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     * @param checked         默认是否选中
     */
    public QMUIDialog showConfirmMessageDialog(String title, String content,
                                               String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                               String confirmMsg, QMUIDialogAction.ActionListener confirmListener,
                                               Boolean checked) {
        dialog = new QMUIDialog.CheckBoxMessageDialogBuilder(mContext)
                .setTitle(title)
                .setMessage(content)
                .setChecked(checked == null ? Boolean.TRUE : checked)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 菜单对话框,默认有选中的项目
     *
     * @param listener
     * @param items
     * @param checkedIndex
     */
    public QMUIDialog showSingleChoiceDialog(List<String> items, DialogInterface.OnClickListener listener, int checkedIndex) {
        dialog = new QMUIDialog.CheckableDialogBuilder(mContext)
                .setCheckedIndex(checkedIndex)
                .addItems(items.toArray(new String[0]), listener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 多选对话框,含有很多单选框,但不是最多
     *
     * @param cancelMsg         显示取消位置的按钮  文字
     * @param confirmMsg        显示确认位置的按钮  文字
     * @param cancelListener    点击取消的监听器
     * @param confirmListener   点击确认的监听器
     * @param itemClickListener item被点击时的监听器
     * @param items             可供选择的子项
     * @param checkedItems      哪些子项默认被选中
     */
    public QMUIDialog showMultiChoiceDialog(String title,
                                            String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                            String confirmMsg, MultiChoiceDialogConfirmListener confirmListener,
                                            List<String> items, int[] checkedItems, DialogInterface.OnClickListener itemClickListener) {
        QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(mContext);
        dialog = builder
                .setTitle(title)
                .setCheckedItems(checkedItems)
                .addItems(items.toArray(new String[0]), itemClickListener)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, (dialog, index) -> confirmListener.onClick(dialog, index, builder.getCheckedItemIndexes()))
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 带有很多的单选框的 dialog
     *
     * @param cancelMsg         显示取消位置的按钮  文字
     * @param confirmMsg        显示确认位置的按钮  文字
     * @param items             可供选择的子项
     * @param checkedItems      哪些子项默认被选中
     * @param cancelListener    点击取消的监听器
     * @param confirmListener   点击确认的监听器
     * @param itemClickListener item被点击时的监听器
     */
    public QMUIDialog showNumerousMultiChoiceDialog(String title,
                                                    String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                                    String confirmMsg, MultiChoiceDialogConfirmListener confirmListener,
                                                    List<String> items, int[] checkedItems, DialogInterface.OnClickListener itemClickListener) {
        QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(mContext);
        dialog = builder
                .setTitle(title)
                .setCheckedItems(checkedItems)
                .addItems(items.toArray(new String[0]), itemClickListener)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, (dialog, index) -> confirmListener.onClick(dialog, index, builder.getCheckedItemIndexes()))
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 带editTextView 的dialog
     *
     * @param title           标题
     * @param placeHolder     EditTextView 的 hint
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public QMUIDialog showEditTextDialog(String title, String placeHolder,
                                         String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                         String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        dialog = new QMUIDialog.EditTextDialogBuilder(mContext)
                .setTitle(title)
                .setPlaceholder(placeHolder)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener)
                .create(mCurrentDialogStyle);
        dialog.show();
        return dialog;
    }

    /**
     * 高度自适应 带editTextView 的dialog
     *
     * @param hintText        EditTextView 的 hint
     * @param content         正文
     * @param cancelMsg       显示取消位置的按钮  文字
     * @param confirmMsg      显示确认位置的按钮  文字
     * @param cancelListener  点击取消的监听器
     * @param confirmListener 点击确认的监听器
     */
    public QMUIDialog showAutoDialog(String hintText, String content,
                                     String cancelMsg, QMUIDialogAction.ActionListener cancelListener,
                                     String confirmMsg, QMUIDialogAction.ActionListener confirmListener) {
        QMAutoTestDialogBuilder autoTestDialogBuilder = (QMAutoTestDialogBuilder) new QMAutoTestDialogBuilder(mContext, hintText, content)
                .addAction(cancelMsg, cancelListener)
                .addAction(confirmMsg, confirmListener);
        dialog = autoTestDialogBuilder.create(mCurrentDialogStyle);
        dialog.show();
        QMUIKeyboardHelper.showKeyboard(autoTestDialogBuilder.getEditText(), true);
        return dialog;
    }

    class QMAutoTestDialogBuilder extends QMUIDialog.AutoResizeDialogBuilder {
        private Context mContext;
        private EditText mEditText;
        private String hintText;
        private String content;

        public QMAutoTestDialogBuilder(Context mContext) {
            this(mContext, "请输入", "内容正文");
        }

        public QMAutoTestDialogBuilder(Context mContext, String hintText, String content) {
            super(mContext);
            this.mContext = mContext;
            this.hintText = hintText;
            this.content = content;
        }

        public EditText getEditText() {
            return mEditText;
        }

        @Override
        public View onBuildContent(QMUIDialog dialog, ScrollView parent) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int padding = QMUIDisplayHelper.dp2px(mContext, 20);
            layout.setPadding(padding, padding, padding, padding);
            mEditText = new EditText(mContext);
            QMUIViewHelper.setBackgroundKeepingPadding(mEditText, QMUIResHelper.getAttrDrawable(mContext, R.attr.qmui_list_item_bg_with_border_bottom));
            mEditText.setHint(hintText);
            LinearLayout.LayoutParams editTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dpToPx(50));
            editTextLP.bottomMargin = QMUIDisplayHelper.dp2px(mContext, 15);
            mEditText.setLayoutParams(editTextLP);
            layout.addView(mEditText);
            TextView textView = new TextView(mContext);
            textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
            textView.setText(content);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_description));
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(textView);
            return layout;
        }
    }
}
