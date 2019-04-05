package com.giga.ehospital.reservation.base.inter;

import com.giga.ehospital.reservation.base.fragment.BaseFragment;

public interface ControllerClickHandler {

    /**
     * 打开一个新的fragment
     * @param targetFragment
     */
    void startFragment(BaseFragment targetFragment);

    /**
     * 打开一个新的fragment并销毁当前fragment
     * @param targetFragment
     */
    void startFragmentAndDestroyCurrent(BaseFragment targetFragment);
}
