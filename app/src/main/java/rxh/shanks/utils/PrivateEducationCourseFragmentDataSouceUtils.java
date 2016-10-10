package rxh.shanks.utils;

import java.util.ArrayList;
import java.util.List;

import rxh.shanks.entity.DataSouceCoachEntity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class PrivateEducationCourseFragmentDataSouceUtils {


    /**
     * workTime表示工作时间格式为9:00-23:00
     * restTime表示课程间隔时间，格式为分钟
     * holdTime表示工作时间格式为"15:00-16:00","16:30-17:30"
     */
    public static List<DataSouceCoachEntity> creatcoachdata(String workTime, String restTime, String date) {
        List<DataSouceCoachEntity> data = new ArrayList<>();
        int start_hour, start_minute, end_hour, end_minute;
        String time = new String(workTime);
        String times[] = time.split("-");
        String start_time = times[0];
        String end_time = times[1];
        String start_hours[] = start_time.split(":");
        String end_hours[] = end_time.split(":");
        start_hour = Integer.parseInt(start_hours[0]);
        start_minute = Integer.parseInt(start_hours[1]);
        end_hour = Integer.parseInt(end_hours[0]);
        end_minute = Integer.parseInt(end_hours[1]);

        //经过计算后的结果
        int Calculated_results_hour = start_hour, Calculated_results_minute = start_minute;
        //第一个数据
        DataSouceCoachEntity dataSouceCoachEntityone = new DataSouceCoachEntity();

        //检验这个时间段是否已经过去
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        if (year > CreatTime.getyear()) {
            dataSouceCoachEntityone.setFlag(0);
        } else if (year < CreatTime.gethours()) {
            dataSouceCoachEntityone.setFlag(3);
        } else {
            //比月
            if (month > CreatTime.getmonth() + 1) {
                dataSouceCoachEntityone.setFlag(0);
            } else if (month < CreatTime.getmonth() + 1) {
                dataSouceCoachEntityone.setFlag(3);
            } else {
                //比日
                if (day > CreatTime.getday()) {
                    dataSouceCoachEntityone.setFlag(0);
                } else if (day < CreatTime.getday()) {
                    dataSouceCoachEntityone.setFlag(3);
                } else {
                    //时间表时间小于系统时间
                    if (Calculated_results_hour < CreatTime.gethours()) {
                        dataSouceCoachEntityone.setFlag(3);
                        //时间表时间等于系统时间。判断分钟的大小
                    } else if (Calculated_results_hour == CreatTime.gethours()) {
                        if (Calculated_results_minute < CreatTime.getminutes()) {
                            dataSouceCoachEntityone.setFlag(3);
                        } else {
                            dataSouceCoachEntityone.setFlag(0);
                        }
                        //时间表时间大于系统时间
                    } else {
                        dataSouceCoachEntityone.setFlag(0);
                    }
                }
            }
        }
        if (Calculated_results_minute < 10) {
            dataSouceCoachEntityone.setTime(Calculated_results_hour + ":0" + Calculated_results_minute);
        } else {
            dataSouceCoachEntityone.setTime(Calculated_results_hour + ":" + Calculated_results_minute);
        }
        data.add(dataSouceCoachEntityone);
        //获取datasouce的个数
        int end = ((end_hour * 60 + end_minute) - (start_hour * 60 + start_minute)) / Integer.parseInt(restTime);
        for (int i = 0; i < end - 1; i++) {
            DataSouceCoachEntity dataSouceCoachEntity = new DataSouceCoachEntity();
            Calculated_results_minute = Calculated_results_minute + Integer.parseInt(restTime);
            if (Calculated_results_minute > 59) {
                Calculated_results_minute = Calculated_results_minute - 60;
                Calculated_results_hour = Calculated_results_hour + 1;
            }
            if (Calculated_results_minute < 10) {
                dataSouceCoachEntity.setTime(Calculated_results_hour + ":0" + Calculated_results_minute);
            } else {
                dataSouceCoachEntity.setTime(Calculated_results_hour + ":" + Calculated_results_minute);
            }

            //检验这个时间段是否已经过去
            if (year > CreatTime.getyear()) {
                dataSouceCoachEntity.setFlag(0);
            } else if (year < CreatTime.gethours()) {
                dataSouceCoachEntity.setFlag(3);
            } else {
                //比月
                if (month > CreatTime.getmonth() + 1) {
                    dataSouceCoachEntity.setFlag(0);
                } else if (month < CreatTime.getmonth() + 1) {
                    dataSouceCoachEntity.setFlag(3);
                } else {
                    //比日
                    if (day > CreatTime.getday()) {
                        dataSouceCoachEntity.setFlag(0);
                    } else if (day < CreatTime.getday()) {
                        dataSouceCoachEntity.setFlag(3);
                    } else {
                        //时间表时间小于系统时间
                        if (Calculated_results_hour < CreatTime.gethours()) {
                            dataSouceCoachEntity.setFlag(3);
                            //时间表时间等于系统时间。判断分钟的大小
                        } else if (Calculated_results_hour == CreatTime.gethours()) {
                            if (Calculated_results_minute < CreatTime.getminutes()) {
                                dataSouceCoachEntity.setFlag(3);
                            } else {
                                dataSouceCoachEntity.setFlag(0);
                            }
                            //时间表时间大于系统时间
                        } else {
                            dataSouceCoachEntity.setFlag(0);
                        }
                    }
                }
            }


            data.add(dataSouceCoachEntity);
        }
        return data;
    }

    //60进制 教练占用的时间的计算方式
    public static List<DataSouceCoachEntity> binary_system(String workTime, String restTime, String date) {
        //flag==1,表示这是教练占用的时间；flag==2，表示这是用户占用的时间
        List<DataSouceCoachEntity> data = new ArrayList<>();
        int start_hour, start_minute, end_hour, end_minute;
        String time = new String(workTime);
        String times[] = time.split("-");
        String start_time = times[0];
        String end_time = times[1];
        String start_hours[] = start_time.split(":");
        String end_hours[] = end_time.split(":");
        start_hour = Integer.parseInt(start_hours[0]);
        start_minute = Integer.parseInt(start_hours[1]);
        end_hour = Integer.parseInt(end_hours[0]);
        end_minute = Integer.parseInt(end_hours[1]);

        //经过计算后的结果
        int Calculated_results_hour = start_hour, Calculated_results_minute = start_minute;
        //第一个数据
        DataSouceCoachEntity dataSouceCoachEntityone = new DataSouceCoachEntity();


        //检验这个时间段是否已经过去
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        if (year > CreatTime.getyear()) {
            dataSouceCoachEntityone.setFlag(0);
        } else if (year < CreatTime.gethours()) {
            dataSouceCoachEntityone.setFlag(3);
        } else {
            //比月
            if (month > CreatTime.getmonth() + 1) {
                dataSouceCoachEntityone.setFlag(0);
            } else if (month < CreatTime.getmonth() + 1) {
                dataSouceCoachEntityone.setFlag(3);
            } else {
                //比日
                if (day > CreatTime.getday()) {
                    dataSouceCoachEntityone.setFlag(0);
                } else if (day < CreatTime.getday()) {
                    dataSouceCoachEntityone.setFlag(3);
                } else {
                    //时间表时间小于系统时间
                    if (Calculated_results_hour < CreatTime.gethours()) {
                        dataSouceCoachEntityone.setFlag(3);
                        //时间表时间等于系统时间。判断分钟的大小
                    } else if (Calculated_results_hour == CreatTime.gethours()) {
                        if (Calculated_results_minute < CreatTime.getminutes()) {
                            dataSouceCoachEntityone.setFlag(3);
                        } else {
                            dataSouceCoachEntityone.setFlag(0);
                        }
                        //时间表时间大于系统时间
                    } else {
                        dataSouceCoachEntityone.setFlag(0);
                    }
                }
            }
        }


        if (Calculated_results_minute < 10) {
            dataSouceCoachEntityone.setTime(Calculated_results_hour + ":0" + Calculated_results_minute);
        } else {
            dataSouceCoachEntityone.setTime(Calculated_results_hour + ":" + Calculated_results_minute);
        }
        data.add(dataSouceCoachEntityone);
        //获取datasouce的个数
        int end = ((end_hour * 60 + end_minute) - (start_hour * 60 + start_minute)) / Integer.parseInt(restTime);
        for (int i = 0; i < end - 1; i++) {
            DataSouceCoachEntity dataSouceCoachEntity = new DataSouceCoachEntity();
            dataSouceCoachEntity.setFlag(1);
            Calculated_results_minute = Calculated_results_minute + Integer.parseInt(restTime);
            if (Calculated_results_minute > 59) {
                Calculated_results_minute = Calculated_results_minute - 60;
                Calculated_results_hour = Calculated_results_hour + 1;
            }
            if (Calculated_results_minute < 10) {
                dataSouceCoachEntity.setTime(Calculated_results_hour + ":0" + Calculated_results_minute);
            } else {
                dataSouceCoachEntity.setTime(Calculated_results_hour + ":" + Calculated_results_minute);
            }

            if (year > CreatTime.getyear()) {
                dataSouceCoachEntity.setFlag(0);
            } else if (year < CreatTime.gethours()) {
                dataSouceCoachEntity.setFlag(3);
            } else {
                //比月
                if (month > CreatTime.getmonth() + 1) {
                    dataSouceCoachEntity.setFlag(0);
                } else if (month < CreatTime.getmonth() + 1) {
                    dataSouceCoachEntity.setFlag(3);
                } else {
                    //比日
                    if (day > CreatTime.getday()) {
                        dataSouceCoachEntity.setFlag(0);
                    } else if (day < CreatTime.getday()) {
                        dataSouceCoachEntity.setFlag(3);
                    } else {
                        //时间表时间小于系统时间
                        if (Calculated_results_hour < CreatTime.gethours()) {
                            dataSouceCoachEntity.setFlag(3);
                            //时间表时间等于系统时间。判断分钟的大小
                        } else if (Calculated_results_hour == CreatTime.gethours()) {
                            if (Calculated_results_minute < CreatTime.getminutes()) {
                                dataSouceCoachEntity.setFlag(3);
                            } else {
                                dataSouceCoachEntity.setFlag(0);
                            }
                            //时间表时间大于系统时间
                        } else {
                            dataSouceCoachEntity.setFlag(0);
                        }
                    }
                }
            }
            data.add(dataSouceCoachEntity);
        }
        return data;
    }


}
