package com.quickinterior.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.quickinterior.Fragment.AvailableStock;
import com.quickinterior.Fragment.WorkOrder;


public class Pager extends FragmentPagerAdapter {


    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                WorkOrder workOrder = new WorkOrder();
                return workOrder;

            case 1:
                AvailableStock availableStock = new AvailableStock();
                return availableStock;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}