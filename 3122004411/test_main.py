import unittest
import os
from main import *
class TestCase(unittest.TestCase):

    def create_test_file(self):#创建测试文档文件

        with open('testtxt1.txt','w',encoding='utf-8')as f:
            f.write('这是测试文本之原文本')

        with open('testtxt2.txt','w',encoding='utf-8')as f:
            f.write('这是测试文本之待检测文本')

        with open('testtxt3.txt','w',encoding='utf-8')as f:
            f.write('')


    def del_testfile(self):#删除测试文档文件
        os.remove('testtxt1.txt')
        os.remove('testtxt2.txt')
        os.remove('testtxt3.txt')

    def test_deletepunct(self):#测试去除标点符号
        txt='人在回家的路上成长，出发时是个孩子，回到家已成老人'
        expected_result='人在回家的路上成长出发时是个孩子回到家已成老人'
        result=deletepunct(txt)
        self.assertEqual(result,expected_result)

    def test_deletpunct_2(self):#测试有换行标识符和空格的文本去除标点
        txt='人 在回家的\n路上，出发时 是个孩子\n《回到家》已成老人'

    def test_tokenize(self):#测试简单文本的分词
        txt="广东工业大学软件工程"
        expected_result=['广东','工业','大学','软件工程']
        result=tokenize(txt)
        self.assertEqual(result,expected_result)


    def test_readfile(self):#测试读取文件文本
        file_txt='测试文件的文本'
        with open('testfile.txt','w',encoding='utf-8')as f:#创建测试读取的文件
            f.write(file_txt)
        expected_txt='测试文件的文本'
        self.assertEqual(readfile('testfile.txt'),expected_txt)
        os.remove('testfile.txt')#删除用于该测试的文档文件


    def test_sim_rate(self):#测试余弦相似度的输出结果
        #相同文本测试
        result=sim_rate('广东工业大学','广东工业大学')
        self.assertEqual(round(result,2),1)
        #无关文本测试
        result2=sim_rate('广东工业大学','北京师范大学')
        self.assertEqual(round(result2,2),0)
        #相似文档测试
        result3=sim_rate('深圳证券交易会所','上海证券交易会所')
        self.assertEqual(round(result3,2),0.6)
        #带有换行标识符的测试
        result4=sim_rate('广东广州','广东\n深圳')
        self.assertEqual(round(result4,2),0.34)
        #长句测试
        result5=sim_rate('今天是星期天，天气晴，今天晚上我要去看电影。','今天是周天，天气\n晴朗，我晚上\n要去看电影。')
        self.assertEqual(round(result5,2),0.46)

    def test_pathop(self):#测试调用路径操作与输出到文件

        #调用创建测试文件
        self.create_test_file()

        pathop('testtxt1.txt','testtxt2.txt','testtxt3.txt')
        result=readfile('testtxt3.txt')
        self.assertEqual(result,'Similarity: 42.44%')

        #调用删除测试文件
        self.del_testfile()


if __name__ == '__main__':
    pass