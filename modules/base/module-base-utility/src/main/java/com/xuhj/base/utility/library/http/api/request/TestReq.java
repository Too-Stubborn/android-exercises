package com.xuhj.library.http.api.request;

import java.util.List;

/**
 * 描述
 *
 * @author xuhj
 */
public class TestReq {
    /**
     * dataset : [{"d1":"serviceDDD1"},{"d2":"serviceDDD2"}]
     * parameter : {"p2":"servicePPP2","p1":"servicePPP1"}
     */

    private ParameterBean parameter;
    private List<DatasetBean> dataset;

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean parameter) {
        this.parameter = parameter;
    }

    public List<DatasetBean> getDataset() {
        return dataset;
    }

    public void setDataset(List<DatasetBean> dataset) {
        this.dataset = dataset;
    }

    public static class ParameterBean {
        /**
         * p2 : servicePPP2
         * p1 : servicePPP1
         */

        private String p2;
        private String p1;

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
        }

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }
    }

    public static class DatasetBean {
        /**
         * d1 : serviceDDD1
         * d2 : serviceDDD2
         */

        private String d1;
        private String d2;

        public String getD1() {
            return d1;
        }

        public void setD1(String d1) {
            this.d1 = d1;
        }

        public String getD2() {
            return d2;
        }

        public void setD2(String d2) {
            this.d2 = d2;
        }
    }
}
