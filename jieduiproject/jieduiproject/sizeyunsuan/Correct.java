package com.a;

import java.io.*;
import java.util.ArrayList;

public class Correct {//批改题目
    StringBuilder correct = new StringBuilder();//对题
    StringBuilder wrong = new StringBuilder();//错题
    int cor = 0;//对题数量
    int wro = 0;//错题数量
    ArrayList<Integer> correctQuestion=new ArrayList<>();//对题集合
    ArrayList<Integer> wrongQuestion=new ArrayList<>();//对题集合


    public Correct(String exerciseFile,String answerFile) throws IOException {
        BufferedReader exf=new BufferedReader(new FileReader(exerciseFile));
        BufferedReader ans=new BufferedReader(new FileReader(answerFile));

        int questonNumber=1;//题号
        int point = 0;//用来跳过小数点
        while (true) {
            String expression=exf.readLine();
            String answer=ans.readLine();
            if(expression==null||answer==null)
                break;
            int i=0;
            int j=0;
            for(i=0;i<expression.length();i++){//跳过题号
                if(expression.charAt(i)=='.')
                    break;
            }
            for(j=0;j<answer.length();j++){
                if(answer.charAt(j)=='.')
                    break;
            }
            i++;
            while(j<answer.length()&&(answer.charAt(j)>'9'||answer.charAt(j)<'0'))
            {
                j++;
            }
            int begin=j;
            while(j<answer.length()&&(answer.charAt(j)<='9'&&answer.charAt(j)>='0'||
                    answer.charAt(j)=='’'||answer.charAt(j)=='/'))
            {
                j++;
            }
            Number rightAnswer=creatExpression(expression.substring(i));//计算正确答案
            Number pathAnswer=getNumber(answer.substring(begin,j));//得到文件中的答案
           if(isright(rightAnswer,pathAnswer)==true){//对
               cor++;
               correctQuestion.add(questonNumber);
            }
           else//错
           {
               wro++;
               wrongQuestion.add(questonNumber);
           }
           questonNumber++;
        }
        exf.close();
        ans.close();
        writeResult(cor,wro);

    }

    private void writeResult(int cor,int wro) throws IOException {//将结果写入文件
        FileWriter fw=new FileWriter("Grade.txt");
        fw.write("Correct："+cor+"（");
        for(int i=0;i<correctQuestion.size();i++){
            String wri="";
            wri+=correctQuestion.get(i);
            fw.write(wri);
            if(i!=correctQuestion.size()-1)//最末尾没逗号
                fw.write("，");
        }
        fw.write("）");
        fw.write("\n");
        fw.write("Wrong："+wro+"（");
        for(int i=0;i<wrongQuestion.size();i++){
            String wri="";
            wri+=wrongQuestion.get(i);
            fw.write(wri);
            if(i!=wrongQuestion.size()-1)
                fw.write("，");
        }
        fw.write("）");

        fw.close();
    }
    private  boolean isright(Number a,Number b){//比较答案
        if(a.numerator==b.numerator&&a.denominator==b.denominator)
            return true;
        else
            return false;

    }
    private Number creatExpression(String expression) {//创建算式类型
        int q=0;//判断括号
        int bracketIndex=0;//括号索引
        Expression e = new Expression();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == ' ')//跳过空格
                continue;
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-' ||
                    expression.charAt(i) == '×' || expression.charAt(i) == '÷') {
                e.symbol.add(expression.charAt(i));//将运算符存入集合
            } else if (expression.charAt(i) == '(') {
                if (e.bracket.size() == 1)
                    q = 1;//连续存入左括号，需要交换括号索引
                e.bracket.add(bracketIndex);
            } else if (expression.charAt(i) == ')') {
                e.bracket.add(bracketIndex);
            } else if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                int j = i;//运算符初索引
                while (expression.charAt(i) >= '0' && expression.charAt(i) <= '9' || expression.charAt(i) == '/'
                        || expression.charAt(i) == '’') {//录入运算数
                    i++;//运算符末索引
                }
                bracketIndex++;
                String num = expression.substring(j, i);//将运算符从表达式中取出
                e.number.add(getNumber(num));
                i--;//找末索引时i加过了，现在要减回来
            }
        }
        if(q==1){//判断是否交换括号
            int  tmp1=e.bracket.get(1);
            int tmp2=e.bracket.get(2);
            e.bracket.set(1,e.bracket.get(3));
            e.bracket.set(2,tmp1);
            e.bracket.set(3,tmp2);
        }
        return e.getAnswer(0,e.symbol.size());//算出答案
    }

    public static Number getNumber(String num) {//将字符串里的运算数转化为Number类
        int k = 0;//索引
        int a = 0;//整数
        int b = 0;//分子
        int c = 1;//分母
        while (k< num.length()&& num.charAt(k) >='0'&& num.charAt(k) <= '9') {
            k++;
        }
        a = main.getint(num.substring(0, k));//将String里的数字转化为int
       if (k< num.length()&& num.charAt(k) == '’')//带分数
        {
            k++;
            int l = k;
            while (k< num.length()&& num.charAt(k) >='0'&& num.charAt(k) <= '9') {
                k++;
            }
            b = main.getint(num.substring(l, k));
        }
       else {
            b = a;
            a=0;
        }
        k++;
        if(k< num.length())
            c = main.getint(num.substring(k));
        Number n1 = new Number(a * c + b, c);
        return n1;
    }


}

