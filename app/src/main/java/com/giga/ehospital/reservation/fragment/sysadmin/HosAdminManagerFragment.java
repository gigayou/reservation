package com.giga.ehospital.reservation.fragment.sysadmin;

import android.app.ProgressDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giga.ehospital.ReservationApplication;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.adapter.sysadmin.HosAdminManageAdapter;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.sysamdin.BuserDataManager;
import com.giga.ehospital.reservation.model.system.Buser;
import com.giga.ehospital.reservation.model.system.BuserDao;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HosAdminManagerFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hosadmin_manage_recyclerview)
    RecyclerView mHosAdminManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_hosadmin_manage_title)
    String title;
    @BindString(R.string.common_right_btn_title)
    String RIGHT_BTN_TITLE;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private static Buser tBuser = new Buser();
    private BuserDao buserDao;
    private BuserDataManager hosAdminDataManager;
    private List<Buser> buserList;

    @Override
    protected void initTopBar() {
        super.initTopBar();
        mTopBar.addRightTextButton(RIGHT_BTN_TITLE, mTopBar.getId()).setOnClickListener(null);
    }

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sysadmin_hosamdin_manage;
    }

    @Override
    protected void initLastCustom() {
        if (buserDao == null) {
            buserDao = ReservationApplication.getInstances().getDaoSession().getBuserDao();
        }
        if (hosAdminDataManager == null) {
            hosAdminDataManager = new BuserDataManager();
        }
        initData();
    }

    /**
     * 初始化页面数据
     */
    private void initData() {
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mHosAdminManageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        HosAdminManageAdapter adapter = new HosAdminManageAdapter(getContext(), buserList);

        adapter.setOnItemClickListener((itemView, pos) -> {

            Buser buser = buserList.get(pos);
            String dialogTitle = buser.getUserName();
            String dialogContent = buser.getLoginId();
            showMessagePositiveDialog(dialogTitle, dialogContent,
                    "取消", (dialog, index) -> {dialog.dismiss();},
                    "关闭", (dialog, index) -> {
                        dialog.dismiss();
                    });
        });
        mHosAdminManageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mHosAdminManageRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        buserList = buserDao.loadAll();
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        hosAdminDataManager.list(tBuser)
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
     * 接收服务端的json数据后序列化成buser对象列表
     * 然后将这些对象存储到SQLite中
     *
     * @param json json数据
     */
    private void dumpAllData(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        List<Buser> busers = GsonParser.fromJSONArray(jsonArray, Buser.class);
        List<Buser> showBusers = new ArrayList<Buser>();
        for (Buser buser : busers) {
            if (buser.getRoleId() == 2) {
                showBusers.add(buser);
            }
        }
        buserDao.deleteAll();
        buserDao.insertInTx(showBusers);
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
}
