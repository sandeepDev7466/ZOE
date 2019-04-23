package com.ztp.app.Data.Remote.Model.Request;

/**
 * Created by htl-dev on 16-04-2019.
 */

public class SiftAddRequest {


    String event_id="";
    String shift_date="";
    String shift_vol_req="";
    String shift_start_time="";
    String shift_end_time="";
    String shift_rank="";
    String shift_task="";


    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getShift_date() {
        return shift_date;
    }

    public void setShift_date(String shift_date) {
        this.shift_date = shift_date;
    }

    public String getShift_vol_req() {
        return shift_vol_req;
    }

    public void setShift_vol_req(String shift_vol_req) {
        this.shift_vol_req = shift_vol_req;
    }

    public String getShift_start_time() {
        return shift_start_time;
    }

    public void setShift_start_time(String shift_start_time) {
        this.shift_start_time = shift_start_time;
    }

    public String getShift_end_time() {
        return shift_end_time;
    }

    public void setShift_end_time(String shift_end_time) {
        this.shift_end_time = shift_end_time;
    }

    public String getShift_rank() {
        return shift_rank;
    }

    public void setShift_rank(String shift_rank) {
        this.shift_rank = shift_rank;
    }

    public String getShift_task() {
        return shift_task;
    }

    public void setShift_task(String shift_task) {
        this.shift_task = shift_task;
    }



}
