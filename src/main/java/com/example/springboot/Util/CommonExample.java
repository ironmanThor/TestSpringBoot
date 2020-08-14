package com.example.springboot.Util;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

/**
 * 通用的example
 *
 * @author xuzhen97
 */
public class CommonExample {

    /**
     * 分页数据
     */
    @Getter
    @Setter
    private Integer start = -1;


    @Getter
    @Setter
    private Integer end;
    /**
     * 排序
     * 例如:
     * course_id asc
     */
    @Getter
    @Setter
    protected String orderByClause;
    /**
     * 查询结果去除重复
     */
    @Getter
    @Setter
    protected boolean distinct;
    /**
     * 条件集合
     */
    @Getter
    protected List<Criteria> oredCriteria;

    public CommonExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * 使用时请自己记录引用
     *
     * @return
     */
    public Criteria or() {
        Criteria criteria = new Criteria();
        oredCriteria.add(criteria);
        return criteria;
    }

    public static class Criteria {

        @Getter
        protected List<Criterion> criteria;

        protected Criteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        /**
         * 传入没有值的条件
         * 例如:
         * user_id is null
         * u.user_id is not null
         *
         * @param condition
         */
        public Criteria noValue(String condition) {
            Assert.notNull(condition, "condition不能为Null!");
            criteria.add(new Criterion(condition));
            return this;
        }

        /**
         * 传入有值的条件
         * 例如:
         * condition: user_id =
         * value: aluoasd
         *
         * @param condition
         * @param value
         */
        public Criteria singleValue(String condition, Object value) {
            Assert.notNull(condition, "condition不能为Null!");
            Assert.notNull(value, "value不能为Null!");
            criteria.add(new Criterion(condition, value));
            return this;
        }

        /**
         * 传入一个范围值的条件
         * 例如：
         * conditon : age not between
         * value1: 10
         * value2: 25
         *
         * @param condition
         * @param value1
         * @param value2
         */
        public Criteria betweenValue(String condition, Object value1, Object value2) {
            Assert.notNull(condition, "condition不能为Null!");
            Assert.notNull(value1, "value1不能为Null!");
            Assert.notNull(value2, "value2不能为Null!");
            criteria.add(new Criterion(condition, value1, value2));
            return this;
        }
    }

    @Getter
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}
