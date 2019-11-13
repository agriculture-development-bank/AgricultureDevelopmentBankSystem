package com.casic.generator;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpringELTest {
    public static void main(String[] args) {
        //解析器
        ExpressionParser parser  = new SpelExpressionParser();
        TestAccount account = new TestAccount(99,"123");
        EvaluationContext context = new StandardEvaluationContext(account);
        boolean result=parser.parseExpression("(amount > 100) || (deptCode=='123') ").getValue(context,Boolean.class);
        System.out.println("操作结果:"+result);
    }

}

class TestAccount {
    private Integer amount;
    private String deptCode;

    public TestAccount(Integer amount,String deptCode){
        this.amount = amount;
        this.deptCode = deptCode;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}