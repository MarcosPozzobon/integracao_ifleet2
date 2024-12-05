package com.ghelere.ti.desenvolvimento.controller.request;

import java.util.List;

public record AlarmCountRequest (
        String starttime,
        String endtime,
        Long type,
        String key,
        List<String> terminalIds
){
}
