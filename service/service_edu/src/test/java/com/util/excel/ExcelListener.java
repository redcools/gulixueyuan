package com.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DamoData> {
    @Override
    public void invoke(DamoData data, AnalysisContext context) {
        System.out.println("8:"+data);
    }
    @Override
    public void invokeHeadMap(Map<Integer, String > headMap, AnalysisContext context){
        System.out.println("表头："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
