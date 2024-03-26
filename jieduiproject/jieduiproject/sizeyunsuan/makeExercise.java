package com.a;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class makeExercise {//生成n道r以内的算式
    ArrayList<Expression> exArray=new ArrayList<>();
    static int  exit=0;//在集合中找到相同算式的次数
    public makeExercise(int n,int r) throws IOException {

        FileWriter fw=new FileWriter("Exercises.txt");
        FileWriter an=new FileWriter("Answers.txt");
        for(int i=0;i<n;i++) {
            if(exit>10000)//有10000次生成已有的算式
            {
                System.out.println("暂不支持生成"+n+"道"+"运算数小于"+r+"的四则运算题目");
                break;
            }
            Expression e = new Expression(r);
            if (e.islegal == true&&isexist(e)==false){//判断是否合法
                exArray.add(e);//添加进集合
                an.write(i+1+". ");
                 if(e.answer.denominator==1){//自然数
                    an.write(e.answer.numerator+"\n");
                }
                else{
                     int shang=e.answer.numerator/e.answer.denominator;
                     if(shang>=1){//判断是否是带分数
                         int yu=e.answer.numerator%e.answer.denominator;//带分数整数部分
                         an.write(shang+"’"+yu+"/"+e.answer.denominator+"\n");
                     }
                     else{
                         an.write(e.answer.numerator+"/"+e.answer.denominator+"\n");
                     }
                 }

                StringBuilder sb=new StringBuilder((i+1)+".");//题号
                for(int j=0;j<=e.countNumber;j++) {
                    addLeftBracket(e, j, sb);//左括号在数字前面

                  if(j<e.countNumber){
                      if(e.number2.get(j).denominator==1)
                          sb.append(" "+e.number2.get(j).numerator);//自然数
                      else{//分数
                          int shang=e.number2.get(j).numerator/e.number2.get(j).denominator;//分数的商
                          if(shang>0)//带分数
                          {
                              int yu=e.number2.get(j).numerator%e.number2.get(j).denominator;//余数
                              sb.append(" "+shang+"’"+yu+"/"+e.number2.get(j).denominator);
                          }
                          else{
                              sb.append(" "+e.number2.get(j).numerator+"/"+e.number2.get(j).denominator);
                          }
                      }
                  }
                    addRightBracket(e, j,sb);//右括号在符号前面数字后面
                    if(j<e.symbol2.size())
                        sb.append(" "+e.symbol2.get(j));
                }

                sb.append(" =");
                fw.write(sb.toString());
                fw.write("\n");
            }

            else
                i--;//不合法重新生成

        }
        fw.close();
        an.close();
    }

    private static void addRightBracket(Expression e,int j, StringBuilder sb) {//写入右括号
        if (e.bracket.size() == 2)//一个括号
        {
            if (j == e.bracket.get(1)-1)
                sb.append(")");
        }
        else if(e.bracket.size()==4){//两个括号
            if (j == e.bracket.get(1)-1)
                sb.append(")");
            if (j == e.bracket.get(3)-1)
                sb.append(")");
        }
    }

    private static void addLeftBracket(Expression e, int j, StringBuilder sb) {//写入左括号
        if (e.bracket.size() == 2)//一个括号
        {
            if (j == e.bracket.get(0))
                sb.append("(");
        }
        else if(e.bracket.size()==4){//两个括号
            if (j == e.bracket.get(0))
                sb.append("(");
            if (j == e.bracket.get(2))
                sb.append("(");
        }
    }//打印左括号

    private boolean isexist(Expression e){//判断是否已经存在相同的算式

        if(exArray.size()==0)
            return false;
        for(int i=0;i<exArray.size();i++) {
            if(e.answer.numerator!=exArray.get(i).answer.numerator||
            e.answer.denominator!=exArray.get(i).answer.denominator){//比较答案
                continue;
            }
            else if(e.countNumber!=exArray.get(i).countNumber){//比较运算数个数
                continue;
            }
            else if(e.symbol2.size()!=exArray.get(i).symbol2.size()){//比较符号个数
                continue;
            }
            int op=0;//判断运算数和符号是否相同，相同赋值0
            for(int j=0;j<e.countNumber;j++){//一一比对运算数
                int judge=0;//判断是否有相同的操错数，没有找到则赋值0
                for(int k=0;k<e.countNumber;k++){
                    if(e.number2.get(j).numerator==exArray.get(i).number2.get(k).numerator&&
                            e.number2.get(j).denominator==exArray.get(i).number2.get(k).denominator){
                        judge=1;
                        break;
                    }

                }
                if(judge==0)
                {
                    op=1;
                    break;
                }

            }//运算数相同
            if(op==1)
               continue;
            op=0;
            for(int j=0;j<e.symbol2.size();j++){
                int judge=0;//1表示找到相同符号
                for(int k=0;k<e.symbol2.size();k++){
                    if(e.symbol2.get(j)==exArray.get(i).symbol2.get(k)||
                            e.symbol2.get(j)=='×'&&exArray.get(i).symbol2.get(k)=='×'
                    ||e.symbol2.get(j)=='÷'&&exArray.get(i).symbol2.get(k)=='÷'){
                        judge=1;
                        break;
                    }

                }
                if(judge==0)
                {
                    op=1;
                    break;
                }
           }//运算符相同
            if(op==1)
                continue;
            exit++;//算式相同
            return true;
        }
        return false;

    }
}
