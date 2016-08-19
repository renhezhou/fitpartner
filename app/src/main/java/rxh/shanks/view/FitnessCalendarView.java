package rxh.shanks.view;

import java.util.List;

import rxh.shanks.entity.FitnessCalendarEntity;
import rxh.shanks.entity.FitnessCalendarTimeEntity;

/**
 * Created by Administrator on 2016/8/9.
 */
public interface FitnessCalendarView {

    void getFitCalenders(List<FitnessCalendarTimeEntity> fitnessCalendarTimeEntityList);

    void onFinished(String result);

}
