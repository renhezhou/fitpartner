package rxh.shanks.utils;

/**
 * Created by Administrator on 2016/4/12.
 */
public class CreatUrl {

    //正式环境http://139.224.18.119/fitpartner/
    //小米环境http://192.168.0.123:9090/
    //阿三环境http://192.168.0.119:8080/

    public static String base_url = "http://139.224.18.119/fitpartner/";

    public static String creaturl(String model, String action) {
        String url = null;
        url = base_url + model + "/" + action;
        return url;
    }

}
