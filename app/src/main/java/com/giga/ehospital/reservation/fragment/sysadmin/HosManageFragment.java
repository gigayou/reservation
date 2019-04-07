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
import com.giga.ehospital.reservation.adapter.sysadmin.HosManageAdapter;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.sysamdin.HosDataManager;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.giga.ehospital.reservation.model.hospital.HospitalDao;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HosManageFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hos_manage_recyclerview)
    RecyclerView mHosManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_hos_manage_title)
    String title;
    @BindString(R.string.common_right_btn_title)
    String RIGHT_BTN_TITLE;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private static Hospital tHospital = new Hospital();
    private HospitalDao hospitalDao;
    private HosDataManager hosDataManager;
    private List<Hospital> hospitalList;

    // qmui dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

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
        return R.layout.fragment_sysadmin_hos_manage;
    }

    @Override
    protected void initLastCustom() {
        if (hospitalDao == null) {
            hospitalDao = ReservationApplication.getInstances().getDaoSession().getHospitalDao();
        }
        if (hosDataManager == null) {
            hosDataManager = new HosDataManager();
        }
        initData();
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

    /**
     * 初始化页面数据
     */
    protected void initData() {
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mHosManageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        HosManageAdapter adapter = new HosManageAdapter(getContext(), hospitalList);

        adapter.setOnItemClickListener((itemView, pos) -> {

            Hospital hospital = hospitalList.get(pos);
            String dialogTitle = hospital.getHospitalName();
            String dialogContent = hospital.getDetailAddr();
            showMessagePositiveDialog(dialogTitle, dialogContent,
                    "取消", (dialog, index) -> {dialog.dismiss();},
                    "关闭", (dialog, index) -> {
                        dialog.dismiss();
                    });
        });
        mHosManageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mHosManageRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        hospitalList = hospitalDao.loadAll();
    }

    /**
     * list
     */
    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        hosDataManager.list(tHospital)
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
     * 接收服务端的json数据后序列化成hospital对象列表
     * 然后将这些对象存储到SQLite中
     *
     * @param json json数据
     */
    private void dumpAllData(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        List<Hospital> hospitals = GsonParser.fromJSONArray(jsonArray, Hospital.class);
        hospitalDao.deleteAll();
        hospitalDao.insertInTx(hospitals);
    }
}
