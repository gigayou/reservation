package com.giga.ehospital.reservation.fragment.patient;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giga.ehospital.ReservationApplication;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.adapter.hosadmin.DepManageAdapter;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.DepDataManager;
import com.giga.ehospital.reservation.model.hospital.Department;
import com.giga.ehospital.reservation.model.hospital.DepartmentDao;
import com.giga.ehospital.reservation.model.hospital.Hospital;
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

public class DepChoiceFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.dep_choice_recyclerview)
    RecyclerView mDepChoiceRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.patient_deparment_choice_fragment_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Department tDepartment = new Department();
    private String hospitalId;
    private DepartmentDao departmentDao;
    private DepDataManager depDataManager;
    private List<Department> departmentList;

    // qmui dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_dep_choice;
    }

    @Override
    protected void initLastCustom() {
        Hospital selHospital = NormalContainer.get(NormalContainer.SELECTED_HOSPITAL);
        if (selHospital == null) {
            getActivity().onBackPressed();
            Toasty.warning(getContext(), "数据同步错误，请重新选择医院", Toasty.LENGTH_SHORT, true).show();
        }
        if (departmentDao == null)
            departmentDao = ReservationApplication.getInstances().getDaoSession().getDepartmentDao();
        if (depDataManager == null)
            depDataManager = new DepDataManager();
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

    private void initData() {
        Hospital hospital = NormalContainer.get(NormalContainer.SELECTED_HOSPITAL);
        hospitalId = hospital.getHospitalId();
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mDepChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        DepManageAdapter adapter = new DepManageAdapter(getContext(), departmentList);

        adapter.setOnItemClickListener((itemView, pos) -> {
            Department department = departmentList.get(pos);
            NormalContainer.put(NormalContainer.SELECTED_DEPARTMENT, department);
            startFragment(new DoctorChoiceFragment());
        });
        mDepChoiceRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mDepChoiceRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        departmentList = departmentDao.loadAll();
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tDepartment.setHospitalId(hospitalId);
        depDataManager.list(tDepartment)
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
        List<Department> departments = GsonParser.fromJSONArray(jsonArray, Department.class);
        departmentDao.deleteAll();
        departmentDao.insertInTx(departments);
    }
}
