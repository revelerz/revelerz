package com.a;

import java.io.IOException;
import java.util.Scanner;

import com.a.*;
public class main {
    public static void main(String[] args) throws IOException {//主函数接收命令行
    getParameter(args);//从命令行接收参数并执行程序

    }

    private static void getParameter(String[] args) throws IOException {
        if (args.length != 4){
            System.out.println("参数输入错误或未输入参数");
            return;
        }
        int n = -1;//题目数量
        int r = -1;//运算数范围
        String exerciseFile=null;//输入的题目文件
        String answerFile=null;//输入的答案文件
        if (args[0].equals("-r") && args[2].equals("-n")) {
            if (args[1].matches("[0-9]+") && args[3].matches("[0-9]+"))//正则表达式判断参数是否合理
            {
                r = getint(args[1]);
                n = getint(args[3]);
            } else {
                System.out.println("参数输入错误");
                return;
            }
        } else if (args[0].equals("-n") && args[2].equals("-r")) {
            if (args[1].matches("[0-9]+") && args[3].matches("[0-9]+"))//正则表达式判断参数是否合理
            {
                r = getint(args[3]);
                n = getint(args[1]);
            } else {
                System.out.println(args[1].matches("[0-9]") && args[3].matches("[0-9]"));
                System.out.println(args[1] + " " + args[3]);
                System.out.println("参数输入错误");
                return;
            }

        }
        else if(args[0].equals("-a")&& args[2].equals("-e")){
            answerFile= args[1];
            exerciseFile= args[3];
        }
        else if(args[0].equals("-e")&& args[2].equals("-a")){
            exerciseFile= args[1];
            answerFile= args[3];
        }
        else{
            System.out.println("参数输入错误");
            return;
        }
        if(n>0&&r>0){
            makeExercise m=new makeExercise(n,r);
        }
        else if(exerciseFile!=null&&answerFile!=null){
            Correct cor=new Correct(exerciseFile,answerFile);
        }
        else{
            System.out.println("参数输入错误");
        }
    }

    public static int getint(String a){//将参数转换为int
        int n=0;
        for (int i = 0; i <a.length() ; i++) {
            n*=10;
            n+=a.charAt(i)-'0';
        }
        return n;
    }

}
