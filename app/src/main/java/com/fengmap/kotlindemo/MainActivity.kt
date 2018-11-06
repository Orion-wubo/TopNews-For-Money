package com.fengmap.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fengmap.android.map.FMMap
import com.fengmap.android.map.FMMapUpgradeInfo
import com.fengmap.android.map.animator.FMAccelerateDecelerateInterpolator
import com.fengmap.android.map.event.OnFMCompassListener
import com.fengmap.android.map.event.OnFMMapClickListener
import com.fengmap.android.map.event.OnFMMapInitListener
import com.fengmap.android.map.event.OnFMSwitchGroupListener
import com.fengmap.android.widget.FMFloorControllerComponent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFMMapInitListener, OnFMSwitchGroupListener, OnFMCompassListener,OnFMMapClickListener {
    override fun onMapClick(p0: Float, p1: Float) {
        val pickMapCoord = fmMap!!.pickMapCoord(p0, p1)
        if (pickMapCoord!=null) {
            val mapCoord = pickMapCoord.mapCoord
        }

    }

    private var fmMap: FMMap? = null
    private var mFloorComponent: FMFloorControllerComponent? = null
    var isSceneChangeCompleted = false
    private var gids: IntArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fmMap = mapview.fmMap
        fmMap!!.setOnFMMapInitListener(this)
        fmMap!!.onFMCompassListener = this
        fmMap!!.setOnFMMapClickListener(this)

        fmMap!!.openMapById("10347", true)
    }

    override fun onMapInitSuccess(p0: String?) {
        fmMap!!.loadThemeById("2001")
        fmMap!!.showCompass()
//        gids = fmMap!!.mapGroupIds
//        fmMap!!.setMultiDisplay(gids, 0, this)

        // 加载楼层控件1
        initFloorControllerComponent()
    }

    private fun initFloorControllerComponent() {
        if (mFloorComponent != null) {
            return
        }
        // 楼层切换
        mFloorComponent = FMFloorControllerComponent(this)
        mFloorComponent!!.setMaxItemCount(4)
        mFloorComponent!!.setOnFMFloorControllerComponentListener(object : FMFloorControllerComponent.OnFMFloorControllerComponentListener {
            override fun onSwitchFloorMode(v: View, currentMode: FMFloorControllerComponent.FMFloorMode) {
                if (currentMode == FMFloorControllerComponent.FMFloorMode.SINGLE) {
                    setSingleDisplay()
                } else {
                    setMultiDisplay()
                }
            }

            override fun onItemSelected(groupId: Int, floorName: String): Boolean {
                if (isSceneChangeCompleted) {
                    switchFloor(groupId)
                    return true
                }
                return false
            }
        })

        mFloorComponent!!.setFloorMode(FMFloorControllerComponent.FMFloorMode.SINGLE)
        mFloorComponent!!.setFloorDataFromFMMapInfo(fmMap!!.fmMapInfo, fmMap!!.focusGroupId)
        mapview.addComponent(mFloorComponent, 10, 400)

    }

    //多层显示
    internal fun setMultiDisplay() {
        val gids = fmMap!!.getMapGroupIds()    //获取地图所有的group
        val fd = mFloorComponent!!.getFloorData(mFloorComponent!!.getSelectedPosition())
        var focus = 0
        for (i in gids.indices) {
            if (gids[i] == fd.getGroupId()) {
                focus = i
                break
            }
        }
        fmMap!!.setMultiDisplay(gids, focus, this)
    }

    //单层显示
    internal fun setSingleDisplay() {
        val gids = intArrayOf(fmMap!!.getFocusGroupId())       //获取当前地图焦点层id
        fmMap!!.setMultiDisplay(gids, 0, this)
    }

    //切换楼层
    internal fun switchFloor(groupId: Int) {
        fmMap!!.setFocusByGroupIdAnimated(groupId, FMAccelerateDecelerateInterpolator(), this)
        //        mMap.setFocusByGroupId(groupId, this);
    }

    override fun onMapInitFailure(p0: String?, p1: Int) {
    }

    override fun onUpgrade(p0: FMMapUpgradeInfo?): Boolean {
        return false
    }

    override fun afterGroupChanged() {
        isSceneChangeCompleted = true
    }

    override fun beforeGroupChanged() {
        isSceneChangeCompleted = false
    }

    override fun onCompassClick() {
        fmMap!!.resetCompassToNorth()
    }
}
