package com.nicolas.coding.common.htmltext;

import com.nicolas.coding.common.Global;
import com.nicolas.coding.model.Maopao;

/**
 * Created by chenchao on 15/3/9.
 */
public class LinkCreate {

    public static String maopao(Maopao.MaopaoObject maopao) {
        return Global.HOST + maopao.path;
    }


}
