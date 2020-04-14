
package com.study.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.study.commons.utils.DecimalUtils;
import com.study.commons.utils.FreemarkerUtils;
import com.study.dto.PeriodPowerOutput;
import com.study.dto.SendBillOutput;
import com.study.dto.StationAmountOutput;
import com.study.dto.StationBillOutput;

@Component
public class ExportExcel {

    /**
     * 导出复杂Excel示例
     * 
     */
    public void export() {
        SendBillOutput bill = new SendBillOutput();
        bill.setCustomerName("奥迪公司");
        bill.setIsGeneralTaxpayer("是");
        bill.setTaxNumber("123456789");
        bill.setAddressAndPhone("北京市望京SOHO" + "&#10;" + "010-8866396");
        bill.setBankAndAccount("中国银行&#10;123456");
        List<StationBillOutput> stationBillList = new ArrayList<StationBillOutput>();
        // 模拟n个电站
        for (int i = 0; i < 5; i++) {
            StationBillOutput stationBillOutput = new StationBillOutput();
            stationBillOutput.setDescription("奥迪公司3月份电费" + i);
            stationBillOutput.setPeriod("2020年03月01日_2020年03月31日");
            // 尖峰平谷时间段数据赋值
            List<PeriodPowerOutput> periodPowerList = new ArrayList<PeriodPowerOutput>();
            for (int j = 0; j < 5; j++) {
                PeriodPowerOutput periodPower = new PeriodPowerOutput();
                switch (j) {
                    case 0:
                        periodPower.setPowerName("尖");
                        break;
                    case 1:
                        periodPower.setPowerName("峰");
                        break;
                    case 2:
                        periodPower.setPowerName("平");
                        break;
                    case 3:
                        periodPower.setPowerName("谷");
                        break;
                    case 4:
                        periodPower.setPowerName("合计");
                        break;
                    default:
                        break;
                }
                periodPower.setPower(DecimalUtils.toBigDecimal(j + 1000));
                periodPower.setPrice(DecimalUtils.toBigDecimal(j + 0.1));
                // 若Excel公式自动计算，这几个字段不用插值
                periodPower.setNoTaxMoney(DecimalUtils.toBigDecimal(j + 1002));
                periodPower.setTaxRate(13);
                periodPower.setTaxAmount(DecimalUtils.toBigDecimal(j + 1004));
                periodPower.setTaxmoney(DecimalUtils.toBigDecimal(j + 1005));
                periodPowerList.add(periodPower);
            }
            stationBillOutput.setPeriodPowerList(periodPowerList);
            stationBillOutput.setStationName("奥迪公司园区" + i+1);
            stationBillList.add(stationBillOutput);
        }
        bill.setStationBillList(stationBillList);
        StationAmountOutput stationAmountOutput = new StationAmountOutput();
        stationAmountOutput.setPower(DecimalUtils.toBigDecimal(123));
        stationAmountOutput.setNoTaxMoney(DecimalUtils.toBigDecimal(456));
        stationAmountOutput.setTaxAmount(DecimalUtils.toBigDecimal(789));
        stationAmountOutput.setTaxmoney(DecimalUtils.toBigDecimal(2324));
        bill.setStationAmount(stationAmountOutput);
        String templateName = "开票申请单.ftl";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bill", bill);
        //导出到项目所在目录下，export文件夹中
        FreemarkerUtils.exportToFile(map, templateName, "", "export/导出Excel.xls");
    }
}