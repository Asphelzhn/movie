package com.xiao;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
//        System.out.println(countSMS(21387871));
//        String s = "【民生转赚】验证码为：576884，切勿告知他人。客服热线400-69-95568转2。";
        int[] a = {8, 1, 3, 4, 2, 6};
//        insertSort(a);
//        bubbleSort(a);
        quickSort(a);
        printArray(a);
    }

    /**
     * 一条业务短信可能超过70个字符，运营商条数规则是短信长1度小于等于70，算一条；超过70，按67字算一条；一个字符就算一个，不区分中英文；
     * 例子：一条业务短信内容70字，运营商按1条收费；若134字，按2条收费；若135字，按3条收费。
     *
     * @param character
     * @return
     */
    public static long countSMS(long character) {
        if (character > 70) {
            long number = character / 67;
            if (0 != (character % 67))
                number++;
            return number;
        }
        return 1;
    }

    /**
     * 插入排序
     */
    public static void insertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                int j = i - 1;
                int temp = a[i];
                do {
                    a[j + 1] = a[j];
                    j--;
                } while (j >= 0 && temp < a[j]);
                a[j + 1] = temp;
            }
            printArray(a);
        }
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
            printArray(a);
        }
    }

    /**
     * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
     *
     * @param numbers 带查找数组
     * @param low     开始位置
     * @param high    结束位置
     * @return 中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        int temp = numbers[low]; //数组的第一个作为中轴
        while (low < high) {
            while (low < high && numbers[high] > temp) {
                high--;
            }
            numbers[low] = numbers[high];//比中轴小的记录移到低端
            while (low < high && numbers[low] < temp) {
                low++;
            }
            numbers[high] = numbers[low]; //比中轴大的记录移到高端
        }
        numbers[low] = temp; //中轴记录到尾
        return low; // 返回中轴的位置
    }

    /**
     * @param numbers 带排序数组
     * @param low     开始位置
     * @param high    结束位置
     */
    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high); //将numbers数组进行一分为二
            quickSort(numbers, low, middle - 1);   //对低字段表进行递归排序
            quickSort(numbers, middle + 1, high); //对高字段表进行递归排序
        }

    }

    /**
     * 快速排序
     * @param numbers 带排序数组
     */
    public static void quickSort(int[] numbers) {
        if (null != numbers && numbers.length > 0)   //查看数组是否为空
        {
            quickSort(numbers, 0, numbers.length - 1);
        }
    }

    public static void printArray(int[] a) {
        System.out.println("排序结果如下：");
        for (int i : a)
            System.out.print(i + "\t");
        System.out.println();
    }

}