package com.xiao.recommend;

import java.util.Iterator;
import java.util.List;

/**
 * Created by xiaojie on 17/3/11.
 * 计算相似度
 */
public class Similarity {

    /**
     * 皮尔逊相关系数计算相似度
     *
     * @param d1
     * @param d2
     * @return 返回相似度
     */
    public Double getsimilarity(List<Double> d1, List<Double> d2) {

        double sim = 0d; //最后的皮尔逊相关度系数

        //判断是否数据为空
        if (null == d1 || null == d2)
            return sim;

        if (d1.size() == 0 || d2.size() == 0)
            return sim;

        //判断是否数据不一致
        if (d1.size() != d2.size())
            return sim;

        double common_items_len = d1.size(); //操作数的个数
        double this_sum = 0d; //第一个相关数的和
        double u_sum = 0d; //第二个相关数的和
        double this_sum_sq = 0d; //第一个相关数的平方和
        double u_sum_sq = 0d; //第二个相关数的平方和
        double p_sum = 0d; //两个相关数乘积的和

        Iterator<Double> it1 = d1.iterator();
        Iterator<Double> it2 = d2.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            double this_grade = it1.next();
            double u_grade = it2.next();
            //评分求和                     //平方和                     //乘积和
            this_sum += this_grade;
            u_sum += u_grade;
            this_sum_sq += Math.pow(this_grade, 2);
            u_sum_sq += Math.pow(u_grade, 2);
            p_sum += this_grade * u_grade;
        }

        System.out.println("common_items_len:" + common_items_len);
        System.out.println("p_sum:" + p_sum);
        System.out.println("this_sum:" + this_sum);
        System.out.println("u_sum:" + u_sum);
        double num = common_items_len * p_sum - this_sum * u_sum;
        double den = Math.sqrt((common_items_len * this_sum_sq - Math.pow(this_sum, 2)) * (common_items_len * u_sum_sq - Math.pow(u_sum, 2)));
        System.out.println("" + num + ":" + den);
        sim = (den == 0) ? 1 : num / den;

        return sim;
    }

}
