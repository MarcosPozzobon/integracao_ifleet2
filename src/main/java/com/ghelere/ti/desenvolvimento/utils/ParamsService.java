package com.ghelere.ti.desenvolvimento.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParamsService {

    public Map<String, Object> construirParametros(String startTime, String endTime, Long type, String key, List<String> terminalIds){
        Map<String, Object> params = new HashMap<>();
        params.put("starttime", startTime);
        params.put("endtime", endTime);
        params.put("type", type);
        params.put("key", key);
        params.put("terid", terminalIds);

        return params;
    }

}
