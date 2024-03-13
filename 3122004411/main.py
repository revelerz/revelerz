# 导入库
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import re
import sys
import jieba


def deletepunct(text):
    # 使用正则表达式删除符号
    return re.sub(r'[\n\s\.,.，。、’“”:：;!！?？()（）"\'\-]', "", text)

#使用jieba分词
def tokenize(text):
    return list(jieba.cut(deletepunct(text)))

#读取文件
def readfile(path):
    with open(path, 'r', encoding='utf-8') as file:
        return file.read()

def Vectorize(oritxt,comptxt):
    # 将文本转换为向量
    vector = TfidfVectorizer(tokenizer=tokenize, lowercase=False)
    martix = vector.fit_transform([oritxt, comptxt])
    return  martix
def sim_rate(oritxt,comptxt):#计算相似度

    #将文本转换为向量
    martix=Vectorize(oritxt,comptxt)
    #计算余弦相似度
    sim_rate=cosine_similarity(martix[0:1],martix[1:2])[0][0]
    return float(sim_rate)

#命令行得到路径后的操作
def pathop(oripath,comppath,respath):

    # 计算查重率
    similarity = sim_rate(oritxt=readfile(oripath), comptxt=readfile(comppath))

    # 将查重率输出到答案文件
    with open(respath, 'w', encoding='utf-8') as f:
        f.write(f"Similarity: {similarity:.2%}")


if __name__ == '__main__':
    # 确保命令行参数数量正确
    if len(sys.argv) != 4:
        print("输入格式为: python main.py <原文路径> <对比文路径> <输出路径>")
        sys.exit(1)
    pathop(sys.argv[1], sys.argv[2], sys.argv[3])


