package com.giga.ehospital.reservation.fragment.sysadmin;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giga.ehospital.ReservationApplication;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.activity.HomeActivity;
import com.giga.ehospital.reservation.adapter.sysadmin.DBManageAdapter;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.sysamdin.DbDataManager;
import com.giga.ehospital.reservation.model.system.BackupDBFile;
import com.giga.ehospital.reservation.model.system.BackupDBFileDao;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DBManageFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.db_manage_recyclerview)
    RecyclerView mDBManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_db_manage_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;
    @BindString(R.string.SUCCESS_MESSAGE)
    String SUCCESS_MESSAGE;
    @BindString(R.string.create_db_backup)
    String CREATE_DB_BACKUP_TITLE;
    @BindString(R.string.BACKUP_SUCCESS_MESSAGE)
    String BACKUP_SUCCESS_MESSAGE;
    @BindString(R.string.BACKUPING_MESSAGE)
    String BACKUPING_MESSAGE;
    @BindString(R.string.RESTORING_MESSAGE)
    String RESTORING_MESSAGE;
    @BindString(R.string.RESTORE_SUCCESS_MESSAGE)
    String RESTORE_SUCCESS_MESSAGE;


    private BackupDBFileDao backupDBFileDao;
    private DbDataManager dbDataManager;
    private List<BackupDBFile> backupDBFiles;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected void initTopBar() {
        super.initTopBar();
        mTopBar.addRightTextButton(CREATE_DB_BACKUP_TITLE, mTopBar.getId()).setOnClickListener(v -> showEditTextDialog());
    }

    /**
     * 点击新建备份按钮后，显示的dialog
     */
    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(CREATE_DB_BACKUP_TITLE)
                .setPlaceholder("请输入备份文件名")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        backup(text.toString());
                        dialog.dismiss();
                    } else {
                        Toasty.info(getContext(), "请输入备份文件名", Toasty.LENGTH_SHORT, true).show();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(true);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                initData();
                mTopBar.postDelayed(() -> mPullRefreshLayout.finishRefresh(), 1500l);
            }
        });
    }

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sysadmin_db_manage;
    }

    @Override
    protected void initLastCustom() {
        if (backupDBFileDao == null) {
            backupDBFileDao = ReservationApplication.getInstances().getDaoSession().getBackupDBFileDao();
        }
        if (dbDataManager == null) {
            dbDataManager = new DbDataManager();
        }
        initData();
    }

    private void reloadView() {
        FragmentActivity activity = getActivity();

    }

    /**
     * list
     */
    public void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        dbDataManager.list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(LOADING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            dumpAllData(response.data);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    /**
     * backup
     *
     * @param filename backup filename
     */
    public void backup(String filename) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        dbDataManager.backup(filename)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(BACKUPING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            initData();
                            Toasty.success(getContext(), BACKUP_SUCCESS_MESSAGE, Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    /**
     * restore
     *
     * @param filename
     */
    public void restore(String filename) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        dbDataManager.restore(filename)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(RESTORING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
                            Toasty.success(getContext(), RESTORE_SUCCESS_MESSAGE, Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }



    /**
     * 接收服务端的json数据后序列化成BackupDBFile对象列表
     * 然后将这些对象存储到SQLite中
     *
     * @param json json数据
     */
    private void dumpAllData(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        List<BackupDBFile> backupDBFiles = GsonParser.fromJSONArray(jsonArray, BackupDBFile.class);
        backupDBFileDao.deleteAll();
        backupDBFileDao.insertInTx(backupDBFiles);
    }

    /**
     * 从SQLite中读取所有与BackupDBFile相对应的数据
     */
    private void readAllData() {
        backupDBFiles = backupDBFileDao.loadAll();
    }

    /**
     * 初始化页面数据
     *
     * 流程：
     *  1.清空本地数据
     *  2.接收服务端数据
     *  3.存入本都数据
     *  4.页面显示数据
     */
    protected void initData() {
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    /**
     * 页面显示数据
     */
    private void initListView() {
        mDBManageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        DBManageAdapter adapter = new DBManageAdapter(getContext(), backupDBFiles);

        adapter.setOnItemClickListener((itemView, pos) -> {

            BackupDBFile backupDBFile = backupDBFiles.get(pos);
            String dialogTitle = backupDBFile.getFilename();
            String dialogContent = "备份时间：" + backupDBFile.getDate();
            showMessagePositiveDialog(dialogTitle, dialogContent,
                    "取消", (dialog, index) -> {dialog.dismiss();},
                    "还原", (dialog, index) -> {
                        restore(dialogTitle);
                        dialog.dismiss();
                    });
        });
        mDBManageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mDBManageRecyclerView.setAdapter(adapter);
    }

}
