package com.a;

import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Random;

public class Expression {//算术表达式

    Random R=new Random();
    boolean islegal=true;//判断算式是否合法（不产生负数）
    int countNumber=R.nextInt(3)+2;//运算数个数
    ArrayList<Number> number2=new ArrayList<>();//由于计算答案时要将number1集合和符号集合改变，故创建number2和symbol2记录算式信息
    ArrayList<Number> number=new ArrayList<>();//运算数
    ArrayList<Integer>bracket=new ArrayList<>();//括号数组，表示括号位置，位于相同索引的运算数的左边
    static String Symbol="+-×÷";//所有运算符
    ArrayList<Character> symbol=new ArrayList<>();//存储运算符,'0'表示已计算
    ArrayList<Character> symbol2=new ArrayList<>();
    Number answer=new Number();//运算结果
    public Expression(int r) {
              makenumber(r);//生成运算数集合
              makeSymbol();//生成运算符集合
              makeBracket();//生成括号数组
              //ischu0();//判断运算过程是否产生0为除数
              answer=getAnswer(0,symbol.size());
              if(answer.denominator==0){
                  islegal=false;

              }
    }

    public Expression() {
    }

//    private void ischu0(){//判断是否有除以0的情况
//        for(int i=0;i<symbol.size();i++){
//            if(symbol.get(i)=='÷'&&number.get(i+1).numerator==0)
//                islegal=false;
//        }
//    }
    public  Number getAnswer(int begin, int end){
        if(bracket.size()==0)//没有括号时
            return countExpression(begin,end);
        else{
            //有括号时，先算括号
            if(bracket.size()==2){//一个括号
                countExpression(bracket.get(0),bracket.get(1)-1);//先算括号
                return countExpression(0,symbol.size());//再算外面
            }
            else{
                //判断两个括号是否有交集
                if(bracket.get(0)<=bracket.get(2)&&bracket.get(1)>= bracket.get(3))//一个括号包另一个括号
                {
                    countExpression(bracket.get(2),bracket.get(3)-1);//先算内括号
                    countExpression(bracket.get(0),bracket.get(1)-2);//再算外括号
                    return countExpression(0,symbol.size());
                }
                else{//无交集

                    countExpression(bracket.get(0),bracket.get(1)-1);
                    countExpression(bracket.get(2)-1,bracket.get(3)-2);
                    return countExpression(0,symbol.size());
                }
            }
        }
    }
    private Number countExpression(int begin,int end) {
        Number sum = new Number(0, 1);
        for (int i = begin; i < symbol.size()&&i<end; i++) {//第一遍遍历先算乘除
            switch (symbol.get(i)) {
                case '×':
                case '÷':
                    sum = count(number.get(i), number.get(i + 1), symbol.get(i));
                    number.set(i, sum);//将结果存入number
                    number.remove(i + 1);
                    symbol.remove(i);
                    end--;
                    i--;
                    if(sum.numerator<0||sum.denominator<=0){
                        islegal=false;
                        return sum;
                    }
            }
//

        }
            for (int i = begin; i < symbol.size()&&i<end; i++) {//顺序计算
                sum=count(number.get(i),number.get(i+1),symbol.get(i));
                if(sum.denominator<=0||sum.numerator<0){
                    islegal=false;
                    return sum;
                }
                number.set(i, sum);
                number.remove(i + 1);
                symbol.remove(i);
                i--;
                end--;
            }

            return number.get(0);

    }
    public static Number count(Number n1,Number n2,char c)//传入两个数的分子分母以及运算符
    {
        switch(c){
            case '÷':
                if(n2.numerator==0){
                    Number nl=new Number(-1,-1);
                    return nl;
                }
            n1.numerator*=n2.denominator;
            n1.denominator*=n2.numerator;
            break;
            case '×':
            n1.denominator*=n2.denominator;
            n1.numerator*=n2.numerator;
            break;
            case '+':
                n1.numerator*=n2.denominator;
                n2.numerator*=n1.denominator;//通分
                n1.denominator*=n2.denominator;
                n1.numerator+=n2.numerator;
                break;
            case '-':
                n1.numerator*=n2.denominator;
                n2.numerator*=n1.denominator;//通分
                n1.denominator*=n2.denominator;
                n1.numerator-=n2.numerator;
                break;
        }
        n1=simplify(n1.numerator,n1.denominator);
        return n1;

    }
    private void makeBracket() {
        int brackNumber=R.nextInt(countNumber-1);//括号个数
        if(brackNumber==1) {
            int index=R.nextInt(2);
            bracket.add(index);
            bracket.add(index+2);
        }
        else if(brackNumber==2){
           int[][] styl={{0,2,2,4},{0,3,0,2},{0,3,1,3},{1,4,1,3},{1,4,2,4}};//所有括号可能的位置
            int index=R.nextInt(4);
            for(int i=0;i<styl[0].length;i++){
                bracket.add(styl[index][i]);
            }
        }
    }

    private void makeSymbol() {
        for(int i=0;i<countNumber-1;i++){
            symbol.add(Symbol.charAt(R.nextInt(4)));
        }

        copySymbol(symbol2,symbol);
    }

    public static void copySymbol(ArrayList<Character> symbol2,ArrayList<Character> symbol) {
        for(int i=0;i<symbol.size();i++){
            symbol2.add(symbol.get(i));//复制符号
        }
    }

    public static void copyBracket(int[] bracket2,int[] bracket){
        bracket2=new int[bracket.length];
        for(int i=0;i<bracket.length;i++){
            bracket2[i]=bracket[i];
        }
    }

    private void makenumber(int r) {
       for(int i=0;i<countNumber;i++)
       {
           Number num=new Number();
           int isfraction=0;
           if(r>2)
           isfraction=R.nextInt(2);//判断是否生成分数，1为分数，0为自然数
           if(isfraction==0){//随机生成自然数
               num.numerator=R.nextInt(r);
               num.denominator=1;//自然数分子为1;
           }
           else{//生成分数
               int a=1;
               int b=1;
              while(true){//必须是真分数
                  a=R.nextInt(r *(r -1)-1)+1;//分子
                  b=R.nextInt(r-1)+1;//分母;
                  if(a%b!=0&&a/b<r)
                      break;

              }
               num=simplify(a,b);
           }
           number.add(num);
       }

        copyNumber(number2,number);
    }

    public static void copyNumber(ArrayList<Number> number2,ArrayList<Number> number) {//复制运算数
        for(int i=0;i<number.size();i++){//复制运算数
            Number n=new Number();
            n.numerator=number.get(i).numerator;
            n.denominator=number.get(i).denominator;
            number2.add(n);
        }
    }

    public static Number simplify(int a,int b){//化简分数
        int a1=a;
        int b1=b;
        while (b!=0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        if(a!=0)
        {
            a1/=a;
            b1/=a;
        }
        else{
            a1=0;
            b1=1;
        }
        Number num=new Number(a1,b1);
        return num;
    }
}
