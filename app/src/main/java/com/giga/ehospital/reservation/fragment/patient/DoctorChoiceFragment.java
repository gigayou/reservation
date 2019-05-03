package com.giga.ehospital.reservation.fragment.patient;

import android.app.ProgressDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giga.ehospital.ReservationApplication;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.adapter.hosadmin.DoctorManageAdapter;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.DoctorDataManager;
import com.giga.ehospital.reservation.model.hospital.Department;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.giga.ehospital.reservation.model.hospital.DoctorDao;
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

public class DoctorChoiceFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.doctor_choice_recyclerview)
    RecyclerView mDoctorChoiceRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.patient_doctor_choice_fragment_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Doctor tDoctor = new Doctor();
    private String hospitalId;
    private Long departmentTypeId;
    private DoctorDao doctorDao;
    private DoctorDataManager doctorDataManager;
    private List<Doctor> doctorList;

    // qmui dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_doctor_choice;
    }

    @Override
    protected void initLastCustom() {
        Hospital hospital = NormalContainer.get(NormalContainer.SELECTED_HOSPITAL);
        Department department = NormalContainer.get(NormalContainer.SELECTED_DEPARTMENT);
        if (hospital == null || department == null) {
            getActivity().onBackPressed();
            Toasty.warning(getContext(), "数据同步错误，请重新选择科室", Toasty.LENGTH_SHORT, true).show();
        }
        if (doctorDao == null)
            doctorDao = ReservationApplication.getInstances().getDaoSession().getDoctorDao();
        if (doctorDataManager == null)
            doctorDataManager = new DoctorDataManager();
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
        Department department = NormalContainer.get(NormalContainer.SELECTED_DEPARTMENT);
        departmentTypeId = department.getTypeId();
        rxReceiveJsonData();
        readAllData();
        initListView();
    }

    private void initListView() {
        mDoctorChoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        DoctorManageAdapter adapter = new DoctorManageAdapter(getContext(), this.doctorList);

        adapter.setOnItemClickListener((itemView, pos) -> {
            Doctor doctor = this.doctorList.get(pos);
            NormalContainer.put(NormalContainer.SELECTED_DOCTOR, doctor);
            startFragment(new CalendarChoiceFragment());
        });
        mDoctorChoiceRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mDoctorChoiceRecyclerView.setAdapter(adapter);
    }

    private void readAllData() {
        this.doctorList = doctorDao.loadAll();
    }

    private void rxReceiveJsonData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tDoctor.setHospitalId(hospitalId);
        tDoctor.setTypeId(departmentTypeId);
        doctorDataManager.list(tDoctor)
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
        List<Doctor> doctorList = GsonParser.fromJSONArray(jsonArray, Doctor.class);
        doctorDao.deleteAll();
        doctorDao.insertInTx(doctorList);
    }
}
